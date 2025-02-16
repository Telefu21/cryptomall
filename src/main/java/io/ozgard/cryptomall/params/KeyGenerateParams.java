package io.ozgard.cryptomall.params;

import org.springframework.stereotype.Component;

import javafx.util.Callback;

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
	public static final String KEYGEN_CONVERT_PUB_FROM_PRIV = "Get Public Key from Private Key File - Input File PEM";
	public static final String KEYGEN_CONVERT_PUBKEY_TO_VIEW = "View Public Key File in Human Readable Form - Input File PEM";
	public static final String KEYGEN_CONVERT_PRIVKEY_TO_VIEW = "View Private Key File in Human Readable Form - Input File PEM";
	public static final String KEYGEN_CONVERT_DER_TO_PEM = "Convert DER File to PEM File";
	public static final String KEYGEN_CONVERT_PEM_TO_DER = "Convert PEM File to DER File";
	public static final String KEYGEN_CONVERT_TO_BASE64 = "Convert Any File to base64 File";
	public static final String KEYGEN_CONVERT_FROM_BASE64 = "Convert Base64 File to Any File";
	public static final String KEYGEN_KEY_LENGHT_512 = "512";
	public static final String KEYGEN_KEY_LENGHT_1024 = "1024";
	public static final String KEYGEN_KEY_LENGHT_2048 = "2048";
	public static final String KEYGEN_KEY_LENGHT_4096 = "4096";
	
	String	pubKeyFilePath;
	String	privKeyFilePath;
	String	paramFilePath;
	String	keyGenAlgo;
	String	keyFileFormat;
	String	keyLength;
	String	elepticCurveName;
	String	fileEncryptionCipher;
	String	fileEncryptionPassword;
	Boolean encryptKeyFile;
	
	public String getPubKeyFilePath() 
	{
		return pubKeyFilePath;
	}
	
	public void setPubKeyFilePath(String pubKeyFilePath) 
	{
		this.pubKeyFilePath = pubKeyFilePath;
	}
	
	public String getPrivKeyFilePath() 
	{
		return privKeyFilePath;
	}
	
	public void setPrivKeyFilePath(String privKeyFilePath) 
	{
		this.privKeyFilePath = privKeyFilePath;
	}
	
	public String getParamFilePath() 
	{
		return paramFilePath;
	}
	
	public void setParamFilePath(String paramFilePath) 
	{
		this.paramFilePath = paramFilePath;
	}
	
	public String getKeyGenAlgo() 
	{
		return keyGenAlgo;
	}
	
	public void setKeyGenAlgo(String keyGenAlgo) 
	{
		this.keyGenAlgo = keyGenAlgo;
	}
	
	public String getKeyFileFormat() 
	{
		return keyFileFormat;
	}
	
	public void setKeyFileFormat(String keyFileFormat) 
	{
		this.keyFileFormat = keyFileFormat;
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
}
