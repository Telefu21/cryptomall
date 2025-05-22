package io.ozgard.cryptomall.service;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;

import org.bouncycastle.jcajce.SecretKeyWithEncapsulation;
import org.bouncycastle.pqc.jcajce.provider.BouncyCastlePQCProvider;
import org.bouncycastle.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.ozgard.cryptomall.model.CrystalsDilithiumSignature;
import io.ozgard.cryptomall.model.CrystalsKyberKem;
import io.ozgard.cryptomall.model.FalconSignature;
import io.ozgard.cryptomall.model.ISignature;
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
	CrystalsKyberKem crystalsKyberKem;
	
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

		return processFileOperations(postQuantumCryptoParams, privKeyBytes, pubKeyBytes, signatureBytesFile, signatureBytesTextArea, algoName);
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

	public String keyEncapsulateKyber(PostQuantumCryptoParams postQuantumCryptoParams) 
	{
		crystalsKyberKem.generatePublicPrivateKeys(postQuantumCryptoParams.getKyberStrToParams().get(postQuantumCryptoParams.getParameterSet()));
		
		
		PrivateKey privateKeyLoad = crystalsKyberKem.getPrivateKeyFromEncoded(crystalsKyberKem.getPrivateKeyBytes());
        PublicKey publicKeyLoad = crystalsKyberKem.getPublicKeyFromEncoded(crystalsKyberKem.getPublicKeyBytes());
        
     // generate the encryption key and the encapsulated key
        String retStr = "the encryption key and the encapsulated key";
        SecretKeyWithEncapsulation secretKeyWithEncapsulationSender = crystalsKyberKem.pqcGenerateChrystalsKyberEncryptionKey(publicKeyLoad);
        byte[] encryptionKey = secretKeyWithEncapsulationSender.getEncoded();
        retStr += "\n" + "encryption key length: " + encryptionKey.length
                + " key: " + Utility.bytesToHex(secretKeyWithEncapsulationSender.getEncoded());
        byte[] encapsulatedKey = secretKeyWithEncapsulationSender.getEncapsulation();
        
        retStr += "\n" + "\nDecryption side: receive the encapsulated key and generate the decryption key";
        byte[] decryptionKey = crystalsKyberKem.pqcGenerateChrystalsKyberDecryptionKey(privateKeyLoad, encapsulatedKey);
        retStr += "\n" + "decryption key length: " + decryptionKey.length + " key: " + Utility.bytesToHex(decryptionKey);
        boolean keysAreEqual = Arrays.areEqual(encryptionKey, decryptionKey);
        retStr += "\n" + "decryption key is equal to encryption key: " + keysAreEqual;
        
		return retStr;
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
	
	private String processFileOperations(PostQuantumCryptoParams postQuantumCryptoParams, byte [] privKeyBytes, byte [] pubKeyBytes, byte [] signatureBytesFile, byte [] signatureBytesTextArea, String fileSpecificName)
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
}
