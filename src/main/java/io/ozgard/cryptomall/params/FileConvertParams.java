package io.ozgard.cryptomall.params;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class FileConvertParams 
{
	public static final int FILE_CONVERT_PUB_FROM_PRIV = 0;
	public static final int FILE_CONVERT_PUBKEY_TO_VIEW = 1;
	public static final int FILE_CONVERT_PRIVKEY_TO_VIEW = 2;
	public static final int FILE_CONVERT_DER_TO_PEM = 3;
	public static final int FILE_CONVERT_PEM_TO_DER = 4;
	public static final int FILE_CONVERT_TO_BASE64 = 5;
	public static final int FILE_CONVERT_FROM_BASE64 = 6;
	public static final int FILE_CONVERT_VIEW_CERTIFICATE = 7;
	public static final int FILE_CONVERT_VIEW_CRL = 8;
	public static final int FILE_CONVERT_VIEW_CSR = 9;
	public static final int FILE_CONVERT_PEM_TO_ASN1 = 10;
	
	Map<Integer, String> convertOperationIdToName;
	
	String	fileEncryptionPassword;
	String	outputFilePath;
	String	inputFilePath;
	int		fileOperationIdNo;
	Boolean encryptKeyFile;
	
	public FileConvertParams()
	{
		convertOperationIdToName = new HashMap<>();
		
		convertOperationIdToName.put(FILE_CONVERT_PUB_FROM_PRIV, "Get Public Key from Private Key File - Only PEM File");
		convertOperationIdToName.put(FILE_CONVERT_PUBKEY_TO_VIEW, "View Public Key File in Human Readable Form - Only PEM File");
		convertOperationIdToName.put(FILE_CONVERT_PRIVKEY_TO_VIEW, "View Private Key File in Human Readable Form - Only PEM File");
		convertOperationIdToName.put(FILE_CONVERT_DER_TO_PEM, "Convert DER File to PEM File");
		convertOperationIdToName.put(FILE_CONVERT_PEM_TO_DER, "Convert PEM File to DER File");
		convertOperationIdToName.put(FILE_CONVERT_TO_BASE64, "Convert Any File to base64 File");
		convertOperationIdToName.put(FILE_CONVERT_FROM_BASE64, "Convert Base64 File to Any File");
		convertOperationIdToName.put(FILE_CONVERT_VIEW_CERTIFICATE, "View Certificate - Only PEM File");
		convertOperationIdToName.put(FILE_CONVERT_VIEW_CRL, "View CRL Certificate - Only PEM File");
		convertOperationIdToName.put(FILE_CONVERT_VIEW_CSR, "View CSR Certificate - Only PEM File");
		convertOperationIdToName.put(FILE_CONVERT_PEM_TO_ASN1, "Parse PEM to ASN1");
	}
	
	public Boolean getEncryptKeyFile() 
	{
		return encryptKeyFile;
	}

	public void setEncryptKeyFile(Boolean encryptKeyFile) 
	{
		this.encryptKeyFile = encryptKeyFile;
	}

	public Map<Integer, String> getConvertOperationIdToName() 
	{
		return convertOperationIdToName;
	}

	public void setConvertOperationIdToName(Map<Integer, String> convertOperationIdToName) 
	{
		this.convertOperationIdToName = convertOperationIdToName;
	}

	public String getFileEncryptionPassword() 
	{
		return fileEncryptionPassword;
	}
	
	public void setFileEncryptionPassword(String fileEncryptionPassword) 
	{
		this.fileEncryptionPassword = fileEncryptionPassword;
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
	
	public int getFileOperationIdNo() 
	{
		return fileOperationIdNo;
	}
	
	public void setFileOperationIdNo(int fileOperationIdNo) 
	{
		this.fileOperationIdNo = fileOperationIdNo;
	}
}
