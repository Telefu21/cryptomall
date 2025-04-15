package io.ozgard.cryptomall.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApiController 
{
	@Value("${version}")
	private String version;
}
