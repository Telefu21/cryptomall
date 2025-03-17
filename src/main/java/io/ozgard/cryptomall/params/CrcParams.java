package io.ozgard.cryptomall.params;

public class CrcParams 
{
	 public static final String ENCRYPT_DECRYPT_TYPE_ASYM_ENCRYPTION = "Asymmetric Encryption";
	 public static final CrcParams CRC8_IEEE 			= new CrcParams(8 , 0x07, 0x00, false, false, 0x0);
	 public static final CrcParams CRC8_SAE_J1850 		= new CrcParams(8 , 0x1D, 0xFF, false, false, 0xFF);
	 public static final CrcParams CRC8_SAE_J1850_ZERO 	= new CrcParams(8 , 0x1D, 0x00, false, false, 0x00);
	 public static final CrcParams CRC8_8H2F 			= new CrcParams(8 , 0x2F, 0xFF, false, false, 0xFF);
	 public static final CrcParams CRC8_CDMA2000 		= new CrcParams(8 , 0x9B, 0xFF, false, false, 0x00);
	 public static final CrcParams CRC8_DARC 			= new CrcParams(8 , 0x39, 0x00, true , true , 0x00);
	 public static final CrcParams CRC8_DVB_S2 			= new CrcParams(8 , 0xD5, 0x00, false, false, 0x00);
	 public static final CrcParams CRC8_EBU 			= new CrcParams(8 , 0x1D, 0xFF, true , true , 0x00);
	 public static final CrcParams CRC8_ICODE 			= new CrcParams(8 , 0x1D, 0xFD, false, false, 0x00);
	 public static final CrcParams CRC8_ITU 			= new CrcParams(8 , 0x07, 0x00, false, false, 0x55);
	 public static final CrcParams CRC8_MAXIM 			= new CrcParams(8 , 0x31, 0x00, true , true , 0x00);
	 public static final CrcParams CRC8_ROHC 			= new CrcParams(8 , 0x07, 0xFF, true , true , 0x00);
	 public static final CrcParams CRC8_WCDMA 			= new CrcParams(8 , 0x9B, 0x00, true , true , 0x00);
	 
	 public static final CrcParams CRC16_CCITT_ZERO		= new CrcParams(16, 0x1021, 0x0000, false, false, 0x0000);
	 public static final CrcParams CRC16_ARC		 	= new CrcParams(16, 0x8005, 0x0000, true , true , 0x0000);
	 public static final CrcParams CRC16_AUG_CCITT		= new CrcParams(16, 0x1021, 0x1D0F, false, false, 0x0000);
	 public static final CrcParams CRC16_BUYPASS 		= new CrcParams(16, 0x8005, 0x0000, false, false, 0x0000);
	 public static final CrcParams CRC16_CCITT_FALSE	= new CrcParams(16, 0x1021, 0xFFFF, false, false, 0x0000);
	 public static final CrcParams CRC16_CDMA2000		= new CrcParams(16, 0xC867, 0xFFFF, false, false, 0x0000);
	 public static final CrcParams CRC16_DDS_110 		= new CrcParams(16, 0x8005, 0x800D, false, false, 0x0000);
	 public static final CrcParams CRC16_DECT_R			= new CrcParams(16, 0x0589, 0x0000, false, false, 0x0001);
	 public static final CrcParams CRC16_DECT_X 		= new CrcParams(16, 0x0589, 0x0000, false, false, 0x0000);
	 public static final CrcParams CRC16_DNP 			= new CrcParams(16, 0x3D65, 0x0000, true , true , 0xFFFF);
	 public static final CrcParams CRC16_EN_13757 		= new CrcParams(16, 0x3D65, 0x0000, false, false, 0xFFFF);
	 public static final CrcParams CRC16_GENIBUS 		= new CrcParams(16, 0x1021, 0xFFFF, false, false, 0xFFFF);
	 public static final CrcParams CRC16_MAXIM 			= new CrcParams(16, 0x8005, 0x0000, true , true , 0xFFFF);
	 public static final CrcParams CRC16_MCRF4XX 		= new CrcParams(16, 0x1021, 0xFFFF, true , true , 0x0000);
	 public static final CrcParams CRC16_RIELLO 		= new CrcParams(16, 0x1021, 0xB2AA, true , true , 0x0000);
	 public static final CrcParams CRC16_T10_DIF		= new CrcParams(16, 0x8BB7, 0x0000, false, false, 0x0000);
	 public static final CrcParams CRC16_TELEDISK		= new CrcParams(16, 0xA097, 0x0000, false, false, 0x0000);
	 public static final CrcParams CRC16_TMS37157 		= new CrcParams(16, 0x1021, 0x89EC, true , true , 0x0000);
	 public static final CrcParams CRC16_USB 			= new CrcParams(16, 0x8005, 0xFFFF, true , true , 0xFFFF);
	 public static final CrcParams CRC16_A 				= new CrcParams(16, 0x1021, 0xC6C6, true , true , 0x0000);
	 public static final CrcParams CRC16_KERMIT			= new CrcParams(16, 0x1021, 0x0000, true , true , 0x0000);
	 public static final CrcParams CRC16_MODBUS 		= new CrcParams(16, 0x8005, 0xFFFF, true , true , 0x0000);
	 public static final CrcParams CRC16_X_25 			= new CrcParams(16, 0x1021, 0xFFFF, true , true , 0xFFFF);
	 public static final CrcParams CRC16_XMODEM 		= new CrcParams(16, 0x1021, 0x0000, false, false, 0x0000);
	 
	 public static final CrcParams CRC32_CRC32			= new CrcParams(32, 0x04C11DB7, 0xFFFFFFFF, true , true , 0xFFFFFFFF);
	 public static final CrcParams CRC32_BZIP2		 	= new CrcParams(32, 0x04C11DB7, 0xFFFFFFFF, false, false, 0xFFFFFFFF);
	 public static final CrcParams CRC32_C				= new CrcParams(32, 0x1EDC6F41, 0xFFFFFFFF, true , true , 0xFFFFFFFF);
	 public static final CrcParams CRC32_D 				= new CrcParams(32, 0xA833982B, 0xFFFFFFFF, true , true , 0xFFFFFFFF);
	 public static final CrcParams CRC32_MPEG2			= new CrcParams(32, 0x04C11DB7, 0xFFFFFFFF, false, false, 0x00000000);
	 public static final CrcParams CRC32_POSIX			= new CrcParams(32, 0x04C11DB7, 0x00000000, false, false, 0xFFFFFFFF);
	 public static final CrcParams CRC32_Q 				= new CrcParams(32, 0x814141AB, 0x00000000, false, false, 0x00000000);
	 public static final CrcParams CRC32_JAMCRC			= new CrcParams(32, 0x04C11DB7, 0xFFFFFFFF, true , true , 0x00000000);
	 public static final CrcParams CRC32_XFER 			= new CrcParams(32, 0x000000AF, 0x00000000, false, false, 0x00000000);
	
	 public static final CrcParams CRC64_ECMA_182 		= new CrcParams(64, 0x42F0E1EBA9EA3693L, 0x0000000000000000L, false, false, 0x0000000000000000L);
	 public static final CrcParams CRC64_GO_ISO			= new CrcParams(64, 0x000000000000001BL, 0xFFFFFFFFFFFFFFFFL, true , true , 0xFFFFFFFFFFFFFFFFL);
	 public static final CrcParams CRC64_WE	 			= new CrcParams(64, 0x42F0E1EBA9EA3693L, 0xFFFFFFFFFFFFFFFFL, false, false, 0xFFFFFFFFFFFFFFFFL);
	 public static final CrcParams CRC64_XZ	 			= new CrcParams(64, 0x42F0E1EBA9EA3693L, 0xFFFFFFFFFFFFFFFFL, true , true , 0xFFFFFFFFFFFFFFFFL);
	 
	 /* TO DO */
	 String [] Crc8PredefinedParamsStr = {"IEEE", "SAE_J1850_ZERO"};
	 String [] Crc16PredefinedParamsStr = {"IEEE", "SAE_J1850_ZERO"};
	 String [] Crc32PredefinedParamsStr = {"IEEE", "SAE_J1850_ZERO"};
	 String [] Crc64PredefinedParamsStr = {"IEEE", "SAE_J1850_ZERO"};
	 /* TO DO */
	 
	 private int 		width;  
	 private long 		polynomial;
	 private boolean 	reflectIn; 
	 private boolean 	reflectOut; 
	 private long 		init;
	 private long 		finalXor;
	 
	public CrcParams(int width, long polynomial, long init, boolean reflectIn, boolean reflectOut, long finalXor)
	{
	     this.width = width;
	     this.polynomial = polynomial;
	     this.reflectIn = reflectIn;
	     this.reflectOut = reflectOut;
	     this.init = init;
	     this.finalXor = finalXor;
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
	 

	     
//	private static final int CRC_PREDEFINED_PARAMS_CRC8 = 0;
//	private static final int CRC_PREDEFINED_PARAMS_CRC16 = 1;
//	private static final int CRC_PREDEFINED_PARAMS_CRC32 = 2;
//	private static final int CRC_PREDEFINED_PARAMS_CRC64 = 3;
}
