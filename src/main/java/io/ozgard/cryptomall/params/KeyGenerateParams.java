package io.ozgard.cryptomall.params;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class KeyGenerateParams 
{
	public static final String KEYGEN_FILE_FORMAT_SELECT_PEM = "PEM";
	public static final String KEYGEN_FILE_FORMAT_SELECT_DER = "DER";
	public static final String KEYGEN_ALGO_SELECT_RSA = "RSA";
	public static final String KEYGEN_ALGO_SELECT_ECC = "EC";
	public static final String KEYGEN_ALGO_SELECT_DSA = "DSA";
	public static final String KEYGEN_ALGO_SELECT_DH = "DH";
	public static final String KEYGEN_ALGO_SELECT_ED25519 = "ED25519";
	public static final String KEYGEN_ALGO_SELECT_X448 = "X448";
	public static final String KEYGEN_ALGO_SELECT_X25519 = "X25519";
	public static final String KEYGEN_ALGO_SELECT_ED448 = "ED448";
	public static final String KEYGEN_KEY_LENGHT_512 = "512";
	public static final String KEYGEN_KEY_LENGHT_1024 = "1024";
	public static final String KEYGEN_KEY_LENGHT_2048 = "2048";
	public static final String KEYGEN_KEY_LENGHT_4096 = "4096";
	
	String	outputFilePath;
	String	inputFilePath;
	String	keyGenAlgo;
	String	outKeyFileFormat;
	String	keyLength;
	String	elepticCurveName;
	String	fileEncryptionCipher;
	String	fileEncryptionPassword;
	Boolean encryptKeyFile;
	
	Set<String> algorithmNames;
	
	public KeyGenerateParams()
	{
		algorithmNames = new HashSet<String>();
		
		algorithmNames.add(KEYGEN_ALGO_SELECT_RSA);
		algorithmNames.add(KEYGEN_ALGO_SELECT_ECC);
		algorithmNames.add(KEYGEN_ALGO_SELECT_DSA);
		algorithmNames.add(KEYGEN_ALGO_SELECT_DH);
		algorithmNames.add(KEYGEN_ALGO_SELECT_ED25519);
		algorithmNames.add(KEYGEN_ALGO_SELECT_X448);
		algorithmNames.add(KEYGEN_ALGO_SELECT_X25519);
		algorithmNames.add(KEYGEN_ALGO_SELECT_ED448);
	}
	
	public Set<String> getAlgorithmNames() 
	{
		return algorithmNames;
	}

	public void setAlgorithmNames(Set<String> algorithmNames) 
	{
		this.algorithmNames = algorithmNames;
	}

	public String getOutputFilePath() 
	{
		return outputFilePath;
	}
	
	public void setOutputFilePath(String outputFilePath) 
	{
		this.outputFilePath = outputFilePath;
	}
	
	public String getInputFilePath() 
	{
		return inputFilePath;
	}
	
	public void setInputFilePath(String inputFilePath) 
	{
		this.inputFilePath = inputFilePath;
	}
	
	public String getKeyGenAlgo() 
	{
		return keyGenAlgo;
	}
	
	public void setKeyGenAlgo(String keyGenAlgo) 
	{
		this.keyGenAlgo = keyGenAlgo;
	}
	
	public String getKeyLength() 
	{
		return keyLength;
	}
	
	public void setKeyLength(String keyLength) 
	{
		this.keyLength = keyLength;
	}
	
	public String getElepticCurveName() 
	{
		return elepticCurveName;
	}
	
	public void setElepticCurveName(String elepticCurveName) 
	{
		this.elepticCurveName = elepticCurveName;
	}
	
	public String getFileEncryptionCipher() 
	{
		return fileEncryptionCipher;
	}
	
	public void setFileEncryptionCipher(String fileEncryptionCipher) 
	{
		this.fileEncryptionCipher = fileEncryptionCipher;
	}
	
	public String getFileEncryptionPassword() 
	{
		return fileEncryptionPassword;
	}
	
	public void setFileEncryptionPassword(String fileEncryptionPassword) 
	{
		this.fileEncryptionPassword = fileEncryptionPassword;
	}
	
	public Boolean getEncryptKeyFile() 
	{
		return encryptKeyFile;
	}
	
	public void setEncryptKeyFile(Boolean encryptKeyFile) 
	{
		this.encryptKeyFile = encryptKeyFile;
	}

	public String getOutKeyFileFormat() 
	{
		return outKeyFileFormat;
	}

	public void setOutKeyFileFormat(String outKeyFileFormat) 
	{
		this.outKeyFileFormat = outKeyFileFormat;
	}
}
