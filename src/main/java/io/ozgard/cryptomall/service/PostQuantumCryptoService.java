package io.ozgard.cryptomall.service;

import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;

import org.bouncycastle.pqc.jcajce.provider.BouncyCastlePQCProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.ozgard.cryptomall.model.CrystalsDilithiumSignature;
import io.ozgard.cryptomall.model.FalconSignature;
import io.ozgard.cryptomall.model.ISignature;
import io.ozgard.cryptomall.model.PQCKeyEncalpsulation;
import io.ozgard.cryptomall.model.SphincsSignature;
import io.ozgard.cryptomall.params.PostQuantumCryptoParams;
import io.ozgard.cryptomall.utility.Utility;


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
	@Autowired
	FalconSignature falconSignature;
	@Autowired
	SphincsSignature sphincsSignature;
	@Autowired
	PQCKeyEncalpsulation keyEncalpsulation;
	
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
		
		return generateSignature(crystalsDilithiumSignature, postQuantumCryptoParams, "Dilithium");
	}

	public String signatureGenerateFalcon(PostQuantumCryptoParams postQuantumCryptoParams) 
	{
		falconSignature.generatePublicPrivateKeys(postQuantumCryptoParams.getFalconStrToParams().get(postQuantumCryptoParams.getParameterSet()));
		
		return generateSignature(falconSignature, postQuantumCryptoParams, "Falcon");
	}

	public String signatureGenerateSphincs(PostQuantumCryptoParams postQuantumCryptoParams) 
	{
		sphincsSignature.generatePublicPrivateKeys(postQuantumCryptoParams.getSphincsStrToParams().get(postQuantumCryptoParams.getParameterSet()));
		
		return generateSignature(sphincsSignature, postQuantumCryptoParams, "Sphincs");
	}
	
	private String generateSignature(ISignature sigAlgoRef, PostQuantumCryptoParams postQuantumCryptoParams, String algoName)
	{
		byte [] privKeyBytes = sigAlgoRef.getPrivateKeyBytes();
		
		byte [] pubKeyBytes = sigAlgoRef.getPublicKeyBytes();
		
		byte [] signatureBytesFile = null;
		
		if(postQuantumCryptoParams.getInputFileBytes() !=  null)
		{
			signatureBytesFile = sigAlgoRef.generateSignature(postQuantumCryptoParams.getInputFileBytes(), privKeyBytes);
		}
		
		byte [] signatureBytesTextArea = null;
		
		if(postQuantumCryptoParams.getTextAreaBytes() !=  null)
		{
			signatureBytesTextArea = sigAlgoRef.generateSignature(postQuantumCryptoParams.getTextAreaBytes(), privKeyBytes);
		}

		return processFileOperationsSignature(postQuantumCryptoParams, privKeyBytes, pubKeyBytes, signatureBytesFile, signatureBytesTextArea, algoName);
	}

	public String signatureVerifyDilithium(PostQuantumCryptoParams postQuantumCryptoParams) 
	{
		return verifySignature(crystalsDilithiumSignature, postQuantumCryptoParams, "Dilithium");
	}

	public String signatureVerifyFalcon(PostQuantumCryptoParams postQuantumCryptoParams) 
	{
		return verifySignature(falconSignature, postQuantumCryptoParams, "Falcon");
	}

	public String signatureVerifySphincs(PostQuantumCryptoParams postQuantumCryptoParams) 
	{
		return verifySignature(sphincsSignature, postQuantumCryptoParams, "Sphincs");
	}
	
	private String verifySignature(ISignature sigAlgoRef, PostQuantumCryptoParams postQuantumCryptoParams, String algoName)
	{
		String retStr = null;
		
		if((postQuantumCryptoParams.getInputFileBytes() == null) || (postQuantumCryptoParams.getSignatureFileBytes() == null) || (postQuantumCryptoParams.getPublicKeyFileBytes() == null))
		{
			return "!!! Error: Select all the files needed for verification !!!";
		}
		
		boolean isVerified = sigAlgoRef.verifySignature(postQuantumCryptoParams.getInputFileBytes(), postQuantumCryptoParams.getSignatureFileBytes(), postQuantumCryptoParams.getPublicKeyFileBytes());
		
		if(isVerified == true)
		{
			retStr = algoName + " Signature Verification: Success:)";
		}
		
		if(isVerified == false)
		{
			retStr = algoName + " Signature Verification: Failed !!!";
		}
		
		return retStr;
	}

	public String keyEncapsulate(PostQuantumCryptoParams postQuantumCryptoParams, AlgorithmParameterSpec algoSpec, String algorithm) 
	{
		keyEncalpsulation.setAlgorithm(algorithm);
		
		keyEncalpsulation.generatePublicPrivateKeys(algoSpec);
		
		byte[] privateKey = keyEncalpsulation.getPrivateKeyBytes();
		byte[] publicKey = keyEncalpsulation.getPublicKeyBytes();
        byte[] secretKey = keyEncalpsulation.pqcGenerateKEMEncryptionKey(keyEncalpsulation.getPublicKeyFromEncoded(publicKey));
        byte[] encapsulatedKey = keyEncalpsulation.getEncapsulation();
       
		return processFileOperationsKEM(postQuantumCryptoParams, privateKey, publicKey, encapsulatedKey, secretKey, algorithm);	
	}
	
	public String keyEncapsulateKyber(PostQuantumCryptoParams postQuantumCryptoParams) 
	{
		return keyEncapsulate(postQuantumCryptoParams, postQuantumCryptoParams.getKyberStrToParams().get(postQuantumCryptoParams.getParameterSet()), "Kyber");
	}

	public String keyEncapsulateHQC(PostQuantumCryptoParams postQuantumCryptoParams) 
	{
		// TODO Auto-generated method stub
		return "keyEncapsulateHQC";
	}

	public String keyEncapsulateBike(PostQuantumCryptoParams postQuantumCryptoParams) 
	{
		return keyEncapsulate(postQuantumCryptoParams, postQuantumCryptoParams.getBikeStrToParams().get(postQuantumCryptoParams.getParameterSet()), "Bike");
	}

	public String keyEncapsulateClassicMcEliece(PostQuantumCryptoParams postQuantumCryptoParams) 
	{
		return keyEncapsulate(postQuantumCryptoParams, postQuantumCryptoParams.getMecelieceStrToParams().get(postQuantumCryptoParams.getParameterSet()), "CMCE");
	}
	
	private String processFileOperationsSignature(PostQuantumCryptoParams postQuantumCryptoParams, byte [] privKeyBytes, byte [] pubKeyBytes, byte [] signatureBytesFile, byte [] signatureBytesTextArea, String fileSpecificName)
	{
		String retStr = "";
		String signatureBytesTextAreafileName = postQuantumCryptoParams.getWorkingDirectoryPath() + "\\" + postQuantumCryptoParams.getParameterSet() + "_" + fileSpecificName + "_text_area_signature.bin";
		String privKeyBytesfileName = postQuantumCryptoParams.getWorkingDirectoryPath() + "\\" + postQuantumCryptoParams.getParameterSet() + "_" + fileSpecificName + "_private_key.bin";
		String pubKeyBytesfileName = postQuantumCryptoParams.getWorkingDirectoryPath() + "\\" + postQuantumCryptoParams.getParameterSet() + "_" + fileSpecificName + "_public_key.bin";
		String signatureBytesFilefileName = postQuantumCryptoParams.getWorkingDirectoryPath() + "\\" + postQuantumCryptoParams.getParameterSet() + "_" + fileSpecificName + "_file_signature.bin";
		
		Utility.writeBytesToFile(privKeyBytes, privKeyBytesfileName);
		retStr += privKeyBytes.length + " bytes of Private Key generated and written to --> " + privKeyBytesfileName + "\n";

		Utility.writeBytesToFile(pubKeyBytes, pubKeyBytesfileName);
		retStr += pubKeyBytes.length + " bytes of Public Key generated and written to --> " + pubKeyBytesfileName + "\n";
		
		if(postQuantumCryptoParams.getInputFileBytes() !=  null)
		{
			Utility.writeBytesToFile(signatureBytesFile, signatureBytesFilefileName);
			retStr += signatureBytesFile.length + " bytes of Signature for Selected File generated and written to --> " + signatureBytesFilefileName + "\n";
		}
		
		if(postQuantumCryptoParams.getTextAreaBytes() !=  null)
		{
			Utility.writeBytesToFile(signatureBytesTextArea, signatureBytesTextAreafileName);
			retStr += signatureBytesTextArea.length + " bytes of Signature for Text Area generated and written to --> " + signatureBytesTextAreafileName + "\n";
		}
		
		return retStr;
	}	
	
	private String processFileOperationsKEM(PostQuantumCryptoParams postQuantumCryptoParams, byte [] privKeyBytes, byte [] pubKeyBytes, byte [] encapsulatedKeyBytes, byte [] secretKeyBytes, String fileSpecificName)
	{
		String retStr = "";
		String encapKeyBytesfileName = postQuantumCryptoParams.getWorkingDirectoryPath() + "\\" + postQuantumCryptoParams.getParameterSet() + "_" + fileSpecificName + "_encapsulated_key.bin";
		String privKeyBytesfileName = postQuantumCryptoParams.getWorkingDirectoryPath() + "\\" + postQuantumCryptoParams.getParameterSet() + "_" + fileSpecificName + "_private_key.bin";
		String pubKeyBytesfileName = postQuantumCryptoParams.getWorkingDirectoryPath() + "\\" + postQuantumCryptoParams.getParameterSet() + "_" + fileSpecificName + "_public_key.bin";
		String secretKeyBytesfileName = postQuantumCryptoParams.getWorkingDirectoryPath() + "\\" + postQuantumCryptoParams.getParameterSet() + "_" + fileSpecificName + "_secret_key.bin";
		
		Utility.writeBytesToFile(privKeyBytes, privKeyBytesfileName);
		retStr += privKeyBytes.length + " bytes of Private Key generated and written to --> " + privKeyBytesfileName + "\n";

		Utility.writeBytesToFile(pubKeyBytes, pubKeyBytesfileName);
		retStr += pubKeyBytes.length + " bytes of Public Key generated and written to --> " + pubKeyBytesfileName + "\n";
		
		Utility.writeBytesToFile(encapsulatedKeyBytes, encapKeyBytesfileName);
		retStr += encapsulatedKeyBytes.length + " bytes of encapsulated Secret Key (Ciphertext) written to --> " + encapKeyBytesfileName + "\n";
		
		Utility.writeBytesToFile(secretKeyBytes, secretKeyBytesfileName);
		retStr += secretKeyBytes.length + " bytes of Secret Key generated and written to --> " + secretKeyBytesfileName + "\n";
		retStr +="Secret Key (Hex): " + Utility.bytesToHex(secretKeyBytes) + "\n";

		return retStr;
	}
}
