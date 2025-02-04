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
	@Autowired
	KeyGenerateParams keygenParams;
	
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
		String prvKeyFile = "\"" + keygenParams.getWorkingDirectory() + "\\privkey_" + keygenParams.getElepticCurveName() + "_ec.key\"";
		String pubKeyFile = "\"" + keygenParams.getWorkingDirectory() + "\\pubkey_" + keygenParams.getElepticCurveName() + "_ec." + keygenParams.getKeyFileFormat().toLowerCase() + "\"";
		
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
		
		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("ec");
		clProcess.addCommandLineStr("-pubout");
		clProcess.addCommandLineStr("-in");
		clProcess.addCommandLineStr(prvKeyFile);
		clProcess.addCommandLineStr("-out");
		clProcess.addCommandLineStr(pubKeyFile);
		clProcess.addCommandLineStr("-outform");
		clProcess.addCommandLineStr(keygenParams.getKeyFileFormat());
		
		clProcess.runCommand();
		
		clProcess.clearCommandLineStr();
		
		clProcess.addCommandLineStr("openssl"); 
		clProcess.addCommandLineStr("ec");
		clProcess.addCommandLineStr("-in");
		clProcess.addCommandLineStr(prvKeyFile);
		clProcess.addCommandLineStr("-noout");
		clProcess.addCommandLineStr("-text");
		
		cmdRetStr = clProcess.runCommand();
		
		return cmdRetStr;
	}   
}
