package io.ozgard.cryptomall.params;

import org.springframework.stereotype.Component;

@Component
public class SignVerifyPrimeParams 
{
	String	HashFunction;
	String	SaltLen;
	String	inputFilePath;
	String	keyFilePath;
	String	signatureFilePath;
	String	OutputFilePath;
	String	PrimeLength;
	boolean	isSafePrime;
	boolean	isHexOutPrime;
	boolean	isRsaPssEnabled;
	
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
		return HashFunction;
	}
	
	public void setHashFunction(String hashFunction) 
	{
		HashFunction = hashFunction;
	}
	
	public String getSaltLen() 
	{
		return SaltLen;
	}
	
	public void setSaltLen(String saltLen) 
	{
		SaltLen = saltLen;
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
		return isRsaPssEnabled;
	}
	
	public void setRsaPssEnabled(boolean isRsaPssEnabled) 
	{
		this.isRsaPssEnabled = isRsaPssEnabled;
	}
	
	
}
