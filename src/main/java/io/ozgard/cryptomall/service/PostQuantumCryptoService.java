package io.ozgard.cryptomall.service;

import java.security.Security;

import org.bouncycastle.pqc.jcajce.provider.BouncyCastlePQCProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.ozgard.cryptomall.model.CrystalsDilithiumSignature;
import io.ozgard.cryptomall.params.PostQuantumCryptoParams;


//Selected Algorithms: Public-key Encryption and Key-establishment Algorithms:
//CRYSTALS-KYBER (3 parameter sets)
//HQC (3 parameter sets)
//
//Selected Algorithms: Digital Signature Algorithms:
//CRYSTALS-DILITHIUM (3 parameter sets)
//FALCON (2 parameter sets)
//SPHINCS+ (24 parameter sets)
//
//Round 4 Submissions: Public-key Encryption and Key-establishment Algorithms
//BIKE (3 parameter sets)
//Classic McEliece (6 parameter sets)

@Service
public class PostQuantumCryptoService 
{
	@Autowired
	CrystalsDilithiumSignature crystalsDilithiumSignature;
	
	public PostQuantumCryptoService() 
	{
		if (Security.getProvider("BCPQC") == null) 
		{
	        Security.addProvider(new BouncyCastlePQCProvider());
	    }
	}

	public String signatureGenerateDilithium(PostQuantumCryptoParams postQuantumCryptoParams) 
	{
		crystalsDilithiumSignature.generatePublicPrivateKeys(postQuantumCryptoParams.getDilithiumStrToParams().get(postQuantumCryptoParams.getParameterSet()));
		return "signatureGenerateDilithium";
	}

	public String signatureGenerateFalcon(PostQuantumCryptoParams postQuantumCryptoParams) 
	{
		// TODO Auto-generated method stub
		return "signatureGenerateFalcon";
	}

	public String signatureGenerateSphincs(PostQuantumCryptoParams postQuantumCryptoParams) 
	{
		// TODO Auto-generated method stub
		return "signatureGenerateSphincs";
	}

	public String signatureVerifyDilithium(PostQuantumCryptoParams postQuantumCryptoParams) 
	{
		// TODO Auto-generated method stub
		return "signatureVerifyDilithium";
	}

	public String signatureVerifyFalcon(PostQuantumCryptoParams postQuantumCryptoParams) 
	{
		// TODO Auto-generated method stub
		return "signatureVerifyFalcon";
	}

	public String signatureVerifySphincs(PostQuantumCryptoParams postQuantumCryptoParams) 
	{
		// TODO Auto-generated method stub
		return "signatureVerifySphincs";
	}

	public String keyEncapsulateKyber(PostQuantumCryptoParams postQuantumCryptoParams) 
	{
		// TODO Auto-generated method stub
		return "keyEncapsulateKyber";
	}

	public String keyEncapsulateHQC(PostQuantumCryptoParams postQuantumCryptoParams) 
	{
		// TODO Auto-generated method stub
		return "keyEncapsulateHQC";
	}

	public String keyEncapsulateBike(PostQuantumCryptoParams postQuantumCryptoParams) 
	{
		// TODO Auto-generated method stub
		return "keyEncapsulateBike";
	}

	public String keyEncapsulateClassicMcEliece(PostQuantumCryptoParams postQuantumCryptoParams) 
	{
		// TODO Auto-generated method stub
		return "keyEncapsulateClassicMcEliece";
	}
}
