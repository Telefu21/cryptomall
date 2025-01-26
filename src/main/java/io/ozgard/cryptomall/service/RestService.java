package io.ozgard.cryptomall.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import io.ozgard.cryptomall.model.Data;

@Service
public class RestService 
{
	public RestService() 
	{
	}

	List<Data> data = Arrays.asList(new Data(1, "name1"), new Data(2, "name2"), new Data(3, "name3"));
	
	public List<Data> getData()
	{
		return data;
	}
	
	public Data getDataById(int id)
	{
		return data.stream().filter(p-> p.getId() == id).findFirst().orElse(new Data(0, "name0"));
	}
}
