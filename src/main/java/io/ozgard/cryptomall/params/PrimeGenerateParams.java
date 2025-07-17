package io.ozgard.cryptomall.params;

import org.springframework.stereotype.Component;

@Component
public class PrimeGenerateParams 
{
	String	primeLength;
	boolean	safePrime;
	boolean	hexOutPrime;
	
	public String getPrimeLength() 
	{
		return primeLength;
	}
	
	public void setPrimeLength(String primeLength) 
	{
		this.primeLength = primeLength;
	}
	
	public boolean isSafePrime() 
	{
		return safePrime;
	}
	
	public void setSafePrime(boolean safePrime) 
	{
		this.safePrime = safePrime;
	}
	
	public boolean isHexOutPrime() 
	{
		return hexOutPrime;
	}
	
	public void setHexOutPrime(boolean hexOutPrime) 
	{
		this.hexOutPrime = hexOutPrime;
	}	
}
