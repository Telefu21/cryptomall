package io.ozgard.cryptomall.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Security;
import java.util.regex.Matcher;

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
		String retStr = "";
		String signatureBytesTextAreafileName = "\"" + postQuantumCryptoParams.getWorkingDirectoryPath() + "\\" + postQuantumCryptoParams.getParameterSet() + "_text_area_dilithium_sign.bin\"";
		String privKeyBytesfileName = "\"" + postQuantumCryptoParams.getWorkingDirectoryPath() + "\\" + postQuantumCryptoParams.getParameterSet() + "_dilithium_private_key.bin\"";
		String pubKeyBytesfileName = "\"" + postQuantumCryptoParams.getWorkingDirectoryPath() + "\\" + postQuantumCryptoParams.getParameterSet() + "_dilithium_public_key.bin\"";
		String signatureBytesFilefileName = "\"" + postQuantumCryptoParams.getWorkingDirectoryPath() + "\\" + postQuantumCryptoParams.getParameterSet() + "_file_dilithium_sign.bin\"";
		
		crystalsDilithiumSignature.generatePublicPrivateKeys(postQuantumCryptoParams.getDilithiumStrToParams().get(postQuantumCryptoParams.getParameterSet()));
		
		byte [] privKeyBytes = crystalsDilithiumSignature.getPrivateKeyBytes();
		writeBytesToFile(privKeyBytes, privKeyBytesfileName);
		 
		retStr += privKeyBytes.length + " bytes of Private Key generated and written to " + privKeyBytesfileName + "\n";
		
		byte [] pubKeyBytes = crystalsDilithiumSignature.getPublicKeyBytes();
		writeBytesToFile(pubKeyBytes, pubKeyBytesfileName);
		
		retStr += pubKeyBytes.length + " bytes of Public Key generated and written to " + pubKeyBytesfileName + "\n";
		
		byte [] signatureBytesFile = crystalsDilithiumSignature.generateSignature(postQuantumCryptoParams.getInputFileBytes(), privKeyBytes);
		writeBytesToFile(signatureBytesFile, signatureBytesFilefileName);
		
		retStr += signatureBytesFile.length + " bytes of Signature for Selected File generated and written to " + signatureBytesFilefileName + "\n";
		
		if(postQuantumCryptoParams.getTextAreaBytes() !=  null)
		{
			byte [] signatureBytesTextArea = crystalsDilithiumSignature.generateSignature(postQuantumCryptoParams.getTextAreaBytes(), privKeyBytes);
			writeBytesToFile(signatureBytesTextArea, signatureBytesTextAreafileName);
			
			retStr += signatureBytesTextArea.length + " bytes of Signature for Text Area generated and written to " + signatureBytesTextAreafileName + "\n";
		}
		
		return retStr;
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
	
	private void writeBytesToFile(byte [] data, String fileName)
	{
		try (FileOutputStream fos = new FileOutputStream(fileName)) 
		{
            fos.write(data);
            fos.close();
        } 
		catch (IOException e) 
		{
            System.err.println("Error writing to file: " + e.getMessage());
        }
	}
}
