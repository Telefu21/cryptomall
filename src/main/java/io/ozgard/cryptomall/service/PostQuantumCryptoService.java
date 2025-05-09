package io.ozgard.cryptomall.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Security;

import org.bouncycastle.pqc.jcajce.provider.BouncyCastlePQCProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.ozgard.cryptomall.model.CrystalsDilithiumSignature;
import io.ozgard.cryptomall.model.FalconSignature;
import io.ozgard.cryptomall.model.SphincsSignature;
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
	@Autowired
	FalconSignature falconSignature;
	@Autowired
	SphincsSignature sphincsSignature;
	
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
		
		byte [] privKeyBytes = crystalsDilithiumSignature.getPrivateKeyBytes();
		
		byte [] pubKeyBytes = crystalsDilithiumSignature.getPublicKeyBytes();
		
		byte [] signatureBytesFile = crystalsDilithiumSignature.generateSignature(postQuantumCryptoParams.getInputFileBytes(), privKeyBytes);

		byte [] signatureBytesTextArea = null;
		
		if(postQuantumCryptoParams.getTextAreaBytes() !=  null)
		{
			signatureBytesTextArea = crystalsDilithiumSignature.generateSignature(postQuantumCryptoParams.getTextAreaBytes(), privKeyBytes);
		}

		return processFileOperations(postQuantumCryptoParams, privKeyBytes, pubKeyBytes, signatureBytesFile, signatureBytesTextArea, "Dilithium");
	}

	public String signatureGenerateFalcon(PostQuantumCryptoParams postQuantumCryptoParams) 
	{
		falconSignature.generatePublicPrivateKeys(postQuantumCryptoParams.getFalconStrToParams().get(postQuantumCryptoParams.getParameterSet()));
		
		byte [] privKeyBytes = falconSignature.getPrivateKeyBytes();
		
		byte [] pubKeyBytes = falconSignature.getPublicKeyBytes();
		
		byte [] signatureBytesFile = falconSignature.generateSignature(postQuantumCryptoParams.getInputFileBytes(), privKeyBytes);

		byte [] signatureBytesTextArea = null;
		
		if(postQuantumCryptoParams.getTextAreaBytes() !=  null)
		{
			signatureBytesTextArea = falconSignature.generateSignature(postQuantumCryptoParams.getTextAreaBytes(), privKeyBytes);
		}

		return processFileOperations(postQuantumCryptoParams, privKeyBytes, pubKeyBytes, signatureBytesFile, signatureBytesTextArea, "Falcon");
	}

	public String signatureGenerateSphincs(PostQuantumCryptoParams postQuantumCryptoParams) 
	{
		sphincsSignature.generatePublicPrivateKeys(postQuantumCryptoParams.getSphincsStrToParams().get(postQuantumCryptoParams.getParameterSet()));
		
		byte [] privKeyBytes = sphincsSignature.getPrivateKeyBytes();
		
		byte [] pubKeyBytes = sphincsSignature.getPublicKeyBytes();
		
		byte [] signatureBytesFile = sphincsSignature.generateSignature(postQuantumCryptoParams.getInputFileBytes(), privKeyBytes);

		byte [] signatureBytesTextArea = null;
		
		if(postQuantumCryptoParams.getTextAreaBytes() !=  null)
		{
			signatureBytesTextArea = sphincsSignature.generateSignature(postQuantumCryptoParams.getTextAreaBytes(), privKeyBytes);
		}

		return processFileOperations(postQuantumCryptoParams, privKeyBytes, pubKeyBytes, signatureBytesFile, signatureBytesTextArea, "Sphincs");
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
	
	private String processFileOperations(PostQuantumCryptoParams postQuantumCryptoParams, byte [] privKeyBytes, byte [] pubKeyBytes, byte [] signatureBytesFile, byte [] signatureBytesTextArea, String fileSpecificName)
	{
		String retStr = "";
		String signatureBytesTextAreafileName = postQuantumCryptoParams.getWorkingDirectoryPath() + "\\" + postQuantumCryptoParams.getParameterSet() + "_" + fileSpecificName + "_text_area_signature.bin";
		String privKeyBytesfileName = postQuantumCryptoParams.getWorkingDirectoryPath() + "\\" + postQuantumCryptoParams.getParameterSet() + "_" + fileSpecificName + "_private_key.bin";
		String pubKeyBytesfileName = postQuantumCryptoParams.getWorkingDirectoryPath() + "\\" + postQuantumCryptoParams.getParameterSet() + "_" + fileSpecificName + "_public_key.bin";
		String signatureBytesFilefileName = postQuantumCryptoParams.getWorkingDirectoryPath() + "\\" + postQuantumCryptoParams.getParameterSet() + "_" + fileSpecificName + "_file_signature.bin";
		
		writeBytesToFile(privKeyBytes, privKeyBytesfileName);
		retStr += privKeyBytes.length + " bytes of Private Key generated and written to --> " + privKeyBytesfileName + "\n";

		writeBytesToFile(pubKeyBytes, pubKeyBytesfileName);
		retStr += pubKeyBytes.length + " bytes of Public Key generated and written to --> " + pubKeyBytesfileName + "\n";
		
		writeBytesToFile(signatureBytesFile, signatureBytesFilefileName);
		retStr += signatureBytesFile.length + " bytes of Signature for Selected File generated and written to --> " + signatureBytesFilefileName + "\n";

		if(postQuantumCryptoParams.getTextAreaBytes() !=  null)
		{
			writeBytesToFile(signatureBytesTextArea, signatureBytesTextAreafileName);
			retStr += signatureBytesTextArea.length + " bytes of Signature for Text Area generated and written to --> " + signatureBytesTextAreafileName + "\n";
		}
		
		return retStr;
	}

	private void writeBytesToFile(byte [] data, String fileName)
	{
		try 
		{
			Files.write(Paths.get(fileName), data);
		} 
		catch (IOException e) 
		{
			System.err.println("Error writing to file: " + e.getMessage());
		}
	}	
}
