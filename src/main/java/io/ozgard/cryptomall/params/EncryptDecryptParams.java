package io.ozgard.cryptomall.params;

import org.springframework.stereotype.Component;

@Component
public class EncryptDecryptParams 
{
	public static final String ENCRYPT_DECRYPT_TYPE_ASYM_ENCRYPTION = "Asymmetric Encryption";
	public static final String ENCRYPT_DECRYPT_TYPE_ASYM_DECRYPTION = "Asymmetric Decryption";
	public static final String ENCRYPT_DECRYPT_TYPE_SYM_ENCRYPTION = "Symmetric Encryption";
	public static final String ENCRYPT_DECRYPT_TYPE_SYM_DECRYPTION = "Symmetric Decryption";
	public static final String ENCRYPT_DECRYPT_TYPE_GENERATE_HASH = "Generate Hash";
	public static final String ENCRYPT_DECRYPT_TYPE_GENERATE_HMAC = "Generate HMAC";
	public static final String ENCRYPT_DECRYPT_TYPE_GENERATE_CMAC = "Generate CMAC";
	
	String	cipher;
	String	hashFunction;
	String	passPhrase;
	String	keyFilePath;
	String	inputFilePath;
	String	outputFilePath;
	String	textInput;
	Boolean	enableRSAOaep;
	Boolean	addSaltEnabled;
	Boolean binaryOutputFileEnabled;
	
	public Boolean getBinaryOutputFileEnabled() 
	{
		return binaryOutputFileEnabled;
	}

	public void setBinaryOutputFileEnabled(Boolean binaryOutputFile) 
	{
		this.binaryOutputFileEnabled = binaryOutputFile;
	}

	public String getOutputFilePath() 
	{
		return outputFilePath;
	}

	public void setOutputFilePath(String outputFilePath) 
	{
		this.outputFilePath = outputFilePath;
	}

	public String getCipher() 
	{
		return cipher;
	}
	
	public void setCipher(String cipher) 
	{
		this.cipher = cipher;
	}
	
	public String getHashFunction() 
	{
		return hashFunction;
	}
	
	public void setHashFunction(String hashFunction) 
	{
		this.hashFunction = hashFunction;
	}
	
	public String getPassPhrase() 
	{
		return passPhrase;
	}
	
	public void setPassPhrase(String passPhrase) 
	{
		this.passPhrase = passPhrase;
	}
	
	public String getKeyFilePath() 
	{
		return keyFilePath;
	}
	
	public void setKeyFilePath(String keyFilePath) 
	{
		this.keyFilePath = keyFilePath;
	}
	
	public String getInputFilePath() 
	{
		return inputFilePath;
	}
	
	public void setInputFilePath(String encryptDecryptFilePath) 
	{
		this.inputFilePath = encryptDecryptFilePath;
	}
	
	public String getTextInput() 
	{
		return textInput;
	}
	
	public void setTextInput(String encryptDecryptTextInput) 
	{
		this.textInput = encryptDecryptTextInput;
	}
	
	public Boolean getEnableRSAOaep() 
	{
		return enableRSAOaep;
	}
	
	public void setEnableRSAOaep(Boolean enableRSAOaep) 
	{
		this.enableRSAOaep = enableRSAOaep;
	}
	
	public Boolean getAddSaltEnabled() 
	{
		return addSaltEnabled;
	}
	
	public void setAddSaltEnabled(Boolean addSalt) 
	{
		this.addSaltEnabled = addSalt;
	}
}
