package io.ozgard.cryptomall.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;

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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.ozgard.cryptomall.params.FileConvertParams;
import io.ozgard.cryptomall.params.KeyGenerateParams;
import io.ozgard.cryptomall.params.PostQuantumCryptoParams;
import io.ozgard.cryptomall.params.PostQuantumCryptoRestApiReturnEntities;
import io.ozgard.cryptomall.params.PrimeGenerateParams;
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
	
	@PostMapping("/v1/keygenerate")
    public ResponseEntity<Resource> keyGenerate(@RequestBody KeyGenerateParams params) 
	{
		params.setInputFilePath(workingDir + Utility.getDoublePathSeperator() + "keyfile");
		params.setOutputFilePath(params.getInputFilePath());
		
		logger.info(openSslService.keyGenerate(params));
		
		return returnFile(params.getOutputFilePath());
	}
	
	@PostMapping("/v1/keygeneratepqckemkyber")
    public PostQuantumCryptoRestApiReturnEntities keyGeneratePqcKemKyber(@RequestBody PostQuantumCryptoParams params) 
	{
		return keyGeneratePqcKem(params, param -> postQuantumCryptoService.keyPairGenerateKyber(param));
	}
	
	@PostMapping("/v1/keygeneratepqckemhqc")
    public PostQuantumCryptoRestApiReturnEntities keyGeneratePqcKemHQC(@RequestBody PostQuantumCryptoParams params) 
	{
		return keyGeneratePqcKem(params, param -> postQuantumCryptoService.keyPairGenerateHQC(param));
	}
	
	@PostMapping("/v1/keygeneratepqckembike")
    public PostQuantumCryptoRestApiReturnEntities keyGeneratePqcKemBike(@RequestBody PostQuantumCryptoParams params) 
	{
		return keyGeneratePqcKem(params, param -> postQuantumCryptoService.keyPairGenerateBike(param));
	}
	
	@PostMapping("/v1/keygeneratepqckemmceliece")
    public PostQuantumCryptoRestApiReturnEntities keyGeneratePqcKemMceliece(@RequestBody PostQuantumCryptoParams params) 
	{
		return keyGeneratePqcKem(params, param -> postQuantumCryptoService.keyPairGenerateClassicMceliece(param));
	}

	@PostMapping("/v1/primegenerate")
    public String primeGenerate(@RequestBody PrimeGenerateParams params) 
	{
		String retStr = openSslService.generatePrime(params);

		logger.info("Generated Prime Number: " + retStr);

		return retStr;
	}
	
	@PostMapping(value = "/v1/fileconvert", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Resource> fileConvert(@RequestPart("params") FileConvertParams params, @RequestPart("file") MultipartFile file) 
	{
		if (file.isEmpty()) 
		{
			return ResponseEntity.badRequest().build();
        }
		
		params.setInputFilePath(workingDir + Utility.getDoublePathSeperator() + "filconvertinput");
		
		try 
		{
			Utility.writeBytesToFile(file.getBytes(), params.getInputFilePath());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		String outputFileName = workingDir + Utility.getDoublePathSeperator() + "filconvertoutput";
		
		int fileOperationIdNo = params.getFileOperationIdNo();
		
		String retStr = fileConvertOperations(fileOperationIdNo, params, openSslService, outputFileName, params.getFileEncryptionPassword());
		
		if(fileOperationIdNo == FileConvertParams.FILE_CONVERT_PRIVKEY_TO_VIEW ||
		   fileOperationIdNo == FileConvertParams.FILE_CONVERT_PUBKEY_TO_VIEW ||
		   fileOperationIdNo == FileConvertParams.FILE_CONVERT_VIEW_CERTIFICATE ||
		   fileOperationIdNo == FileConvertParams.FILE_CONVERT_VIEW_CRL ||
		   fileOperationIdNo == FileConvertParams.FILE_CONVERT_VIEW_CSR ||
		   fileOperationIdNo == FileConvertParams.FILE_CONVERT_PEM_TO_ASN1)
		{
			Utility.writeBytesToFile(retStr.getBytes(), outputFileName);
			
			params.setOutputFilePath(outputFileName);
			
			logger.info("converted file sent to client");
		}
		else
		{
			logger.info(retStr);
		}
		
		return returnFile(params.getOutputFilePath());
    }
	
	@PostMapping(value = "/v1/signaturegenerate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Resource> signatureGenerate(@RequestPart("params") SignVerifyPrimeParams params, @RequestPart("inputfile") MultipartFile inputFile, @RequestPart("privatekeyfile") MultipartFile privateKeyFile) 
	{
		if (inputFile.isEmpty() || privateKeyFile.isEmpty()) 
		{
			return ResponseEntity.badRequest().build();
        }
		
		params.setInputFilePath(workingDir + Utility.getDoublePathSeperator() + "signgeninput");
		params.setKeyFilePath(workingDir + Utility.getDoublePathSeperator() + "signgenprivkey");
		
		try 
		{
			Utility.writeBytesToFile(inputFile.getBytes(), params.getInputFilePath());
			Utility.writeBytesToFile(privateKeyFile.getBytes(), params.getKeyFilePath());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		params.setOutputFilePath(workingDir + Utility.getDoublePathSeperator() + "signgenoutput");
		openSslService.generateSignature(params);
		
		return returnFile(params.getOutputFilePath());
    }

	@PostMapping(value = "/v1/signatureverify", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String signatureVerify(@RequestPart("params") SignVerifyPrimeParams params, @RequestPart("inputfile") MultipartFile inputFile, @RequestPart("publickeyfile") MultipartFile publicKeyFile, @RequestPart("signaturefile") MultipartFile signatureFile) 
	{
		if (inputFile.isEmpty() || publicKeyFile.isEmpty()  || signatureFile.isEmpty()) 
		{
			return "Error: Files are empty !!!!";
        }
		
		params.setInputFilePath(workingDir + Utility.getDoublePathSeperator() + "signverinput");
		params.setKeyFilePath(workingDir + Utility.getDoublePathSeperator() + "signverpubkey");
		params.setSignatureFilePath(workingDir + Utility.getDoublePathSeperator() + "signveroutput");
		
		try 
		{
			Utility.writeBytesToFile(inputFile.getBytes(), params.getInputFilePath());
			Utility.writeBytesToFile(publicKeyFile.getBytes(), params.getKeyFilePath());
			Utility.writeBytesToFile(signatureFile.getBytes(), params.getSignatureFilePath());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return openSslService.verifySignature(params);
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
	
	private PostQuantumCryptoRestApiReturnEntities keyGeneratePqcKem(PostQuantumCryptoParams params, Function<PostQuantumCryptoParams, String> func) 
	{
		params.setWorkingDirectoryPath(workingDir);
		
		func.apply(params);
		
		PostQuantumCryptoRestApiReturnEntities retEntities = new PostQuantumCryptoRestApiReturnEntities();
		
		retEntities.setPrivateKey(params.getPrivateKey());
		retEntities.setPublicKey(params.getPublicKey());
		
		return retEntities;
	}
}
