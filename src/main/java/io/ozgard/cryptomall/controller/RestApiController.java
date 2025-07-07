package io.ozgard.cryptomall.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

@RestController
public class RestApiController 
{
	@Value("${version}")
	private String version;
	private String workingDir;
	
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
		Path folderPath = Paths.get("web_app_wd");
		
		if (!(Files.exists(folderPath) && Files.isDirectory(folderPath))) 
		{
			try 
	        {
	            Files.createDirectory(folderPath);
	            workingDir = folderPath.toAbsolutePath().toString();
	            
	        } 
	        catch (IOException e) 
	        {
	            System.err.println("Error creating folder: " + e.getMessage());
	        }
        } 
	}
	
	@PostMapping("/keygenerateparams")
    public String createUser(@RequestBody KeyGenerateParams params) 
	{
		return "algorithm: " + params.getKeyGenAlgo() + ", length: " + params.getKeyLength();
    }
}
