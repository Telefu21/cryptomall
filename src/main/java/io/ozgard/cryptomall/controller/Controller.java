package io.ozgard.cryptomall.controller;

import org.springframework.stereotype.Component;

import io.ozgard.cryptomall.params.FileConvertParams;
import io.ozgard.cryptomall.service.OpenSslService;

@Component
public class Controller 
{
	protected String fileConvertOperations(int fileConvertOperationId, FileConvertParams fileConvertParams, OpenSslService openSslService, String outputFileName, String passWd)
	{
		switch(fileConvertOperationId)
		{
			case FileConvertParams.FILE_CONVERT_DER_TO_PEM:
				fileConvertParams.setOutputFilePath("\"" + outputFileName + ".pem" + "\"");
				
				return openSslService.convertFileDerToPem(fileConvertParams);
				
			case FileConvertParams.FILE_CONVERT_PEM_TO_DER:
				fileConvertParams.setOutputFilePath("\"" + outputFileName + ".der" + "\"");
				
				return openSslService.convertFilePemToDer(fileConvertParams);
				
			case FileConvertParams.FILE_CONVERT_PRIVKEY_TO_VIEW:
				if(passWd.length() >= 4)
				{
					fileConvertParams.setFileEncryptionPassword(passWd);
					fileConvertParams.setEncryptKeyFile(true);
				}
				else
				{
					fileConvertParams.setEncryptKeyFile(false);
				}
				
				return openSslService.privKeyView(fileConvertParams);
				
			case FileConvertParams.FILE_CONVERT_PUBKEY_TO_VIEW:
				return openSslService.pubKeyView(fileConvertParams);
				
			case FileConvertParams.FILE_CONVERT_PUB_FROM_PRIV:
				fileConvertParams.setOutputFilePath("\"" + outputFileName  + "_pub.pem" + "\"");
				
				if(passWd.length() >= 4)
				{
					fileConvertParams.setFileEncryptionPassword(passWd);
					fileConvertParams.setEncryptKeyFile(true);
				}
				else
				{
					fileConvertParams.setEncryptKeyFile(false);
				}
				
				return openSslService.pubKeyGenerate(fileConvertParams);
				
			case FileConvertParams.FILE_CONVERT_FROM_BASE64:
				fileConvertParams.setOutputFilePath("\"" + outputFileName + ".file" + "\"");
				
				return openSslService.convertFileBase64ToAny(fileConvertParams);
				
			case FileConvertParams.FILE_CONVERT_TO_BASE64:
				fileConvertParams.setOutputFilePath("\"" + outputFileName + ".b64" + "\"");
				
				return openSslService.convertFileAnyToBase64(fileConvertParams);
				
			case FileConvertParams.FILE_CONVERT_VIEW_CERTIFICATE:
				return openSslService.convertFileViewCertificate(fileConvertParams);
				
			case FileConvertParams.FILE_CONVERT_VIEW_CRL:
				return openSslService.convertFileViewCrlCertificate(fileConvertParams);
				
			case FileConvertParams.FILE_CONVERT_VIEW_CSR:
				return openSslService.convertFileViewCsrCertificate(fileConvertParams);
				
			case FileConvertParams.FILE_CONVERT_PEM_TO_ASN1:
				return openSslService.convertFilePemToAnsi(fileConvertParams);
		}
		
		return "";
	}

}
