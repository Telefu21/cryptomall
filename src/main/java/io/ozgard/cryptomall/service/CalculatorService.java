package io.ozgard.cryptomall.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.ozgard.cryptomall.model.CommandLineProcess;
import io.ozgard.cryptomall.params.KeyGenerateParams;

@Service
public class CalculatorService 
{
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
		String privateKeyFileName = "\"" + keygenParams.getWorkingDirectory() + "\\" + keygenParams.getKeyGenAlgo().toLowerCase() + "_privkey" + "." + keygenParams.getKeyFileFormat().toLowerCase() + "\"";
		String paramFileName = "\"" + keygenParams.getWorkingDirectory() + "\\" + keygenParams.getKeyGenAlgo().toLowerCase() + "_param" + "." + keygenParams.getKeyFileFormat().toLowerCase() + "\"";
		String cmdRetStr = "";
		
		if(keygenParams.getKeyGenAlgo().compareTo(KeyGenerateParams.KEYGEN_ALGO_SELECT_DH) == 0
		|| keygenParams.getKeyGenAlgo().compareTo(KeyGenerateParams.KEYGEN_ALGO_SELECT_DSA) == 0)
		{
			cmdRetStr = privKeyGenerate(keygenParams, paramFileName);
			
			cmdRetStr += privKeyGenerateFromParamFile(keygenParams, paramFileName, privateKeyFileName);
		}
		else
		{
			cmdRetStr = privKeyGenerate(keygenParams, privateKeyFileName);
		}
		
		String passwd = null;
		
		if(keygenParams.getEncryptKeyFile() == true)
		{
			passwd = keygenParams.getFileEncryptionPassword();
		}
		
		cmdRetStr += pubKeyGenerate(privateKeyFileName, passwd, keygenParams.getKeyFileFormat());
		
		cmdRetStr += privKeyView(privateKeyFileName, passwd, keygenParams.getKeyFileFormat());
		
		return cmdRetStr;
	}
	
	public String privKeyGenerateFromParamFile(KeyGenerateParams keygenParams, String paramFile, String privKeyFile) 
	{
		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("genpkey");
		clProcess.addCommandLineStr("-paramfile");
		clProcess.addCommandLineStr(paramFile);
		clProcess.addCommandLineStr("-out");
		clProcess.addCommandLineStr(privKeyFile);

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
	
	public String privKeyGenerate(KeyGenerateParams keygenParams, String privateKeyFile) 
	{
		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("genpkey");
		clProcess.addCommandLineStr("-outform");
		clProcess.addCommandLineStr(keygenParams.getKeyFileFormat());
		
		if(keygenParams.getKeyGenAlgo().compareTo(KeyGenerateParams.KEYGEN_ALGO_SELECT_DH) == 0
		|| keygenParams.getKeyGenAlgo().compareTo(KeyGenerateParams.KEYGEN_ALGO_SELECT_DSA) == 0)
		{
			clProcess.addCommandLineStr("-genparam");
		}
		
		clProcess.addCommandLineStr("-algorithm");
		clProcess.addCommandLineStr(keygenParams.getKeyGenAlgo());
		clProcess.addCommandLineStr("-out");
		clProcess.addCommandLineStr(privateKeyFile);
		
		if(keygenParams.getKeyGenAlgo().compareTo(KeyGenerateParams.KEYGEN_ALGO_SELECT_RSA) == 0)
		{
			clProcess.addCommandLineStr("-pkeyopt");
			clProcess.addCommandLineStr("rsa_keygen_bits:" + keygenParams.getKeyLength());
		}
		
		if(keygenParams.getKeyGenAlgo().compareTo(KeyGenerateParams.KEYGEN_ALGO_SELECT_DH) == 0)
		{
			clProcess.addCommandLineStr("-pkeyopt");
			clProcess.addCommandLineStr("dh_paramgen_prime_len:" + keygenParams.getKeyLength());
		}
		
		if(keygenParams.getKeyGenAlgo().compareTo(KeyGenerateParams.KEYGEN_ALGO_SELECT_DSA) == 0)
		{
			clProcess.addCommandLineStr("-pkeyopt");
			clProcess.addCommandLineStr("dsa_paramgen_bits:" + keygenParams.getKeyLength());
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
	
	public String pubKeyGenerate(String privKeyFileName, String passwd, String fileformat) 
	{
		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("pkey");
		clProcess.addCommandLineStr("-pubout");
		clProcess.addCommandLineStr("-outform");
		clProcess.addCommandLineStr(fileformat.toLowerCase());
		clProcess.addCommandLineStr("-out");
		clProcess.addCommandLineStr(privKeyFileName.replace("." + fileformat.toLowerCase(), "_pub" + "." + fileformat.toLowerCase()));
		clProcess.addCommandLineStr("-inform");
		clProcess.addCommandLineStr(fileformat.toLowerCase());
		clProcess.addCommandLineStr("-in");
		clProcess.addCommandLineStr(privKeyFileName);
		
		if(passwd != null)	
		{
			clProcess.addCommandLineStr("-passin");
			clProcess.addCommandLineStr("pass:" + passwd);
		}
		
		String cmdRetStr = clProcess.runCommand();
		
		clProcess.clearCommandLineStr();
		
		return cmdRetStr;
	}
	
	public String privKeyView(String privKeyFileName, String passwd, String fileformat) 
	{
		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("pkey");
		clProcess.addCommandLineStr("-text");
		clProcess.addCommandLineStr("-inform");
		clProcess.addCommandLineStr(fileformat.toLowerCase());
		clProcess.addCommandLineStr("-in");
		clProcess.addCommandLineStr(privKeyFileName);
		
		if(passwd != null)	
		{
			clProcess.addCommandLineStr("-passin");
			clProcess.addCommandLineStr("pass:" + passwd);
		}
		
		String cmdRetStr = clProcess.runCommand();
		
		clProcess.clearCommandLineStr();
		
		return cmdRetStr;
	}
	
	public String pubKeyView(String pubKeyFileName, String passwd, String fileformat) 
	{
		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("pkey");
		clProcess.addCommandLineStr("-inform");
		clProcess.addCommandLineStr(fileformat.toLowerCase());
		clProcess.addCommandLineStr("-pubin");
		clProcess.addCommandLineStr("-in");
		clProcess.addCommandLineStr(pubKeyFileName);
		clProcess.addCommandLineStr("-text");
		
		String cmdRetStr = clProcess.runCommand();
		
		clProcess.clearCommandLineStr();
		
		return cmdRetStr;
	}
}