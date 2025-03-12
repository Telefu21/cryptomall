package io.ozgard.cryptomall.service;


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.ozgard.cryptomall.model.CommandLineProcess;
import io.ozgard.cryptomall.params.EncryptDecryptParams;
import io.ozgard.cryptomall.params.KeyGenerateParams;

@Service
public class CalculatorService 
{
    private static final String UNKNOWN_CHARACTER = ".";
    
	@Autowired
	CommandLineProcess clProcess;
	
	public String [] getListElipticCurveName() 
	{		
		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("ecparam");
		clProcess.addCommandLineStr("-list_curves");
		
		String [] ecList = clProcess.runCommand().split("\n");
		
		clProcess.clearCommandLineStr();
		
		for(int i = 0; i < ecList.length; i++)
		{			
			if(ecList[i].contains(":") == true)
			{
				ecList[i] = ecList[i].split(":")[0].replace(" " , "");
			}
			else
			{
				ecList[i] = ecList[i-1];
			}
		}
		
		return ecList;
	}
	
	public String [] getListHashFuncs() 
	{
		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("dgst");
		clProcess.addCommandLineStr("-list");
		
		String [] clProcessOutList = clProcess.runCommand().split("\n");
		
		clProcess.clearCommandLineStr();
		
		int base = 0;
		
		for(int i = 0; i < clProcessOutList.length; i++)
		{
			if(clProcessOutList[i].contains("-"))
			{
				String [] clProcessOutSubList = clProcessOutList[i].split(" -");
				
				base += clProcessOutSubList.length;
			}
		}

		String [] retList = new String[base];
		
		base = 0;
		
		for(int i = 0; i < clProcessOutList.length; i++)
		{
			if(clProcessOutList[i].contains("-"))
			{
				String [] clProcessOutSubList = clProcessOutList[i].split(" -");
				
				for(int j = 0; j < clProcessOutSubList.length; j++)
				{
					if(clProcessOutSubList[j].startsWith("-") == false)
					{
						clProcessOutSubList[j] = "-" + clProcessOutSubList[j];
					}
					
					retList[base + j] = clProcessOutSubList[j];
				}
				
				base += clProcessOutSubList.length;
			}
		}
		
		return retList;
	}
	
	public String [] getListCiphers() 
	{
		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("enc");
		clProcess.addCommandLineStr("-list");
		
		String [] clProcessOutList = clProcess.runCommand().split("\n");
		
		clProcess.clearCommandLineStr();
		
		int base = 0;
		
		for(int i = 0; i < clProcessOutList.length; i++)
		{
			if(clProcessOutList[i].contains("-"))
			{
				String [] clProcessOutSubList = clProcessOutList[i].split(" -");
				
				base += clProcessOutSubList.length;
			}
		}

		String [] retList = new String[base];
		
		base = 0;
		
		for(int i = 0; i < clProcessOutList.length; i++)
		{
			if(clProcessOutList[i].contains("-"))
			{
				String [] clProcessOutSubList = clProcessOutList[i].split(" -");
				
				for(int j = 0; j < clProcessOutSubList.length; j++)
				{
					if(clProcessOutSubList[j].startsWith("-") == false)
					{
						clProcessOutSubList[j] = "-" + clProcessOutSubList[j];
					}
					
					retList[base + j] = clProcessOutSubList[j];
				}
				
				base += clProcessOutSubList.length;
			}
		}
		
		return retList;
	}
	
	public String keyGenerate(KeyGenerateParams keygenParams) 
	{
		String cmdRetStr = "";
		
		if(keygenParams.getKeyGenAlgo().compareTo(KeyGenerateParams.KEYGEN_ALGO_SELECT_DH) == 0
		|| keygenParams.getKeyGenAlgo().compareTo(KeyGenerateParams.KEYGEN_ALGO_SELECT_DSA) == 0)
		{
			cmdRetStr = paramFileGenerate(keygenParams);
			
			cmdRetStr += privKeyGenerate(keygenParams);
		}
		else
		{
			cmdRetStr = privKeyGenerate(keygenParams);
		}
		
		return cmdRetStr;
	}
	
	public String privKeyGenerate(KeyGenerateParams keygenParams) 
	{
		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("genpkey");
		clProcess.addCommandLineStr("-outform");
		clProcess.addCommandLineStr(keygenParams.getOutKeyFileFormat());
		
		if(keygenParams.getKeyGenAlgo().compareTo(KeyGenerateParams.KEYGEN_ALGO_SELECT_DH) == 0
		|| keygenParams.getKeyGenAlgo().compareTo(KeyGenerateParams.KEYGEN_ALGO_SELECT_DSA) == 0)
		{
			clProcess.addCommandLineStr("-paramfile");
			clProcess.addCommandLineStr(keygenParams.getInputFilePath());
		}
		else
		{
			clProcess.addCommandLineStr("-algorithm");
			clProcess.addCommandLineStr(keygenParams.getKeyGenAlgo());
		}
		
		clProcess.addCommandLineStr("-out");
		clProcess.addCommandLineStr(keygenParams.getOutputFilePath());
		
		if(keygenParams.getKeyGenAlgo().compareTo(KeyGenerateParams.KEYGEN_ALGO_SELECT_RSA) == 0)
		{
			clProcess.addCommandLineStr("-pkeyopt");
			clProcess.addCommandLineStr("rsa_keygen_bits:" + keygenParams.getKeyLength());
		}
		
		if(keygenParams.getKeyGenAlgo().compareTo(KeyGenerateParams.KEYGEN_ALGO_SELECT_ECC) == 0)
		{
			clProcess.addCommandLineStr("-pkeyopt");
			clProcess.addCommandLineStr("ec_paramgen_curve:" + keygenParams.getElepticCurveName());
		}

		if(keygenParams.getEncryptKeyFile() == true)	
		{
			clProcess.addCommandLineStr(keygenParams.getFileEncryptionCipher());
			clProcess.addCommandLineStr("-pass");
			clProcess.addCommandLineStr("pass:" + keygenParams.getFileEncryptionPassword());
		}
		
		String cmdRetStr = clProcess.runCommand();
		
		clProcess.clearCommandLineStr();
		
		return cmdRetStr;
	}
	
	public String paramFileGenerate(KeyGenerateParams keygenParams) 
	{
		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("genpkey");
		clProcess.addCommandLineStr("-outform");
		clProcess.addCommandLineStr(keygenParams.getOutKeyFileFormat());
		clProcess.addCommandLineStr("-genparam");
		clProcess.addCommandLineStr("-algorithm");
		clProcess.addCommandLineStr(keygenParams.getKeyGenAlgo());
		clProcess.addCommandLineStr("-out");
		clProcess.addCommandLineStr(keygenParams.getOutputFilePath());
		clProcess.addCommandLineStr("-pkeyopt");
		
		if(keygenParams.getKeyGenAlgo().compareTo(KeyGenerateParams.KEYGEN_ALGO_SELECT_DH) == 0)
		{
			clProcess.addCommandLineStr("dh_paramgen_prime_len:" + keygenParams.getKeyLength());
		}
		
		if(keygenParams.getKeyGenAlgo().compareTo(KeyGenerateParams.KEYGEN_ALGO_SELECT_DSA) == 0)
		{
			clProcess.addCommandLineStr("dsa_paramgen_bits:" + keygenParams.getKeyLength());
		}
		
		String cmdRetStr = clProcess.runCommand();
		
		clProcess.clearCommandLineStr();
		
		return cmdRetStr;
	}
	
	public String pubKeyGenerate(KeyGenerateParams keygenParams) 
	{
		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("pkey");
		clProcess.addCommandLineStr("-pubout");
		clProcess.addCommandLineStr("-outform");
		clProcess.addCommandLineStr(keygenParams.getOutKeyFileFormat());
		clProcess.addCommandLineStr("-out");
		clProcess.addCommandLineStr(keygenParams.getOutputFilePath());
		clProcess.addCommandLineStr("-inform");
		clProcess.addCommandLineStr(keygenParams.getInKeyFileFormat());
		clProcess.addCommandLineStr("-in");
		clProcess.addCommandLineStr(keygenParams.getInputFilePath());
		
		if(keygenParams.getEncryptKeyFile() == true)	
		{
			clProcess.addCommandLineStr("-passin");
			clProcess.addCommandLineStr("pass:" + keygenParams.getFileEncryptionPassword());
		}
		
		String cmdRetStr = clProcess.runCommand();
		
		clProcess.clearCommandLineStr();
		
		return cmdRetStr;
	}
	
	public String convertFilePemDer(KeyGenerateParams keygenParams) 
	{
		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("pkey");
		clProcess.addCommandLineStr("-outform");
		clProcess.addCommandLineStr(keygenParams.getOutKeyFileFormat());
		clProcess.addCommandLineStr("-out");
		clProcess.addCommandLineStr(keygenParams.getOutputFilePath());
		clProcess.addCommandLineStr("-inform");
		clProcess.addCommandLineStr(keygenParams.getInKeyFileFormat());
		clProcess.addCommandLineStr("-in");
		clProcess.addCommandLineStr(keygenParams.getInputFilePath());
		
		String cmdRetStr = clProcess.runCommand();
		
		clProcess.clearCommandLineStr();
		
		return cmdRetStr;
	}
	
	public String convertFileBase64ToAny(KeyGenerateParams keygenParams) 
	{
		clProcess.addCommandLineStr("openssl");
		clProcess.addCommandLineStr("base64");
		clProcess.addCommandLineStr("-d");
		clProcess.addCommandLineStr("-out");
		clProcess.addCommandLineStr(keygenParams.getOutputFilePath());
		clProcess.addCommandLineStr("-in");
		clProcess.addCommandLineStr(keygenParams.getInputFilePath());
		
		String cmdRetStr = clProcess.runCommand();
		
		clProcess.clearCommandLineStr();
		
		return cmdRetStr;
	}
	
	public String convertFileAnyToBase64(KeyGenerateParams keygenParams) 
	{
		clProcess.addCommandLineStr("openssl");
		clProcess.addCommandLineStr("base64");
		clProcess.addCommandLineStr("-out");
		clProcess.addCommandLineStr(keygenParams.getOutputFilePath());
		clProcess.addCommandLineStr("-in");
		clProcess.addCommandLineStr(keygenParams.getInputFilePath());
		
		String cmdRetStr = clProcess.runCommand();
		
		clProcess.clearCommandLineStr();
		
		return cmdRetStr;
	}
	
	public String privKeyView(KeyGenerateParams keygenParams) 
	{
		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("pkey");
		clProcess.addCommandLineStr("-text");
		clProcess.addCommandLineStr("-inform");
		clProcess.addCommandLineStr(keygenParams.getInKeyFileFormat());
		clProcess.addCommandLineStr("-in");
		clProcess.addCommandLineStr(keygenParams.getInputFilePath());
		
		if(keygenParams.getEncryptKeyFile() == true)	
		{
			clProcess.addCommandLineStr("-passin");
			clProcess.addCommandLineStr("pass:" + keygenParams.getFileEncryptionPassword());
		}
		
		String cmdRetStr = clProcess.runCommand();
		
		clProcess.clearCommandLineStr();
		
		return cmdRetStr;
	}
	
	public String pubKeyView(KeyGenerateParams keygenParams) 
	{
		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("pkey");
		clProcess.addCommandLineStr("-inform");
		clProcess.addCommandLineStr(keygenParams.getInKeyFileFormat());
		clProcess.addCommandLineStr("-pubin");
		clProcess.addCommandLineStr("-in");
		clProcess.addCommandLineStr(keygenParams.getInputFilePath());
		clProcess.addCommandLineStr("-text");
		
		String cmdRetStr = clProcess.runCommand();
		
		clProcess.clearCommandLineStr();
		
		return cmdRetStr;
	}

	public String symmetricEncrypt(EncryptDecryptParams encryptDecryptParams) 
	{
		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("enc");
		clProcess.addCommandLineStr(encryptDecryptParams.getCipher());
		clProcess.addCommandLineStr("-pbkdf2");
		clProcess.addCommandLineStr("-in");
		clProcess.addCommandLineStr(encryptDecryptParams.getInputFilePath());
		clProcess.addCommandLineStr("-out");
		clProcess.addCommandLineStr(encryptDecryptParams.getOutputFilePath());
		clProcess.addCommandLineStr("-pass");
		clProcess.addCommandLineStr("pass:" + encryptDecryptParams.getPassPhrase());
		
		if(encryptDecryptParams.getAddSaltEnabled() == false)
		{
			clProcess.addCommandLineStr("-nosalt");
		}

		String cmdRetStr = clProcess.runCommand();
				
		clProcess.clearCommandLineStr();
		
		return cmdRetStr;
	}

	public String asymmetricEncrypt(EncryptDecryptParams encryptDecryptParams) 
	{
		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("pkeyutl");
		clProcess.addCommandLineStr("-encrypt");
		clProcess.addCommandLineStr("-pubin");
		clProcess.addCommandLineStr("-inkey");
		clProcess.addCommandLineStr(encryptDecryptParams.getKeyFilePath());
		clProcess.addCommandLineStr("-in");
		clProcess.addCommandLineStr(encryptDecryptParams.getInputFilePath());
		
		if(encryptDecryptParams.getEnableRSAOaep() == true)
		{
			clProcess.addCommandLineStr("-pkeyopt"); 
			clProcess.addCommandLineStr("rsa_padding_mode:oaep");
			clProcess.addCommandLineStr("-pkeyopt");
			clProcess.addCommandLineStr("rsa_oaep_md:" + encryptDecryptParams.getHashFunction().replaceFirst("-", ""));
			clProcess.addCommandLineStr("-pkeyopt");
			clProcess.addCommandLineStr("rsa_mgf1_md:" + encryptDecryptParams.getHashFunction().replaceFirst("-", ""));
		}
		
		clProcess.addCommandLineStr("-out");
		clProcess.addCommandLineStr(encryptDecryptParams.getOutputFilePath());
		
		String cmdRetStr = clProcess.runCommand();
		
		if(cmdRetStr.compareTo("") != 0)
		{
			if(encryptDecryptParams.getEnableRSAOaep() == true)
			{
				cmdRetStr += "\nRSA Oaep Error: Hash Function not supported for given RSA key length, key length should be more than digest length";
			}
		}
		
		clProcess.clearCommandLineStr();
		
		return cmdRetStr;
	}

	public String symmetricDecrypt(EncryptDecryptParams encryptDecryptParams) 
	{
		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("enc");
		clProcess.addCommandLineStr(encryptDecryptParams.getCipher());
		clProcess.addCommandLineStr("-pbkdf2");
		clProcess.addCommandLineStr("-d");
		clProcess.addCommandLineStr("-in");
		clProcess.addCommandLineStr(encryptDecryptParams.getInputFilePath());
		clProcess.addCommandLineStr("-out");
		clProcess.addCommandLineStr(encryptDecryptParams.getOutputFilePath());
		clProcess.addCommandLineStr("-pass");
		clProcess.addCommandLineStr("pass:" + encryptDecryptParams.getPassPhrase());
		
		if(encryptDecryptParams.getAddSaltEnabled() == false)
		{
			clProcess.addCommandLineStr("-nosalt");
		}
		
		String cmdRetStr = clProcess.runCommand();
		
		clProcess.clearCommandLineStr();
		
		return cmdRetStr;
	}

	public String asymmetricDecrypt(EncryptDecryptParams encryptDecryptParams) 
	{
		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("pkeyutl");
		clProcess.addCommandLineStr("-decrypt");
		clProcess.addCommandLineStr("-inkey");
		clProcess.addCommandLineStr(encryptDecryptParams.getKeyFilePath());
		clProcess.addCommandLineStr("-in");
		clProcess.addCommandLineStr(encryptDecryptParams.getInputFilePath());
		
		if(encryptDecryptParams.getEnableRSAOaep()== true)
		{
			clProcess.addCommandLineStr("-pkeyopt"); 
			clProcess.addCommandLineStr("rsa_padding_mode:oaep");
			clProcess.addCommandLineStr("-pkeyopt");
			clProcess.addCommandLineStr("rsa_oaep_md:" + encryptDecryptParams.getHashFunction());
			clProcess.addCommandLineStr("-pkeyopt");
			clProcess.addCommandLineStr("rsa_mgf1_md:" + encryptDecryptParams.getHashFunction());
		}
		
		clProcess.addCommandLineStr("-out");
		clProcess.addCommandLineStr(encryptDecryptParams.getOutputFilePath());
		
		String cmdRetStr = clProcess.runCommand();
		
		clProcess.clearCommandLineStr();
		
		return cmdRetStr;
	}

	public String generateHash(EncryptDecryptParams encryptDecryptParams) 
	{
		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("dgst");
		clProcess.addCommandLineStr(encryptDecryptParams.getHashFunction());
		
		if(encryptDecryptParams.getBinaryOutputFileEnabled()== true)
		{
			clProcess.addCommandLineStr("-binary"); 
		}
		
		clProcess.addCommandLineStr("-out");
		clProcess.addCommandLineStr(encryptDecryptParams.getOutputFilePath());
		clProcess.addCommandLineStr(encryptDecryptParams.getInputFilePath());
		
		String cmdRetStr = clProcess.runCommand();
		
		clProcess.clearCommandLineStr();
		
		return cmdRetStr;
	}

	public String generateCMac(EncryptDecryptParams encryptDecryptParams) 
	{
		int passwdLen = calculatePasswordLenghtforCMac(encryptDecryptParams);
		
		if(passwdLen != 0)
		{
			return "!!! Password length should be " + passwdLen + " !!!";
		}
		
		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("dgst");
		clProcess.addCommandLineStr(encryptDecryptParams.getHashFunction());
		
		if(encryptDecryptParams.getBinaryOutputFileEnabled() == true)
			
		{
			clProcess.addCommandLineStr("-binary"); 
		}
		
		clProcess.addCommandLineStr("-mac");
		clProcess.addCommandLineStr("cmac");
		clProcess.addCommandLineStr("-macopt");
		clProcess.addCommandLineStr("cipher:"  + encryptDecryptParams.getCipher());
		clProcess.addCommandLineStr("-macopt");
		clProcess.addCommandLineStr("key:"  + encryptDecryptParams.getPassPhrase());
		
		clProcess.addCommandLineStr("-out");
		clProcess.addCommandLineStr(encryptDecryptParams.getOutputFilePath());
		clProcess.addCommandLineStr(encryptDecryptParams.getInputFilePath());
		
		String cmdRetStr = clProcess.runCommand();
		
		clProcess.clearCommandLineStr();
		
		return cmdRetStr;
	}

	public String generateHMac(EncryptDecryptParams encryptDecryptParams) 
	{
		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("dgst");
		clProcess.addCommandLineStr(encryptDecryptParams.getHashFunction());
		
		if(encryptDecryptParams.getBinaryOutputFileEnabled( )== true)
		{
			clProcess.addCommandLineStr("-binary"); 
		}
		
		clProcess.addCommandLineStr("-mac");
		clProcess.addCommandLineStr("hmac");
		clProcess.addCommandLineStr("-macopt");
		clProcess.addCommandLineStr("key:"  + encryptDecryptParams.getPassPhrase());
		
		clProcess.addCommandLineStr("-out");
		clProcess.addCommandLineStr(encryptDecryptParams.getOutputFilePath());
		clProcess.addCommandLineStr(encryptDecryptParams.getInputFilePath());
		
		String cmdRetStr = clProcess.runCommand();
		
		clProcess.clearCommandLineStr();
		
		return cmdRetStr;
	}
	
	private int calculatePasswordLenghtforCMac(EncryptDecryptParams encryptDecryptParams) 
	{
		int		passwdLen = 8;
		String	cipherName = encryptDecryptParams.getCipher();
		
		if(cipherName.contains("128") || cipherName.contains("des-ede-cbc") || cipherName.contains("idea-cbc")  || cipherName.contains("seed-cbc") || cipherName.contains("sm4-cbc"))
		{
			passwdLen = 16;
		}
		
		if(cipherName.contains("192") || cipherName.contains("des-ede3-cbc") || cipherName.contains("desx-cb"))
		{
			passwdLen = 24;
		}
		
		if(cipherName.contains("256"))
		{
			passwdLen = 32;
		}

		if(encryptDecryptParams.getPassPhrase().length() == passwdLen)
		{
			return 0;
		}
		
		return passwdLen;
	}
	
	public static String convertFileToHex(String fileName) throws IOException  
	{
        StringBuilder result = new StringBuilder();
        StringBuilder hex = new StringBuilder();
        StringBuilder input = new StringBuilder();

        int count = 0;
        int value;

        try (InputStream inputStream = Files.newInputStream(Paths.get(fileName))) 
        {
			while ((value = inputStream.read()) != -1) 
			{

			    hex.append(String.format("%02X ", value));

			    if (!Character.isISOControl(value)) 
			    {
			        input.append((char) value);
			    } 
			    else 
			    {
			        input.append(UNKNOWN_CHARACTER);
			    }

			    if (count == 14) 
			    {
			        result.append(String.format("%-60s | %s%n", hex, input));
			        hex.setLength(0);
			        input.setLength(0);
			        count = 0;
			    } 
			    else 
			    {
			        count++;
			    }
			}
		}
        
        if (count > 0) 
        {
            result.append(String.format("%-60s | %s%n", hex, input));
        }

        return result.toString();
    }
}