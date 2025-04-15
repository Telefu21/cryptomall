package io.ozgard.cryptomall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import io.ozgard.cryptomall.service.RestService;

@RestController
public class RestApiController 
{
	@Autowired
	RestService restService;
	
	@Value("${version}")
	private String version;
}
