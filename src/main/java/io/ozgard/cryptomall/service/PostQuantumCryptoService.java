package io.ozgard.cryptomall.service;

import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;
import java.time.LocalTime;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.jcajce.provider.BouncyCastlePQCProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.ozgard.cryptomall.model.PQCKeyEncalpsulation;
import io.ozgard.cryptomall.model.PQCKeyEncalpsulationHQC;
import io.ozgard.cryptomall.model.PQCSignature;
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
	PQCKeyEncalpsulation keyEncalpsulation;
	@Autowired
	PQCKeyEncalpsulationHQC keyEncalpsulationHqc;
	@Autowired
	PQCSignature pqcSignature;
	
	public PostQuantumCryptoService() 
	{
		if (Security.getProvider("BCPQC") == null) 
		{
	        Security.addProvider(new BouncyCastlePQCProvider());
	        Security.addProvider(new BouncyCastleProvider());
	    }
	}

	public String signatureGenerateDilithium(PostQuantumCryptoParams postQuantumCryptoParams) 
	{
		pqcSignature.setAlgorithm("Dilithium");
		pqcSignature.generatePublicPrivateKeys(postQuantumCryptoParams.getDilithiumStrToParams().get(postQuantumCryptoParams.getParameterSet()));
		
		return generateSignature(postQuantumCryptoParams, "Dilithium");
	}

	public String signatureGenerateFalcon(PostQuantumCryptoParams postQuantumCryptoParams) 
	{
		pqcSignature.setAlgorithm("Falcon");
		pqcSignature.generatePublicPrivateKeys(postQuantumCryptoParams.getFalconStrToParams().get(postQuantumCryptoParams.getParameterSet()));
		
		return generateSignature(postQuantumCryptoParams, "Falcon");
	}

	public String signatureGenerateSphincs(PostQuantumCryptoParams postQuantumCryptoParams) 
	{
		pqcSignature.setAlgorithm("SPHINCSPlus");
		pqcSignature.generatePublicPrivateKeys(postQuantumCryptoParams.getSphincsStrToParams().get(postQuantumCryptoParams.getParameterSet()));
		
		return generateSignature(postQuantumCryptoParams, "SPHINCSPlus");
	}
	
	private String generateSignature(PostQuantumCryptoParams postQuantumCryptoParams, String algoName)
	{
		byte [] privKeyBytes = pqcSignature.getPrivateKeyBytes();
		
		byte [] pubKeyBytes = pqcSignature.getPublicKeyBytes();
		
		byte [] signatureBytesFile = null;
		
		if(postQuantumCryptoParams.getInputFileBytes() !=  null)
		{
			signatureBytesFile = pqcSignature.generateSignature(postQuantumCryptoParams.getInputFileBytes(), privKeyBytes);
		}
		
		byte [] signatureBytesTextArea = null;
		
		if(postQuantumCryptoParams.getTextAreaBytes() !=  null)
		{
			signatureBytesTextArea = pqcSignature.generateSignature(postQuantumCryptoParams.getTextAreaBytes(), privKeyBytes);
		}
		
		postQuantumCryptoParams.setPrivateKey(privKeyBytes);
		postQuantumCryptoParams.setPublicKey(pubKeyBytes);
		postQuantumCryptoParams.setSignature(signatureBytesFile);

		return processFileOperationsSignature(postQuantumCryptoParams, privKeyBytes, pubKeyBytes, signatureBytesFile, signatureBytesTextArea, algoName);
	}

	public String signatureVerifyDilithium(PostQuantumCryptoParams postQuantumCryptoParams) 
	{
		pqcSignature.setAlgorithm("Dilithium");
		return verifySignature(postQuantumCryptoParams, "Dilithium");
	}

	public String signatureVerifyFalcon(PostQuantumCryptoParams postQuantumCryptoParams) 
	{
		pqcSignature.setAlgorithm("Falcon");
		return verifySignature(postQuantumCryptoParams, "Falcon");
	}

	public String signatureVerifySphincs(PostQuantumCryptoParams postQuantumCryptoParams) 
	{
		pqcSignature.setAlgorithm("SPHINCSPlus");
		return verifySignature(postQuantumCryptoParams, "SPHINCSPlus");
	}
	
	private String verifySignature(PostQuantumCryptoParams postQuantumCryptoParams, String algoName)
	{
		String retStr = null;
		
		if((postQuantumCryptoParams.getInputFileBytes() == null) || (postQuantumCryptoParams.getSignatureFileBytes() == null) || (postQuantumCryptoParams.getPublicKeyFileBytes() == null))
		{
			return "!!! Error: Select all the files needed for verification !!!";
		}
		
		boolean isVerified = pqcSignature.verifySignature(postQuantumCryptoParams.getInputFileBytes(), postQuantumCryptoParams.getSignatureFileBytes(), postQuantumCryptoParams.getPublicKeyFileBytes());
		
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

	private String keyEncapsulate(PostQuantumCryptoParams postQuantumCryptoParams, String algorithm) 
	{
		String retStr = "!!! Please Select the Public Key File !!!!";
		
		if(postQuantumCryptoParams.getInputFileBytes() !=  null)
		{
			byte[] secretKey = null;
			byte[] encapsulatedKey =null;
			
			String encapKeyBytesfileName = postQuantumCryptoParams.getWorkingDirectoryPath() + Utility.getPathSeperator() + postQuantumCryptoParams.getParameterSet() + "_" + algorithm + "_encapsulated_key_"  + LocalTime.now().getHour() + "-" + LocalTime.now().getMinute()+ "-" + LocalTime.now().getSecond() + ".bin";
			String secretKeyBytesfileName = postQuantumCryptoParams.getWorkingDirectoryPath() + Utility.getPathSeperator() + postQuantumCryptoParams.getParameterSet() + "_" + algorithm + "_secret_key_"  + LocalTime.now().getHour() + "-" + LocalTime.now().getMinute()+ "-" + LocalTime.now().getSecond() + ".bin";
			
			if(algorithm != "HQC")
			{
				keyEncalpsulation.setAlgorithm(algorithm);
				
				secretKey = keyEncalpsulation.pqcGenerateKEMEncryptionKey(keyEncalpsulation.getPublicKeyFromEncoded(postQuantumCryptoParams.getInputFileBytes()));
		        encapsulatedKey = keyEncalpsulation.getEncapsulation();
			}
			
			if(algorithm == "HQC")
			{			
				secretKey = keyEncalpsulationHqc.pqcGenerateKEMEncryptionKey(keyEncalpsulationHqc.getPublicKeyFromEncoded(postQuantumCryptoParams.getInputFileBytes(), postQuantumCryptoParams.getHqcStrToParams().get(postQuantumCryptoParams.getParameterSet())));
		        encapsulatedKey = keyEncalpsulationHqc.getEncapsulation();
			}
			
			postQuantumCryptoParams.setEncapsulatedSecretKey(encapsulatedKey);
			postQuantumCryptoParams.setSecretKey(secretKey);
			
			Utility.writeBytesToFile(encapsulatedKey, encapKeyBytesfileName);
			retStr = encapsulatedKey.length + " bytes of encapsulated Secret Key (Ciphertext) written to --> " + encapKeyBytesfileName + "\n";
			
			Utility.writeBytesToFile(secretKey, secretKeyBytesfileName);
			retStr += secretKey.length + " bytes of Secret Key generated and written to --> " + secretKeyBytesfileName + "\n";
			retStr +="Secret Key (Hex): " + Utility.bytesToHex(secretKey) + "\n";
		}
		
		return retStr;
	}
	
	public String keyEncapsulateKyber(PostQuantumCryptoParams postQuantumCryptoParams) 
	{
		return keyEncapsulate(postQuantumCryptoParams, "Kyber");
	}

	public String keyEncapsulateHQC(PostQuantumCryptoParams postQuantumCryptoParams) 
	{
		return keyEncapsulate(postQuantumCryptoParams, "HQC");
	}

	public String keyEncapsulateBike(PostQuantumCryptoParams postQuantumCryptoParams) 
	{
		return keyEncapsulate(postQuantumCryptoParams, "Bike");
	}

	public String keyEncapsulateClassicMcEliece(PostQuantumCryptoParams postQuantumCryptoParams) 
	{
		return keyEncapsulate(postQuantumCryptoParams, "CMCE");
	}
	
	private String processFileOperationsSignature(PostQuantumCryptoParams postQuantumCryptoParams, byte [] privKeyBytes, byte [] pubKeyBytes, byte [] signatureBytesFile, byte [] signatureBytesTextArea, String fileSpecificName)
	{
		String retStr = "";
		String signatureBytesTextAreafileName = postQuantumCryptoParams.getWorkingDirectoryPath() + Utility.getPathSeperator() + postQuantumCryptoParams.getParameterSet() + "_" + fileSpecificName + "_text_area_signature_"  + LocalTime.now().getHour() + "-" + LocalTime.now().getMinute()+ "-" + LocalTime.now().getSecond() + ".bin";
		String privKeyBytesfileName = postQuantumCryptoParams.getWorkingDirectoryPath() + Utility.getPathSeperator() + postQuantumCryptoParams.getParameterSet() + "_" + fileSpecificName + "_private_key_"  + LocalTime.now().getHour() + "-" + LocalTime.now().getMinute()+ "-" + LocalTime.now().getSecond() + ".bin";
		String pubKeyBytesfileName = postQuantumCryptoParams.getWorkingDirectoryPath() + Utility.getPathSeperator() + postQuantumCryptoParams.getParameterSet() + "_" + fileSpecificName + "_public_key_"  + LocalTime.now().getHour() + "-" + LocalTime.now().getMinute()+ "-" + LocalTime.now().getSecond() + ".bin";
		String signatureBytesFilefileName = postQuantumCryptoParams.getWorkingDirectoryPath() + Utility.getPathSeperator() + postQuantumCryptoParams.getParameterSet() + "_" + fileSpecificName + "_file_signature_"  + LocalTime.now().getHour() + "-" + LocalTime.now().getMinute()+ "-" + LocalTime.now().getSecond() + ".bin";
		
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

	private String keyDecapsulate(PostQuantumCryptoParams postQuantumCryptoParams, String algorithm) 
	{
		String retStr = "!!! Please Select the Private Key and Encapsulated Key Files !!!!";
		
		keyEncalpsulation.setAlgorithm(algorithm);
		
		if(postQuantumCryptoParams.getInputFileBytes() !=  null && postQuantumCryptoParams.getPublicKeyFileBytes() !=  null)
		{
			if(algorithm == "HQC")
			{
				retStr = "Decapsulated Secret Key (Hex): " + Utility.bytesToHex(keyEncalpsulationHqc.pqcGenerateKEMDecryptionKey(keyEncalpsulationHqc.getPrivateKeyFromEncoded(postQuantumCryptoParams.getInputFileBytes(), postQuantumCryptoParams.getHqcStrToParams().get(postQuantumCryptoParams.getParameterSet())), postQuantumCryptoParams.getPublicKeyFileBytes())) + "\n";
			}
			
			if(algorithm != "HQC")
			{
				retStr = "Decapsulated Secret Key (Hex): " + Utility.bytesToHex(keyEncalpsulation.pqcGenerateKEMDecryptionKey(keyEncalpsulation.getPrivateKeyFromEncoded(postQuantumCryptoParams.getInputFileBytes()), postQuantumCryptoParams.getPublicKeyFileBytes())) + "\n";
			}
		}
		
		return retStr;
	}

	public String keyDecapsulateHQC(PostQuantumCryptoParams postQuantumCryptoParams) 
	{
		return keyDecapsulate(postQuantumCryptoParams, "HQC");
	}
	
	public String keyDecapsulateBike(PostQuantumCryptoParams postQuantumCryptoParams) 
	{
		return keyDecapsulate(postQuantumCryptoParams, "Bike");
	}

	public String keyDecapsulateClassicMcEliece(PostQuantumCryptoParams postQuantumCryptoParams) 
	{
		return keyDecapsulate(postQuantumCryptoParams, "CMCE");
	}

	public String keyDecapsulateKyber(PostQuantumCryptoParams postQuantumCryptoParams) 
	{
		return keyDecapsulate(postQuantumCryptoParams, "Kyber");
	}

	private String keyPairGenerate(PostQuantumCryptoParams postQuantumCryptoParams, AlgorithmParameterSpec algoSpec, String algorithm) 
	{
		byte[] privateKey = null;
		byte[] publicKey = null;
		
		if(algorithm != "HQC")
		{
			keyEncalpsulation.setAlgorithm(algorithm);
			
			keyEncalpsulation.generatePublicPrivateKeys(algoSpec);
			
			privateKey = keyEncalpsulation.getPrivateKeyBytes();
			publicKey = keyEncalpsulation.getPublicKeyBytes();
		}
		
		if(algorithm == "HQC")
		{
			keyEncalpsulationHqc.generatePublicPrivateKeys(postQuantumCryptoParams.getHqcStrToParams().get(postQuantumCryptoParams.getParameterSet()));
			
			privateKey = keyEncalpsulationHqc.getPrivateKeyBytes();
			publicKey = keyEncalpsulationHqc.getPublicKeyBytes();
		}
		
		postQuantumCryptoParams.setPrivateKey(privateKey); 
		postQuantumCryptoParams.setPublicKey(publicKey); 
		
		String retStr = "";
		String privKeyBytesfileName = postQuantumCryptoParams.getWorkingDirectoryPath() + Utility.getPathSeperator() + postQuantumCryptoParams.getParameterSet() + "_" + algorithm + "_private_key_"  + LocalTime.now().getHour() + "-" + LocalTime.now().getMinute()+ "-" + LocalTime.now().getSecond() + ".bin";
		String pubKeyBytesfileName = postQuantumCryptoParams.getWorkingDirectoryPath() + Utility.getPathSeperator() + postQuantumCryptoParams.getParameterSet() + "_" + algorithm + "_public_key_"  + LocalTime.now().getHour() + "-" + LocalTime.now().getMinute()+ "-" + LocalTime.now().getSecond() + ".bin";
		
		Utility.writeBytesToFile(privateKey, privKeyBytesfileName);
		retStr += privateKey.length + " bytes of Private Key generated and written to --> " + privKeyBytesfileName + "\n";

		Utility.writeBytesToFile(publicKey, pubKeyBytesfileName);
		retStr += publicKey.length + " bytes of Public Key generated and written to --> " + pubKeyBytesfileName + "\n";
		
		return retStr;
	}
	
	public String keyPairGenerateKyber(PostQuantumCryptoParams postQuantumCryptoParams) 
	{
		return keyPairGenerate(postQuantumCryptoParams, postQuantumCryptoParams.getKyberStrToParams().get(postQuantumCryptoParams.getParameterSet()), "Kyber");
	}

	public String keyPairGenerateHQC(PostQuantumCryptoParams postQuantumCryptoParams) 
	{
		return keyPairGenerate(postQuantumCryptoParams, null, "HQC");
	}

	public String keyPairGenerateBike(PostQuantumCryptoParams postQuantumCryptoParams) 
	{
		return keyPairGenerate(postQuantumCryptoParams, postQuantumCryptoParams.getBikeStrToParams().get(postQuantumCryptoParams.getParameterSet()), "Bike");
	}

	public String keyPairGenerateClassicMceliece(PostQuantumCryptoParams postQuantumCryptoParams) 
	{
		return keyPairGenerate(postQuantumCryptoParams, postQuantumCryptoParams.getMecelieceStrToParams().get(postQuantumCryptoParams.getParameterSet()), "CMCE");
	}
}
