package io.ozgard.cryptomall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.ozgard.cryptomall.model.Data;
import io.ozgard.cryptomall.service.RestService;

@RestController
public class RestApiController 
{
	@Autowired
	RestService restService;
	
	@Value("${version}")
	private String version;
	
	@GetMapping("/data")
	public List<Data> getDummies()
	{
		return restService.getData();	
	}
	
	@GetMapping("/data/{id}")
	public Data getDummyById(@PathVariable int id)
	{
		return restService.getDataById(id);
	}
	
	@GetMapping("/version")
	public String getVersion()
	{
		return version;
	}
	
	void setVersion(String version)
	{
		this.version = version;
	}
}
