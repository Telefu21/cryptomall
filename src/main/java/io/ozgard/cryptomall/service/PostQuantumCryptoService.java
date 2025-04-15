package io.ozgard.cryptomall.service;

import java.security.Security;

import org.bouncycastle.pqc.jcajce.provider.BouncyCastlePQCProvider;
import org.springframework.stereotype.Service;

@Service
public class PostQuantumCryptoService 
{
	public PostQuantumCryptoService() 
	{
		if (Security.getProvider("BCPQC") == null) 
		{
	        Security.addProvider(new BouncyCastlePQCProvider());
	    }
	}
	
	

}
