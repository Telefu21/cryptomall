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
		String cmdRetStr = "";
		
		if(keygenParams.getKeyGenAlgo().compareTo(KeyGenerateParams.KEYGEN_ALGO_SELECT_DH) == 0
		|| keygenParams.getKeyGenAlgo().compareTo(KeyGenerateParams.KEYGEN_ALGO_SELECT_DSA) == 0)
		{
			cmdRetStr = privKeyGenerate(keygenParams);
			
			cmdRetStr += privKeyGenerateFromParamFile(keygenParams);
		}
		else
		{
			cmdRetStr = privKeyGenerate(keygenParams);
		}
		
		cmdRetStr += pubKeyGenerate(keygenParams);
		
		cmdRetStr += privKeyView(keygenParams);
		
		return cmdRetStr;
	}
	
	public String privKeyGenerateFromParamFile(KeyGenerateParams keygenParams) 
	{
		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("genpkey");
		clProcess.addCommandLineStr("-paramfile");
		clProcess.addCommandLineStr(keygenParams.getParamFilePath());
		clProcess.addCommandLineStr("-out");
		clProcess.addCommandLineStr(keygenParams.getPrivKeyFilePath());

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
	
	public String privKeyGenerate(KeyGenerateParams keygenParams) 
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
		
		if(keygenParams.getKeyGenAlgo().compareTo(KeyGenerateParams.KEYGEN_ALGO_SELECT_DH) == 0
		|| keygenParams.getKeyGenAlgo().compareTo(KeyGenerateParams.KEYGEN_ALGO_SELECT_DSA) == 0)
		{
			clProcess.addCommandLineStr(keygenParams.getParamFilePath());
		}
		else
		{
			clProcess.addCommandLineStr(keygenParams.getPrivKeyFilePath());
		}
		
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

		if(keygenParams.getEncryptKeyFile() == true && !(keygenParams.getKeyGenAlgo().compareTo(KeyGenerateParams.KEYGEN_ALGO_SELECT_DH) == 0 || keygenParams.getKeyGenAlgo().compareTo(KeyGenerateParams.KEYGEN_ALGO_SELECT_DSA) == 0))	
		{
			clProcess.addCommandLineStr(keygenParams.getFileEncryptionCipher());
			clProcess.addCommandLineStr("-pass");
			clProcess.addCommandLineStr("pass:" + keygenParams.getFileEncryptionPassword());
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
		clProcess.addCommandLineStr(keygenParams.getKeyFileFormat());
		clProcess.addCommandLineStr("-out");
		clProcess.addCommandLineStr(keygenParams.getPubKeyFilePath());
		clProcess.addCommandLineStr("-inform");
		clProcess.addCommandLineStr(keygenParams.getKeyFileFormat());
		clProcess.addCommandLineStr("-in");
		clProcess.addCommandLineStr(keygenParams.getPrivKeyFilePath());
		
		if(keygenParams.getEncryptKeyFile() == true)	
		{
			clProcess.addCommandLineStr("-passin");
			clProcess.addCommandLineStr("pass:" + keygenParams.getFileEncryptionPassword());
		}
		
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
		clProcess.addCommandLineStr(keygenParams.getKeyFileFormat());
		clProcess.addCommandLineStr("-in");
		clProcess.addCommandLineStr(keygenParams.getPrivKeyFilePath());
		
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
		clProcess.addCommandLineStr(keygenParams.getKeyFileFormat());
		clProcess.addCommandLineStr("-pubin");
		clProcess.addCommandLineStr("-in");
		clProcess.addCommandLineStr(keygenParams.getPubKeyFilePath());
		clProcess.addCommandLineStr("-text");
		
		String cmdRetStr = clProcess.runCommand();
		
		clProcess.clearCommandLineStr();
		
		return cmdRetStr;
	}
}