package io.ozgard.cryptomall.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.ozgard.cryptomall.params.CertificateParams;
import io.ozgard.cryptomall.params.CrcParams;
import io.ozgard.cryptomall.params.EncryptDecryptParams;
import io.ozgard.cryptomall.params.KeyGenerateParams;
import io.ozgard.cryptomall.params.PostQuantumCryptoParams;
import io.ozgard.cryptomall.params.SignVerifyPrimeParams;
import io.ozgard.cryptomall.service.CRCService;
import io.ozgard.cryptomall.service.OpenSslService;
import io.ozgard.cryptomall.service.PostQuantumCryptoService;
import io.ozgard.cryptomall.utility.Utility;

@RestController
public class RestApiController 
{
	@Value("${version}")
	private String version;
	
	private String workingDir;
	
	private Logger logger;
	
	@Autowired
	private OpenSslService openSslService;
	@Autowired
	private CRCService crcService;
	@Autowired
	private PostQuantumCryptoService postQuantumCryptoService;
	
	@Autowired
	KeyGenerateParams keygenParams;
	@Autowired
	EncryptDecryptParams encryptDecryptParams;
	@Autowired
	SignVerifyPrimeParams signVerifyPrimeParams;
	@Autowired
	CrcParams crcParams;
	@Autowired
	CertificateParams certificateParams;
	@Autowired
	PostQuantumCryptoParams postQuantumCryptoParams;
	
	RestApiController()
	{
		logger = LogManager.getLogger(RestApiController.class);
		
		Path folderPath = Paths.get("web_app_wd");
		
		if (!(Files.exists(folderPath) && Files.isDirectory(folderPath))) 
		{
			try 
	        {
	            Files.createDirectory(folderPath);
	        } 
	        catch (IOException e) 
	        {
	            System.err.println("Error creating folder: " + e.getMessage());
	        }
        } 
		
		workingDir = folderPath.toAbsolutePath().toString().replace("\\", "\\\\");
	}
	
	@PostMapping("/keygenerateparams")
    public ResponseEntity<Resource> keyGenerate(@RequestBody KeyGenerateParams params) 
	{
		params.setInputFilePath(workingDir + Utility.getDoublePathSeperator() + "keyfile");
		params.setOutputFilePath(params.getInputFilePath());
		
		params.setEncryptKeyFile(true);
		
		if(params.getFileEncryptionPassword() == "")
		{
			params.setEncryptKeyFile(false);
		}
		
		logger.info(openSslService.keyGenerate(params));
		
		Path filePath = Paths.get(params.getOutputFilePath());
		Resource resource = null;
		
		try 
		{
			resource = new UrlResource(filePath.toUri());
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		}

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filePath.getFileName().toString() + "\"").body(resource);
    }
}
