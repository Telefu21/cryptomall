package io.ozgard.cryptomall.utility;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileOutputStream;

public class Utility 
{
	private static final String UNKNOWN_CHARACTER = ".";

	static public void stringHexToFile(String filePath, String hexString) 
	{
        byte[] byteArray = hexStringToByteArray(hexString);

        try (FileOutputStream fos = new FileOutputStream(filePath)) 
		{
            fos.write(byteArray);
        } 
		catch (IOException e) 
		{
            e.printStackTrace();
        }
	}

	static public byte[] hexStringToByteArray(String hexString) 
	{
		String pureHexStr = hexString.replaceAll(" 0x", "").replaceAll("0x", "").replaceAll(" 0X", "").replaceAll("0X", "");
		int len = pureHexStr.length();
		byte[] data = new byte[len / 2];
		
		for (int i = 0; i < len; i += 2) 
		{
			data[i / 2] = (byte) ((Character.digit(pureHexStr.charAt(i), 16) << 4) + Character.digit(pureHexStr.charAt(i+1), 16));
		}

		return data;
	}

	static public byte [] readFileContentToBytes(String filePathName)
	{
		try 
        {
            return Files.readAllBytes(Paths.get(filePathName));
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
		
		return null;
	}
    
	static public String bytesToHex(byte[] bytes) 
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

	static public String hexToAscii(String hexStr) 
	{
	    StringBuilder output = new StringBuilder("");
	    
	    for (int i = 0; i < hexStr.length(); i += 2) 
	    {
	        String str = hexStr.substring(i, i + 2);
	        output.append((char) Integer.parseInt(str, 16));
	    }
	    
	    return output.toString();
	}
	
	static public String convertFileToHex(String fileName) throws IOException  
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
	
	static public void writeBytesToFile(byte [] data, String fileName)
	{
		try 
		{
			Files.write(Paths.get(fileName), data);
		} 
		catch (IOException e) 
		{
			System.err.println("Error writing to file: " + e.getMessage());
		}
	}
	
	static public String getPathSeperator()
	{
		String os = System.getProperty("os.name").toLowerCase();
		String retStr = "";
		
		if(os.contains("win"))
		{
			retStr = "\\";
		}
		
		if(os.contains("nix") || os.contains("nux") || os.contains("aix"))
		{
			retStr = "/";
		}
		
		return retStr;
	}
}
