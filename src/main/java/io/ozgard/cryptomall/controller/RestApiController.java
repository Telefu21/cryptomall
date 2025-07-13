package io.ozgard.cryptomall.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.ozgard.cryptomall.params.CertificateParams;
import io.ozgard.cryptomall.params.CrcParams;
import io.ozgard.cryptomall.params.EncryptDecryptParams;
import io.ozgard.cryptomall.params.FileConvertParams;
import io.ozgard.cryptomall.params.KeyGenerateParams;
import io.ozgard.cryptomall.params.PostQuantumCryptoParams;
import io.ozgard.cryptomall.params.SignVerifyPrimeParams;
import io.ozgard.cryptomall.service.CRCService;
import io.ozgard.cryptomall.service.OpenSslService;
import io.ozgard.cryptomall.service.PostQuantumCryptoService;
import io.ozgard.cryptomall.utility.Utility;

@RestController
public class RestApiController extends Controller
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
	@Autowired
	FileConvertParams fileConvertParams;
	
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
		
		return returnFile(params.getOutputFilePath());
	}
	
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@RequestMapping("/fileConvertparams")
    public ResponseEntity<Resource> handleMixedUpload(@RequestPart("metadata") FileConvertParams params, @RequestPart("file") MultipartFile file) 
	{
		if (file.isEmpty()) 
		{
			return ResponseEntity.badRequest().build();
        }
		
		fileConvertParams.setInputFilePath(workingDir + Utility.getDoublePathSeperator() + "tmp");
		
		try 
		{
			Utility.writeBytesToFile(file.getBytes(), fileConvertParams.getInputFilePath());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		String outputFileName = workingDir + Utility.getPathSeperator() + workingDir.split(Utility.getDoublePathSeperator())[workingDir.split(Utility.getDoublePathSeperator()).length - 1].split("\\.")[0];
		
		int fileConvertOperationId = 0;

		logger.info(fileConvertOperations(fileConvertOperationId, fileConvertParams, openSslService, outputFileName, fileConvertParams.getFileEncryptionPassword()));
		
		return returnFile(params.getOutputFilePath());
    }
	
	private ResponseEntity<Resource> returnFile(String fileName)
	{
		Path filePath = Paths.get(fileName);
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
