package io.ozgard.cryptomall.model;

import io.ozgard.cryptomall.params.CrcParams;

public class CRC
{
	 private CrcParams 	crcParams;
	 private long   	initValue;
	 private long[] 	crctable;
	 private long   	mask;

	 private static long reflect(long in, int count)
	 {
	     long ret = in;
	     
	     for (int idx = 0; idx < count; idx++)
	     {
	         long srcbit = 1L << idx;
	         long dstbit = 1L << (count - idx - 1);
	         if ((in & srcbit) != 0)
	         {
	             ret |= dstbit;
	         }
	         else
	         {
	             ret = ret & (~dstbit);
	         }
	     }
	     
	     return ret;
	 }
	
	 public static long calculateCRC(CrcParams crcParams, byte[] data)
	 {
	     return calculateCRC(crcParams, data, 0, data.length);
	 }
	
	 public static long calculateCRC(CrcParams crcParams, byte[] data, int offset, int length)
	 {
	     long curValue = crcParams.getInit();
	     long topBit = 1L << (crcParams.getWidth() - 1);
	     long mask = (topBit << 1) - 1;
	     int end = offset + length;
	
	     for (int i = offset; i < end; i ++)
	     {
	         long curByte = ((long)(data[i])) & 0x00FFL;
	         
	         if (crcParams.isReflectIn())
	         {
	             curByte = reflect(curByte, 8);
	         }
	
	         for (int j = 0x80; j != 0; j >>= 1)
	         {
	             long bit = curValue & topBit;
	             curValue <<= 1;
	
	             if ((curByte & j) != 0)
	             {
	                 bit ^= topBit;
	             }
	
	             if (bit != 0)
	             {
	                 curValue ^= crcParams.getPolynomial();
	             }
	         }
	
	     }
	
	     if (crcParams.isReflectOut())
	     {
	         curValue = reflect(curValue, crcParams.getWidth());
	     }
	
	     curValue = curValue ^ crcParams.getFinalXor();
	
	     return curValue & mask;
	 }
	
	 public long init()
	 {
	     return initValue;
	 }
	
	 public long update (long curValue, byte[] chunk, int offset, int length)
	 {
	     if (crcParams.isReflectIn())
	     {
	         for (int i=0; i < length; i++)
	         {
	             byte v = chunk[offset+i];
	             curValue = crctable[(((byte)curValue) ^ v)&0x00FF]^(curValue >>> 8);
	         }
	     }
	     else if (crcParams.getWidth()<8)
	     {
	         for (int i=0; i < length; i++)
	         {
	             byte v = chunk[offset+i];
	             curValue = crctable[((((byte)(curValue << (8-crcParams.getWidth()))) ^ v)&0xFF)]^(curValue << 8);
	         }
	     }
	     else
	     {
	         for (int i=0; i < length; i++)
	         {
	             byte v = chunk[offset+i];
	             curValue = crctable[((((byte)(curValue >>> (crcParams.getWidth() - 8))) ^ v)&0xFF)]^(curValue << 8);
	         }
	     }
	
	     return curValue;
	 }
	
	 public long update (long curValue, byte[] chunk)
	 {
	     return update(curValue, chunk, 0, chunk.length);
	 }
	
	 public long finalCRC(long curValue)
	 {
	     long ret=curValue;
	     
	     if (crcParams.isReflectOut() != crcParams.isReflectIn())
	     {
	         ret = reflect(ret, crcParams.getWidth());
	     }
	     return (ret ^ crcParams.getFinalXor()) & mask;
	 }
	
	 public long calculateCRC(byte[] data)
	 {
	     return calculateCRC(data, 0, data.length);
	 }
	
	 public long calculateCRC(byte[] data, int offset, int length)
	 {
	     long crc = init();
	     crc = update(crc, data, offset, length);
	     return finalCRC(crc);
	 }
	
	 public CRC(CrcParams crcParams)
	 {
	     this.crcParams = new CrcParams(crcParams);
	
	     initValue = (crcParams.isReflectIn()) ? reflect(crcParams.getInit(), crcParams.getWidth()) : crcParams.getInit();
	     this.mask = ((crcParams.getWidth() >= 64) ? 0 : (1L << crcParams.getWidth())) - 1;
	     this.crctable = new long[256];
	
	     byte[] tmp = new byte[1];
	
	     CrcParams tableParams = new CrcParams(crcParams);
	
	     tableParams.setInit(0);
	     tableParams.setReflectOut(tableParams.isReflectIn());
	     tableParams.setFinalXor(0);
	     
	     for (int i=0; i< 256; i++)
	     {
	         tmp[0] = (byte)i;
	         crctable[i] = CRC.calculateCRC(tableParams, tmp);
	     }
	 }
	 
	 public long[] getCrcTable()
	 {
		 return crctable;
	 }
	
	 public byte finalCRC8 (long curValue)
	 {
	     if (crcParams.getWidth() != 8)
	         throw new RuntimeException("CRC width mismatch");
	     return (byte) finalCRC(curValue);
	 }
	
	 public short finalCRC16 (long curValue)
	 {
	     if (crcParams.getWidth() != 16)
	         throw new RuntimeException("CRC width mismatch");
	     return (short) finalCRC(curValue);
	 }
	
	 public int finalCRC32(long curValue)
	 {
	     if (crcParams.getWidth() != 32)
	         throw new RuntimeException("CRC width mismatch");
	     return (int) finalCRC(curValue);
	 }
}
