package io.ozgard.cryptomall.params;

import org.springframework.stereotype.Component;

@Component
public class CrcParams 
{
	public static CrcParams[] Crc8PredefinedParams = {	new CrcParams(8 , 0x07, 0x00, false, false, 0x0, "CRC 8"),
														new CrcParams(8 , 0x1D, 0xFF, false, false, 0xFF, "SAE J1850"),
														new CrcParams(8 , 0x1D, 0x00, false, false, 0x00, "SAE J1850 ZERO"),
														new CrcParams(8 , 0x2F, 0xFF, false, false, 0xFF, "8H2F"),
														new CrcParams(8 , 0x9B, 0xFF, false, false, 0x00, "CDMA2000"),
														new CrcParams(8 , 0x39, 0x00, true , true , 0x00,"DARC"),
														new CrcParams(8 , 0xD5, 0x00, false, false, 0x00, "DVB S2"),
														new CrcParams(8 , 0x1D, 0xFF, true , true , 0x00, "EBU"),
														new CrcParams(8 , 0x1D, 0xFD, false, false, 0x00, "ICODE"),
														new CrcParams(8 , 0x07, 0x00, false, false, 0x55, "ITU"),
														new CrcParams(8 , 0x31, 0x00, true , true , 0x00, "MAXIM"),
														new CrcParams(8 , 0x07, 0xFF, true , true , 0x00, "ROHC"),
														new CrcParams(8 , 0x9B, 0x00, true , true , 0x00, "WCDMA")};
	
	public static CrcParams[] Crc16PredefinedParams = {	new CrcParams(16, 0x1021, 0x0000, false, false, 0x0000, "CCITT ZERO"),
														new CrcParams(16, 0x8005, 0x0000, true , true , 0x0000, "ARC"),
														new CrcParams(16, 0x1021, 0x1D0F, false, false, 0x0000, "AUG CCITT"),
														new CrcParams(16, 0x8005, 0x0000, false, false, 0x0000, "BUYPASS"),
														new CrcParams(16, 0x1021, 0xFFFF, false, false, 0x0000, "CCITT FALSE"),
														new CrcParams(16, 0xC867, 0xFFFF, false, false, 0x0000, "CDMA2000"),
														new CrcParams(16, 0x8005, 0x800D, false, false, 0x0000, "DDS 110"),
														new CrcParams(16, 0x0589, 0x0000, false, false, 0x0001, "DECT R"),
														new CrcParams(16, 0x0589, 0x0000, false, false, 0x0000, "DECT X"),
														new CrcParams(16, 0x3D65, 0x0000, true , true , 0xFFFF, "DNP"),
														new CrcParams(16, 0x3D65, 0x0000, false, false, 0xFFFF, "EN13757"),
														new CrcParams(16, 0x1021, 0xFFFF, false, false, 0xFFFF, "GENIBUS"),
														new CrcParams(16, 0x8005, 0x0000, true , true , 0xFFFF, "MAXIM"),
														new CrcParams(16, 0x1021, 0xFFFF, true , true , 0x0000, "MCRF4XX"),
														new CrcParams(16, 0x1021, 0xB2AA, true , true , 0x0000, "RIELLO"),
														new CrcParams(16, 0x8BB7, 0x0000, false, false, 0x0000, "T10 DIF"),
														new CrcParams(16, 0xA097, 0x0000, false, false, 0x0000, "TELEDISK"),
														new CrcParams(16, 0x1021, 0x89EC, true , true , 0x0000, "TMS37157"),
														new CrcParams(16, 0x8005, 0xFFFF, true , true , 0xFFFF, "USB"),
														new CrcParams(16, 0x1021, 0xC6C6, true , true , 0x0000, "A"),
														new CrcParams(16, 0x1021, 0x0000, true , true , 0x0000, "KERMIT"),
														new CrcParams(16, 0x8005, 0xFFFF, true , true , 0x0000, "MODBUS"),
														new CrcParams(16, 0x1021, 0xFFFF, true , true , 0xFFFF, "X 25"),
														new CrcParams(16, 0x1021, 0x0000, false, false, 0x0000, "X MODEM")};

	public static CrcParams[] Crc32PredefinedParams = {	new CrcParams(32, 0x04C11DB7, 0xFFFFFFFF, true , true , 0xFFFFFFFF, "CRC32 IEEE"),
														new CrcParams(32, 0x04C11DB7, 0xFFFFFFFF, false, false, 0xFFFFFFFF, "BZIP2"),
														new CrcParams(32, 0x1EDC6F41, 0xFFFFFFFF, true , true , 0xFFFFFFFF, "C"),
														new CrcParams(32, 0xA833982B, 0xFFFFFFFF, true , true , 0xFFFFFFFF, "D"),
														new CrcParams(32, 0x04C11DB7, 0xFFFFFFFF, false, false, 0x00000000, "MPEG2"),
														new CrcParams(32, 0x04C11DB7, 0x00000000, false, false, 0xFFFFFFFF, "POSIX"),
														new CrcParams(32, 0x814141AB, 0x00000000, false, false, 0x00000000, "Q"),
														new CrcParams(32, 0x04C11DB7, 0xFFFFFFFF, true , true , 0x00000000, "JAMCRC"),
														new CrcParams(32, 0x000000AF, 0x00000000, false, false, 0x00000000, "XFER")};

	public static CrcParams[] Crc64PredefinedParams = {	new CrcParams(64, 0x42F0E1EBA9EA3693L, 0x0000000000000000L, false, false, 0x0000000000000000L, "ECMA 182"),
														new CrcParams(64, 0x000000000000001BL, 0xFFFFFFFFFFFFFFFFL, true , true , 0xFFFFFFFFFFFFFFFFL, "GO ISO"),
														new CrcParams(64, 0x42F0E1EBA9EA3693L, 0xFFFFFFFFFFFFFFFFL, false, false, 0xFFFFFFFFFFFFFFFFL, "WE"),
														new CrcParams(64, 0x42F0E1EBA9EA3693L, 0xFFFFFFFFFFFFFFFFL, true , true , 0xFFFFFFFFFFFFFFFFL, "XZ")};
	
	private int 		width;  
	private long 		polynomial;
	private boolean 	reflectIn; 
	private boolean 	reflectOut; 
	private long 		init;
	private long 		finalXor;
	private String 		predefinedParamsStr;
	 
	public CrcParams(int width, long polynomial, long init, boolean reflectIn, boolean reflectOut, long finalXor, String predefinedParamsStr)
	{
	     this.width = width;
	     this.polynomial = polynomial;
	     this.reflectIn = reflectIn;
	     this.reflectOut = reflectOut;
	     this.init = init;
	     this.finalXor = finalXor;
	     this.predefinedParamsStr = predefinedParamsStr;
	}
	
	public CrcParams(CrcParams orig)
	{
		 width = orig.width;
		 polynomial = orig.polynomial;
		 reflectIn = orig.reflectIn;
		 reflectOut = orig.reflectOut;
		 init = orig.init;
		 finalXor = orig.finalXor;
	}
	
	public CrcParams()
	{
		
	}
	
	public String getPredefinedParamsStr() 
	{
		return predefinedParamsStr;
	}

	public void setPredefinedParamsStr(String predefinedParamsStr) 
	{
		this.predefinedParamsStr = predefinedParamsStr;
	}

	public void setWidth(int width) 
	{
		this.width = width;
	}

	public void setPolynomial(long polynomial) 
	{
		this.polynomial = polynomial;
	}

	public void setReflectIn(boolean reflectIn) 
	{
		this.reflectIn = reflectIn;
	}

	public void setReflectOut(boolean reflectOut) 
	{
		this.reflectOut = reflectOut;
	}

	public void setInit(long init) 
	{
		this.init = init;
	}

	public void setFinalXor(long finalXor) 
	{
		this.finalXor = finalXor;
	}
	
	 public int getWidth()
	 {
	     return width;
	 }
	
	 public long getPolynomial()
	 {
	     return polynomial;
	 }
	
	 public boolean isReflectIn()
	 {
	     return reflectIn;
	 }
	
	 public boolean isReflectOut()
	 {
	     return reflectOut;
	 }
	
	 public long getInit()
	 {
	     return init;
	 }
	
	 public long getFinalXor()
	 {
	     return finalXor;
	 }
}
