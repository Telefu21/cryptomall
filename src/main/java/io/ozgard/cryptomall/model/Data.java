package io.ozgard.cryptomall.model;

import org.springframework.stereotype.Component;

@Component
public class Data 
{
	private int Id;
	private String name;
	
	public Data(int id, String name) 
	{
		Id = id;
		this.name = name;
	}
	
	public Data() 
	{
	}
	
	public int getId() 
	{
		return Id;
	}
	public void setId(int id) 
	{
		Id = id;
	}
	
	public String getName() 
	{
		return name;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
}
