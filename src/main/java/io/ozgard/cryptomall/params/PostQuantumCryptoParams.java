package io.ozgard.cryptomall.params;

import org.springframework.stereotype.Component;

@Component
public class PostQuantumCryptoParams 
{
	public static final String PQC_DILITHIUM2 = "Dilithium2";
	public static final String PQC_DILITHIUM3 = "Dilithium3";
	public static final String PQC_DILITHIUM5 = "Dilithium5";
	
	public static final String PQC_KYBER512 = "Kyber512";
	public static final String PQC_KYBER768 = "Kyber768";
	public static final String PQC_KYBER1024 = "Kyber1024";
	
	public static final String PQC_FALCON512 = "Falcon512";
	public static final String PQC_FALCON1024 = "Falcon1024";
	
	public static final String PQC_BIKE128 = "Bike128";
	public static final String PQC_BIKE192 = "Bike192";
	public static final String PQC_BIKE256 = "Bike256";
	
	public static final String PQC_HQC128 = "Hqc128";
	public static final String PQC_HQC192 = "Hqc192";
	public static final String PQC_HQC256 = "Hqc256";
	
	public static final String PQC_MCELIECE348864 = "Mceliece348864";
	public static final String PQC_MCELIECE348864f = "Mceliece348864f";
	public static final String PQC_MCELIECE460896 = "Mceliece460896";
	public static final String PQC_MCELIECE6688128 = "Mceliece6688128";
	public static final String PQC_MCELIECE6960119 = "Mceliece6960119";
	public static final String PQC_MCELIECE8192128 = "Mceliece8192128";
	/*
	SPHINCSPlusParameterSpec.sha2_128f,
    SPHINCSPlusParameterSpec.sha2_128f_simple,
    SPHINCSPlusParameterSpec.sha2_128s,
    SPHINCSPlusParameterSpec.sha2_128s_simple,
    SPHINCSPlusParameterSpec.sha2_192f,
    SPHINCSPlusParameterSpec.sha2_192f_simple,
    SPHINCSPlusParameterSpec.sha2_192s,
    SPHINCSPlusParameterSpec.sha2_192s_simple,
    SPHINCSPlusParameterSpec.sha2_256f,
    SPHINCSPlusParameterSpec.sha2_256f_simple,
    SPHINCSPlusParameterSpec.sha2_256s,
    SPHINCSPlusParameterSpec.sha2_256s_simple,
    SPHINCSPlusParameterSpec.shake_128f,
    SPHINCSPlusParameterSpec.shake_128f_simple,
    SPHINCSPlusParameterSpec.shake_128s,
    SPHINCSPlusParameterSpec.shake_128s_simple,
    SPHINCSPlusParameterSpec.shake_192f,
    SPHINCSPlusParameterSpec.shake_192f_simple,
    SPHINCSPlusParameterSpec.shake_192s,
    SPHINCSPlusParameterSpec.shake_192s_simple,
    SPHINCSPlusParameterSpec.shake_256f,
    SPHINCSPlusParameterSpec.shake_256f_simple,
    SPHINCSPlusParameterSpec.shake_256s,
    SPHINCSPlusParameterSpec.shake_256s_simple
	*/
	byte [] inputFileBytes;
	byte [] publicKeyFileBytes;
	byte [] signatureFileBytes;
	byte [] textAreaBytes;
	
	String parameterSet;
	
	public byte[] getTextAreaBytes() 
	{
		return textAreaBytes;
	}

	public void setTextAreaBytes(byte[] textAreaBytes) 
	{
		this.textAreaBytes = textAreaBytes;
	}

	public byte[] getInputFileBytes() 
	{
		return inputFileBytes;
	}
	
	public void setInputFileBytes(byte[] inputFileBytes) 
	{
		this.inputFileBytes = inputFileBytes;
	}
	
	public byte[] getPublicKeyFileBytes() 
	{
		return publicKeyFileBytes;
	}
	
	public void setPublicKeyFileBytes(byte[] publicKeyFileBytes) 
	{
		this.publicKeyFileBytes = publicKeyFileBytes;
	}
	
	public byte[] getSignatureFileBytes() 
	{
		return signatureFileBytes;
	}
	
	public void setSignatureFileBytes(byte[] signatureFileBytes) 
	{
		this.signatureFileBytes = signatureFileBytes;
	}
}
