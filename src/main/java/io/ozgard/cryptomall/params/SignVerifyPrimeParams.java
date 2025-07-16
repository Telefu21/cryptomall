package io.ozgard.cryptomall.params;

import org.springframework.stereotype.Component;

@Component
public class SignVerifyPrimeParams 
{
	String	hashFunction;
	String	saltLen;
	String	inputFilePath;
	String	keyFilePath;
	String	signatureFilePath;
	String	OutputFilePath;
	String	PrimeLength;
	boolean	isSafePrime;
	boolean	isHexOutPrime;
	boolean	rsaPssEnabled;
	
	public String getOutputFilePath() 
	{
		return OutputFilePath;
	}

	public void setOutputFilePath(String outputFilePath) 
	{
		OutputFilePath = outputFilePath;
	}

	public String getHashFunction() 
	{
		return hashFunction;
	}
	
	public void setHashFunction(String hashFunction) 
	{
		this.hashFunction = hashFunction;
	}
	
	public String getSaltLen() 
	{
		return saltLen;
	}
	
	public void setSaltLen(String saltLen) 
	{
		this.saltLen = saltLen;
	}
	
	public String getInputFilePath() 
	{
		return inputFilePath;
	}
	
	public void setInputFilePath(String inputFilePath) 
	{
		this.inputFilePath = inputFilePath;
	}
	
	public String getKeyFilePath() 
	{
		return keyFilePath;
	}
	
	public void setKeyFilePath(String keyFilePath) 
	{
		this.keyFilePath = keyFilePath;
	}
	
	public String getSignatureFilePath() 
	{
		return signatureFilePath;
	}
	
	public void setSignatureFilePath(String signatureFilePath) 
	{
		this.signatureFilePath = signatureFilePath;
	}
	
	public String getPrimeLength() 
	{
		return PrimeLength;
	}
	
	public void setPrimeLength(String primeLength) 
	{
		PrimeLength = primeLength;
	}
	
	public boolean isSafePrime() 
	{
		return isSafePrime;
	}
	
	public void setSafePrime(boolean isSafePrime) 
	{
		this.isSafePrime = isSafePrime;
	}
	
	public boolean isHexOutPrime() 
	{
		return isHexOutPrime;
	}
	
	public void setHexOutPrime(boolean isHexOutPrime) 
	{
		this.isHexOutPrime = isHexOutPrime;
	}

	public boolean isRsaPssEnabled() 
	{
		return rsaPssEnabled;
	}

	public void setRsaPssEnabled(boolean rsaPssEnabled) 
	{
		this.rsaPssEnabled = rsaPssEnabled;
	}
	
	
}
