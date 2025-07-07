package io.ozgard.cryptomall.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.ozgard.cryptomall.model.CommandLineProcess;
import io.ozgard.cryptomall.params.CertificateParams;
import io.ozgard.cryptomall.params.EncryptDecryptParams;
import io.ozgard.cryptomall.params.KeyGenerateParams;
import io.ozgard.cryptomall.params.SignVerifyPrimeParams;
import io.ozgard.cryptomall.utility.Utility;

@Service
public class OpenSslService 
{
	@Autowired
	CommandLineProcess clProcess;
	
	public String [] getListElipticCurveName() 
	{		
		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("ecparam");
		clProcess.addCommandLineStr("-list_curves");
		
		String [] ecList = clProcess.runCommand().split("\n");
		
		ArrayList<String> ecArrayList = new ArrayList<>(); 
		
		clProcess.clearCommandLineStr();
		
		for(int i = 0; i < ecList.length; i++)
		{			
			if(ecList[i].contains(":") == true)
			{
				ecArrayList.add(ecList[i].split(":")[0].replace(" " , ""));
			}
		}
		
		return ecArrayList.toArray(new String[ecArrayList.size()]);
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
			cmdRetStr += paramFileGenerate(keygenParams);
			
			cmdRetStr += privKeyGenerate(keygenParams);
		}
		else
		{
			cmdRetStr += privKeyGenerate(keygenParams);
		}

		cmdRetStr += "Private Key file generated and written to --> " + keygenParams.getOutputFilePath() + "\n";
		
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
		
		cmdRetStr += "\n" + "Converted file save to --> " + keygenParams.getOutputFilePath() + "\n";
		
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
		
		cmdRetStr += "\n" + "Converted file saved to --> " + keygenParams.getOutputFilePath() + "\n";
		
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
		
		cmdRetStr += "\n" + "Converted file saved to --> " + keygenParams.getOutputFilePath() + "\n";
		
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
		
		cmdRetStr += "\n" + "Converted file saved to --> " + keygenParams.getOutputFilePath() + "\n";
		
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
		
		clProcess.addCommandLineStr("-hex");
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
		clProcess.addCommandLineStr("hexkey:"  + encryptDecryptParams.getPassPhrase());
		
		clProcess.addCommandLineStr("-hex");
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
		clProcess.addCommandLineStr("hexkey:"  + encryptDecryptParams.getPassPhrase());
		
		clProcess.addCommandLineStr("-hex");
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

		if(encryptDecryptParams.getPassPhrase().length() == (2 * passwdLen)) // Hex input 2 digit corresponds to 1 number
		{
			return 0;
		}
		
		return passwdLen;
	}

	public String generatePrime(SignVerifyPrimeParams signVerifyPrimeParams) 
	{
		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("prime");
		clProcess.addCommandLineStr("-generate");
		clProcess.addCommandLineStr("-bits");
		clProcess.addCommandLineStr(signVerifyPrimeParams.getPrimeLength());
		
		if(signVerifyPrimeParams.isHexOutPrime()== true)
		{
			clProcess.addCommandLineStr("-hex"); 
		}
		
		if(signVerifyPrimeParams.isSafePrime()== true)
		{
			clProcess.addCommandLineStr("-safe"); 
		}
		
		String  cmdRetStr = "Generated Prime Number: \n";
		
		cmdRetStr += clProcess.runCommand();
		
		clProcess.clearCommandLineStr();
		
		return cmdRetStr;
	}

	public String generateSignature(SignVerifyPrimeParams signVerifyPrimeParams) 
	{
		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("dgst");
		clProcess.addCommandLineStr(signVerifyPrimeParams.getHashFunction());
		clProcess.addCommandLineStr("-sign");
		clProcess.addCommandLineStr(signVerifyPrimeParams.getKeyFilePath());
		
		if(signVerifyPrimeParams.isRsaPssEnabled( )== true)
		{
			clProcess.addCommandLineStr("-sigopt"); 
			clProcess.addCommandLineStr("rsa_padding_mode:pss");
			clProcess.addCommandLineStr("-sigopt"); 
			clProcess.addCommandLineStr("rsa_pss_saltlen:" + signVerifyPrimeParams.getSaltLen());
		}
		
		clProcess.addCommandLineStr("-out");
		clProcess.addCommandLineStr(signVerifyPrimeParams.getOutputFilePath());
		clProcess.addCommandLineStr(signVerifyPrimeParams.getInputFilePath());
		
		String cmdRetStr = clProcess.runCommand();
		
		cmdRetStr += "Signature file saved to --> " + signVerifyPrimeParams.getOutputFilePath();
		
		clProcess.clearCommandLineStr();
		
		return cmdRetStr;
	}

	public String verifySignature(SignVerifyPrimeParams signVerifyPrimeParams) 
	{
		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("dgst");
		clProcess.addCommandLineStr(signVerifyPrimeParams.getHashFunction());
		clProcess.addCommandLineStr("-verify");
		clProcess.addCommandLineStr(signVerifyPrimeParams.getKeyFilePath());
		
		if(signVerifyPrimeParams.isRsaPssEnabled( )== true)
		{
			clProcess.addCommandLineStr("-sigopt"); 
			clProcess.addCommandLineStr("rsa_padding_mode:pss");
			clProcess.addCommandLineStr("-sigopt"); 
			clProcess.addCommandLineStr("rsa_pss_saltlen:" + signVerifyPrimeParams.getSaltLen());
		}
		
		clProcess.addCommandLineStr("-signature");
		clProcess.addCommandLineStr(signVerifyPrimeParams.getSignatureFilePath());
		clProcess.addCommandLineStr(signVerifyPrimeParams.getInputFilePath());
		
		String cmdRetStr = "Verification Result: \n";
		cmdRetStr += clProcess.runCommand();
		
		clProcess.clearCommandLineStr();
		
		return cmdRetStr;
	}

	public String convertFileViewCertificate(KeyGenerateParams keygenParams) 
	{
		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("x509");
		clProcess.addCommandLineStr("-in");
		clProcess.addCommandLineStr(keygenParams.getInputFilePath());
		clProcess.addCommandLineStr("-text"); 
		clProcess.addCommandLineStr("-noout");

		String cmdRetStr = clProcess.runCommand();
		
		clProcess.clearCommandLineStr();
		
		return cmdRetStr;
	}
	
	public String convertFileViewCsrCertificate(KeyGenerateParams keygenParams) 
	{
		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("req");
		clProcess.addCommandLineStr("-in");
		clProcess.addCommandLineStr(keygenParams.getInputFilePath());
		clProcess.addCommandLineStr("-text"); 
		clProcess.addCommandLineStr("-noout");

		String cmdRetStr = clProcess.runCommand();
		
		clProcess.clearCommandLineStr();
		
		return cmdRetStr;
	}

	public String convertFileViewCrlCertificate(KeyGenerateParams keygenParams) 
	{
		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("crl");
		clProcess.addCommandLineStr("-in");
		clProcess.addCommandLineStr(keygenParams.getInputFilePath());
		clProcess.addCommandLineStr("-text"); 
		clProcess.addCommandLineStr("-noout");

		String cmdRetStr = clProcess.runCommand();
		
		clProcess.clearCommandLineStr();
		
		return cmdRetStr;
	}

	public String convertFilePemToAnsi(KeyGenerateParams keygenParams) 
	{
		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("asn1parse");
		clProcess.addCommandLineStr("-in");
		clProcess.addCommandLineStr(keygenParams.getInputFilePath());

		String cmdRetStr = clProcess.runCommand();
		
		clProcess.clearCommandLineStr();
		
		return cmdRetStr;
	}
	
	public void generateConfigFilesToWorkingDirectory(String directoryPath, String fileName) 
	{		
		String[] path = directoryPath.split(Utility.getDoublePathSeperator());
		
		String workingDirectorypathWith1BackSlash = path[0];
		String workingDirectorypathWith2BackSlash = path[0];
		
		for(int i=1; i < path.length ; i++)
		{
			workingDirectorypathWith1BackSlash = workingDirectorypathWith1BackSlash + Utility.getPathSeperator()+ path[i];
			workingDirectorypathWith2BackSlash = workingDirectorypathWith2BackSlash + Utility.getDoublePathSeperator()+ path[i];
		}
		
		String indexFile = workingDirectorypathWith2BackSlash  + Utility.getDoublePathSeperator() + fileName + "index"; 
		String serialFile = workingDirectorypathWith2BackSlash  + Utility.getDoublePathSeperator() + fileName + "serial";  
		String configFile = workingDirectorypathWith2BackSlash  + Utility.getDoublePathSeperator() + fileName +".config"; 
		
		File index = new File(indexFile);
		File serial = new File(serialFile);
		File config = new File(configFile);
		
		try 
		{	
			index.createNewFile();
			serial.createNewFile();
			config.createNewFile();
			
			FileWriter indexWriter = new FileWriter(indexFile);
			FileWriter serialWriter = new FileWriter(serialFile);
			FileWriter configWriter = new FileWriter(configFile);
			
			indexWriter.write("[empty]");
			indexWriter.close();
			
			serialWriter.write("00");
			serialWriter.close();
			
			String policyAndExtStr = "[ policy_any ]\r\n"
					+ "commonName             = supplied\r\n"
					+ "countryName            = optional\r\n"
					+ "stateOrProvinceName    = optional\r\n"
					+ "organizationName       = optional\r\n"
					+ "organizationalUnitName = optional\r\n"
					+ "localityName		   	  = optional\r\n"
					+ "title		          = optional\r\n"
					+ "serialNumber           = optional\r\n"
					+ "givenName              = optional\r\n"
					+ "surname                = optional\r\n"
					+ "initials               = optional\r\n"
					+ "pseudonym              = optional\r\n"
					+ "street                 = optional\r\n"
					+ "userId                 = optional\r\n"
					+ "dnQualifier            = optional\r\n"
					+ "generationQualifier    = optional\r\n"
					+ "domainComponent        = optional\r\n"
					+ "\r\n"
					+ "[ v3_ext ]\r\n"
					+ "basicConstraints = critical,CA:true\r\n"
					+ "keyUsage         = critical,keyCertSign";
			
			configWriter.write("[ CA_default]\r\n"
					+ "database        = " + Utility.getDoubleQuote() + workingDirectorypathWith2BackSlash + Utility.getDoublePathSeperator() + "rootindex" + Utility.getDoubleQuote() + "\r\n"
					+ "serial          = " + Utility.getDoubleQuote() + workingDirectorypathWith2BackSlash + Utility.getDoublePathSeperator() + "rootserial" + Utility.getDoubleQuote() + "\r\n"
					+ "\r\n"
					+ policyAndExtStr);
			
			configWriter.close();
		} 
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	private String createSubjectAttribsInStr(String [] subjAttribsCertStr, String [] subjAttribsTableInput) 
	{
		String subjCertAttributes = "-subj \"";
	
		int minLength = subjCertAttributes.length() + 1;
				
		for(int i = 0; i < subjAttribsCertStr.length; i++)
		{
			if(subjAttribsTableInput[i].compareTo("") != 0)
			{
				subjCertAttributes += "/" + subjAttribsCertStr[i] + "=" + subjAttribsTableInput[i];
			}
		}
			
		subjCertAttributes += "\"";
		
		if(subjCertAttributes.length() <= minLength)
		{
			subjCertAttributes = "";
		}
		
		return  subjCertAttributes;
	}

	public String generateCertificates(CertificateParams certificateParams) 
	{
		String rootCsrFile = "\"" + certificateParams.getWorkingDirectory() + Utility.getPathSeperator() + "root_csr_" + LocalTime.now().getHour() + "-" + LocalTime.now().getMinute()+ "-" + LocalTime.now().getSecond() + ".pem\"";
		String rootCertFile = "\"" + certificateParams.getWorkingDirectory() + Utility.getPathSeperator() + "root_cert_" + LocalTime.now().getHour() + "-" + LocalTime.now().getMinute()+ "-" + LocalTime.now().getSecond()+ ".pem\"";
		String rootConfigFile = "\"" + certificateParams.getWorkingDirectory() + Utility.getPathSeperator() + "root.config" + "\"";
		String intermediateCsrFile = "\"" + certificateParams.getWorkingDirectory() + Utility.getPathSeperator() + "intermediate_csr_" + LocalTime.now().getHour() + "-" + LocalTime.now().getMinute()+ "-" + LocalTime.now().getSecond()+ ".pem\"";
		String intermediateCertFile = "\"" + certificateParams.getWorkingDirectory() + Utility.getPathSeperator() + "intermediate_cert_" + LocalTime.now().getHour() + "-" + LocalTime.now().getMinute()+ "-" + LocalTime.now().getSecond()+ ".pem\"";
		String intermediateConfigFile = "\"" + certificateParams.getWorkingDirectory() + Utility.getPathSeperator() + "intermediate.config" + "\"";
		String endEntityCsrFile = "\"" + certificateParams.getWorkingDirectory() + Utility.getPathSeperator() + "endEntity_csr_" + LocalTime.now().getHour() + "-" + LocalTime.now().getMinute()+ "-" + LocalTime.now().getSecond()+ ".pem\"";
		String endEntityCertFile = "\"" + certificateParams.getWorkingDirectory() + Utility.getPathSeperator() + "endEntity_cert_" + LocalTime.now().getHour() + "-" + LocalTime.now().getMinute()+ "-" + LocalTime.now().getSecond()+ ".pem\"";
		
		String [] tmp = new String[certificateParams.getCertificateParamsRows().length - 1];
		
		for(int i = 0; i < certificateParams.getCertificateParamsRows().length - 1; i++)
		{
			tmp[i] = certificateParams.getCertificateParamsRows()[i + 1].getRootCertificate();
		}
		
		String rootSubjAttribsCertStr  = createSubjectAttribsInStr(certificateParams.getSubjAttribsCertStr(), tmp);
		
		generateConfigFilesToWorkingDirectory(certificateParams.getWorkingDirectory(), "root");
		
		String rootCertLogStr = generateCsr(certificateParams.getRootKeyVerifyFilePath(), rootCsrFile, rootSubjAttribsCertStr);
		
		rootCertLogStr += generateCertificate(true, "\"" + certificateParams.getWorkingDirectory() + "\"", rootCsrFile, rootCertFile, rootCertFile, "\"" + certificateParams.getRootKeyVerifyFilePath() + "\"", certificateParams.getCertificateParamsRows()[0].getRootCertificate() , rootConfigFile,  certificateParams.getRootHashFunction().replaceAll("-", ""));
		
		for(int i = 0; i < certificateParams.getCertificateParamsRows().length - 1; i++)
		{
			tmp[i] = certificateParams.getCertificateParamsRows()[i + 1].getIntermediateCertificate();
		}
		
		String intermediateSubjAttribsCertStr  = createSubjectAttribsInStr(certificateParams.getSubjAttribsCertStr(), tmp);
		
		generateConfigFilesToWorkingDirectory(certificateParams.getWorkingDirectory(), "intermediate");
		
		String intermediateCertLogStr = generateCsr(certificateParams.getIntermediateKeyVerifyFilePath(), intermediateCsrFile, intermediateSubjAttribsCertStr);
		
		intermediateCertLogStr += generateCertificate(false, "\"" + certificateParams.getWorkingDirectory() + "\"", intermediateCsrFile, intermediateCertFile, rootCertFile, "\"" + certificateParams.getRootKeyVerifyFilePath() + "\"", certificateParams.getCertificateParamsRows()[0].getIntermediateCertificate(), rootConfigFile,  certificateParams.getIntermediateHashFunction().replaceAll("-", ""));
		
		for(int i = 0; i < certificateParams.getCertificateParamsRows().length - 1; i++)
		{
			tmp[i] = certificateParams.getCertificateParamsRows()[i + 1].getEndEntitiyCertificate();
		}
		
		String endEntitySubjAttribsCertStr  = createSubjectAttribsInStr(certificateParams.getSubjAttribsCertStr(), tmp);
		
		String endEntityCertLogStr = generateCsr(certificateParams.getEndEntityKeyVerifyFilePath(), endEntityCsrFile, endEntitySubjAttribsCertStr);
		
		endEntityCertLogStr += generateCertificate(false, "\"" + certificateParams.getWorkingDirectory() + "\"", endEntityCsrFile, endEntityCertFile, intermediateCertFile, "\"" + certificateParams.getIntermediateKeyVerifyFilePath() + "\"", certificateParams.getCertificateParamsRows()[0].getIntermediateCertificate(), intermediateConfigFile,  certificateParams.getEndEntityHashFunction().replaceAll("-", ""));

		try 
		{
			Thread.sleep(400);

			deleteJunkFilesAtWs(certificateParams);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		return (rootCertLogStr + intermediateCertLogStr + endEntityCertLogStr);
	}
	
	private String generateCsr(String keyFileName, String csrFileName, String subjectAttribute) 
	{
		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("req");
		clProcess.addCommandLineStr("-new");
		clProcess.addCommandLineStr("-key");
		clProcess.addCommandLineStr(keyFileName);
		clProcess.addCommandLineStr("-out");
		clProcess.addCommandLineStr(csrFileName);
		
		clProcess.addCommandLineStr(subjectAttribute); 
		
		String cmdRetStr = clProcess.runCommand();
		
		cmdRetStr += "Csr file saved to --> " + csrFileName + "\n";
		
		clProcess.clearCommandLineStr();
		
		return cmdRetStr;
	}
	
	protected String generateCertificate(boolean isSelfSigned, String workingDirectory, String csrFileName, String certFileName, String CACertFile, String CAKeyFile, String daysToExpire, String configFileName, String hashMethod) 
	{
		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("ca");
		clProcess.addCommandLineStr("-in"); 
		clProcess.addCommandLineStr(csrFileName);
		clProcess.addCommandLineStr("-out"); 
		clProcess.addCommandLineStr(certFileName);
		clProcess.addCommandLineStr("-extensions"); 
		clProcess.addCommandLineStr("v3_ext");
		clProcess.addCommandLineStr("-days"); 
		clProcess.addCommandLineStr(daysToExpire);
		clProcess.addCommandLineStr("-cert"); 
		clProcess.addCommandLineStr(CACertFile);
		clProcess.addCommandLineStr("-keyfile"); 
		clProcess.addCommandLineStr(CAKeyFile);
		clProcess.addCommandLineStr("-name"); 
		clProcess.addCommandLineStr("CA_default");
		clProcess.addCommandLineStr("-policy"); 
		clProcess.addCommandLineStr("policy_any");
		clProcess.addCommandLineStr("-outdir"); 
		clProcess.addCommandLineStr(workingDirectory);
		clProcess.addCommandLineStr("-config");
		clProcess.addCommandLineStr(configFileName);
		clProcess.addCommandLineStr("-md");
		clProcess.addCommandLineStr(hashMethod);
			
		if(isSelfSigned)
		{
			clProcess.addCommandLineStr("-selfsign");
		}
		
		clProcess.addCommandLineStr("-notext");
			
		String cmdRetStr = clProcess.runAndConfirmCommand();
		
		cmdRetStr += "Certificate file saved to --> " + certFileName + "\n";
		
		clProcess.clearCommandLineStr();
		
		return cmdRetStr;
	}
	
	private void deleteJunkFilesAtWs(CertificateParams certificateParams) 
	{
		String os = System.getProperty("os.name").toLowerCase();
		
		if(os.contains("win"))
		{
			clProcess.addCommandLineStr("del"); 
		}
		
		if(os.contains("nix") || os.contains("nux") || os.contains("aix"))
		{
			clProcess.addCommandLineStr("rm"); 
		}
		
		clProcess.addCommandLineStr(Utility.getDoubleQuote() + certificateParams.getWorkingDirectory()  + Utility.getPathSeperator() + "*serial*" + Utility.getDoubleQuote());
		clProcess.addCommandLineStr(Utility.getDoubleQuote() + certificateParams.getWorkingDirectory()  + Utility.getPathSeperator() + "*index*" + Utility.getDoubleQuote());
		clProcess.addCommandLineStr(Utility.getDoubleQuote() + certificateParams.getWorkingDirectory()  + Utility.getPathSeperator() + "*old*" + Utility.getDoubleQuote());
		clProcess.addCommandLineStr(Utility.getDoubleQuote() + certificateParams.getWorkingDirectory()  + Utility.getPathSeperator() + "*attr*" + Utility.getDoubleQuote());
		clProcess.addCommandLineStr(Utility.getDoubleQuote() + certificateParams.getWorkingDirectory()  + Utility.getPathSeperator() + "*config*" + Utility.getDoubleQuote());
		clProcess.addCommandLineStr(Utility.getDoubleQuote() + certificateParams.getWorkingDirectory()  + Utility.getPathSeperator() + "00.pem" + Utility.getDoubleQuote());
		clProcess.addCommandLineStr(Utility.getDoubleQuote() + certificateParams.getWorkingDirectory()  + Utility.getPathSeperator() + "01.pem" + Utility.getDoubleQuote());
		clProcess.addCommandLineStr(Utility.getDoubleQuote() + certificateParams.getWorkingDirectory()  + Utility.getPathSeperator() + "02.pem" + Utility.getDoubleQuote());
		clProcess.runCommand();
		
		clProcess.runAndConfirmCommand();
		
		clProcess.clearCommandLineStr();
	}
	
	public String verifyChainOfCertificates(CertificateParams certificateParams) 
	{				
		String rootCertFile = "\"" + certificateParams.getRootKeyVerifyFilePath() + "\"";
		String intermediateCertFile = "\"" + certificateParams.getIntermediateKeyVerifyFilePath() + "\"";
		String endEntityCertFile = "\"" + certificateParams.getEndEntityKeyVerifyFilePath() + "\"";

		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("verify");
		clProcess.addCommandLineStr("-CAfile");
		
		if(certificateParams.getIsTwoChainVerifySelected() == false)
		{
			clProcess.addCommandLineStr(rootCertFile);
			clProcess.addCommandLineStr("-untrusted");
			clProcess.addCommandLineStr(intermediateCertFile);
		}
		
		if(certificateParams.getIsTwoChainVerifySelected() == true)
		{
			clProcess.addCommandLineStr(intermediateCertFile);
		}
		
		clProcess.addCommandLineStr(endEntityCertFile);
		
		String cmdRetStr = clProcess.runCommand();
		
		clProcess.clearCommandLineStr();
		
		return cmdRetStr;
	}
	
	public boolean isOpensslInstalled() 
	{
		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("version");
		
		String cmdRetStr = clProcess.runCommand();
		
		clProcess.clearCommandLineStr();
		
		if(cmdRetStr.toLowerCase().contains("not recognized") == true)
		{
			return false;
		}

		return true;
	}
}