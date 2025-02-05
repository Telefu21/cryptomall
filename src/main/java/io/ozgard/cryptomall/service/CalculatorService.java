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
		if(keygenParams.getKeyGenAlgo().compareTo(KeyGenerateParams.KEYGEN_ALGO_SELECT_RSA) == 0)
		{
			return keyGenerateRSA(keygenParams);
		}
		
		if(keygenParams.getKeyGenAlgo().compareTo(KeyGenerateParams.KEYGEN_ALGO_SELECT_ECC) == 0)
		{
			return keyGenerateECC(keygenParams);
		}
		
		return null;
	}

	private String keyGenerateECC(KeyGenerateParams keygenParams) 
	{
		String prvKeyFile = "\"" + keygenParams.getWorkingDirectory() + "\\privkey_" + keygenParams.getElepticCurveName() + "_ec.key\"";
		String pubKeyFile = "\"" + keygenParams.getWorkingDirectory() + "\\pubkey_" + keygenParams.getElepticCurveName() + "_ec." + keygenParams.getKeyFileFormat().toLowerCase() + "\"";
		String prvKeyFilePkcs8 = "\"" + keygenParams.getWorkingDirectory() + "\\privkey_" + keygenParams.getElepticCurveName() + "_pkcs8_ec.pem\"";
		
		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("ecparam");
		clProcess.addCommandLineStr("-genkey");
		clProcess.addCommandLineStr("-name");
		clProcess.addCommandLineStr(keygenParams.getElepticCurveName());
		clProcess.addCommandLineStr("-out");
		clProcess.addCommandLineStr(prvKeyFile);
						
		String cmdRetStr = clProcess.runCommand();
		
		clProcess.clearCommandLineStr();
		
		if(cmdRetStr.compareTo("") != 0)
		{
			return cmdRetStr;
		}
		
		cmdRetStr += "\n ------------------------------------------------ \n";
		
		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("pkcs8");
		
		if(keygenParams.getEncryptKeyFile() == true)	
		{
			clProcess.addCommandLineStr("-passout");
			clProcess.addCommandLineStr("pass::" + new String(keygenParams.getFileEncryptionPassword()));
		}
		else
		{
			clProcess.addCommandLineStr("-nocrypt");
		}
		
		clProcess.addCommandLineStr("-topk8");
		clProcess.addCommandLineStr("-in");
		clProcess.addCommandLineStr(prvKeyFile);
		clProcess.addCommandLineStr("-out");
		clProcess.addCommandLineStr(prvKeyFilePkcs8);
		
		cmdRetStr += clProcess.runCommand();
		cmdRetStr += "\n ------------------------------------------------ \n";
		clProcess.clearCommandLineStr();
		
		
		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("ec");
		clProcess.addCommandLineStr("-pubout");
		clProcess.addCommandLineStr("-in");
		clProcess.addCommandLineStr(prvKeyFilePkcs8);
		
		if(keygenParams.getEncryptKeyFile() == true)	
		{
			clProcess.addCommandLineStr("-passin");
			clProcess.addCommandLineStr("pass::" + keygenParams.getFileEncryptionPassword());
		}
		
		clProcess.addCommandLineStr("-out");
		clProcess.addCommandLineStr(pubKeyFile);
		clProcess.addCommandLineStr("-outform");
		clProcess.addCommandLineStr(keygenParams.getKeyFileFormat());
		
		cmdRetStr += clProcess.runCommand();
		cmdRetStr += "\n ------------------------------------------------ \n";		
		clProcess.clearCommandLineStr();
		
		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("ec");
		
		if(keygenParams.getEncryptKeyFile() == true)	
		{
			clProcess.addCommandLineStr("-passin");
			clProcess.addCommandLineStr("pass::" + keygenParams.getFileEncryptionPassword());
		}
		
		clProcess.addCommandLineStr("-in");
		clProcess.addCommandLineStr(prvKeyFilePkcs8);
		clProcess.addCommandLineStr("-noout");
		clProcess.addCommandLineStr("-text");
		
		cmdRetStr += clProcess.runCommand();
		
		clProcess.clearCommandLineStr();
		
		return cmdRetStr;
	}

	private String keyGenerateRSA(KeyGenerateParams keygenParams) 
	{
		String prvKeyFile = "\"" + keygenParams.getWorkingDirectory() + "\\privkey_" + keygenParams.getRsaKeyLength() + "_rsa.key\"";
		String pubKeyFile = "\"" + keygenParams.getWorkingDirectory() + "\\pubkey_" + keygenParams.getRsaKeyLength() + "_rsa." + keygenParams.getKeyFileFormat().toLowerCase() + "\"";
		
		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("genrsa");
		
		if(keygenParams.getEncryptKeyFile() == true)	
		{
			clProcess.addCommandLineStr(keygenParams.getFileEncryptionCipher().replaceAll(" ", ""));
			clProcess.addCommandLineStr("-passout");
			clProcess.addCommandLineStr("pass::" + new String(keygenParams.getFileEncryptionPassword()));
		}
		
		clProcess.addCommandLineStr("-out");
		clProcess.addCommandLineStr(prvKeyFile);
		
		clProcess.addCommandLineStr(keygenParams.getRsaKeyLength());
		
		
		String cmdRetStr = clProcess.runCommand();
		cmdRetStr += "\n ------------------------------------------------ \n";
						
		clProcess.clearCommandLineStr();
		
		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("rsa");
		clProcess.addCommandLineStr("-pubout");
		clProcess.addCommandLineStr("-in");
		clProcess.addCommandLineStr(prvKeyFile);
		
		if(keygenParams.getEncryptKeyFile() == true)	
		{
			clProcess.addCommandLineStr("-passin");
			clProcess.addCommandLineStr("pass::" + keygenParams.getFileEncryptionPassword());
		}
		
		clProcess.addCommandLineStr("-outform");
		clProcess.addCommandLineStr(keygenParams.getKeyFileFormat());
		clProcess.addCommandLineStr("-out");
		clProcess.addCommandLineStr(pubKeyFile);

		cmdRetStr += clProcess.runCommand();
		cmdRetStr += "\n ------------------------------------------------ \n";
										
		clProcess.clearCommandLineStr();
		
		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("rsa");
		
		if(keygenParams.getEncryptKeyFile() == true)	
		{
			clProcess.addCommandLineStr("-passin");
			clProcess.addCommandLineStr("pass::" + keygenParams.getFileEncryptionPassword());
		}
		
		clProcess.addCommandLineStr("-in");
		clProcess.addCommandLineStr(prvKeyFile);
		clProcess.addCommandLineStr("-noout");
		clProcess.addCommandLineStr("-text");
			  
		cmdRetStr += clProcess.runCommand();
		
		clProcess.clearCommandLineStr();
		
		return cmdRetStr;
	}   
}
