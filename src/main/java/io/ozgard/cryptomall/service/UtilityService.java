package io.ozgard.cryptomall.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;

@Service
public class UtilityService 
{
	private static final String UNKNOWN_CHARACTER = ".";
	
	UtilityService()
	{
		
	}
    
    public String bytesToHex(byte[] bytes) 
    {
        StringBuffer result = new StringBuffer();
        for (byte b : bytes) result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }
    
	public String asciiToHex(String asciiStr) 
	{
	    char[] chars = asciiStr.toCharArray();
	    StringBuilder hex = new StringBuilder();
	    
	    for (char ch : chars) 
	    {
	        hex.append(Integer.toHexString((int) ch));
	    }

	    return hex.toString();
	}

	public String hexToAscii(String hexStr) 
	{
	    StringBuilder output = new StringBuilder("");
	    
	    for (int i = 0; i < hexStr.length(); i += 2) 
	    {
	        String str = hexStr.substring(i, i + 2);
	        output.append((char) Integer.parseInt(str, 16));
	    }
	    
	    return output.toString();
	}
	
	public String convertFileToHex(String fileName) throws IOException  
	{
        StringBuilder result = new StringBuilder();
        StringBuilder hex = new StringBuilder();
        StringBuilder input = new StringBuilder();

        int count = 0;
        int value;

        try (InputStream inputStream = Files.newInputStream(Paths.get(fileName))) 
        {
			while ((value = inputStream.read()) != -1) 
			{

			    hex.append(String.format("%02X ", value));

			    if (!Character.isISOControl(value)) 
			    {
			        input.append((char) value);
			    } 
			    else 
			    {
			        input.append(UNKNOWN_CHARACTER);
			    }

			    if (count == 14) 
			    {
			        result.append(String.format("%-60s | %s%n", hex, input));
			        hex.setLength(0);
			        input.setLength(0);
			        count = 0;
			    } 
			    else 
			    {
			        count++;
			    }
			}
		}
        
        if (count > 0) 
        {
            result.append(String.format("%-60s | %s%n", hex, input));
        }
        
        return result.toString();
    }
}
