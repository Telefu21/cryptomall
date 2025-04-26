package io.ozgard.cryptomall.params;

import java.util.HashMap;
import java.util.Map;

import org.bouncycastle.pqc.crypto.hqc.HQCParameters;
import org.bouncycastle.pqc.jcajce.spec.BIKEParameterSpec;
import org.bouncycastle.pqc.jcajce.spec.CMCEParameterSpec;
import org.bouncycastle.pqc.jcajce.spec.DilithiumParameterSpec;
import org.bouncycastle.pqc.jcajce.spec.FalconParameterSpec;
import org.bouncycastle.pqc.jcajce.spec.KyberParameterSpec;
import org.bouncycastle.pqc.jcajce.spec.SPHINCSPlusParameterSpec;
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

	public static final String PQC_SPHINCSSHA2_128F = "sha2_128f";
	public static final String PQC_SPHINCSSHA2_128F_SIMPLE = "sha2_128f_simple";
	public static final String PQC_SPHINCSSHA2_128S = "sha2_128s";
	public static final String PQC_SPHINCSSHA2_128S_SIMPLE = "sha2_128s_simple";
	public static final String PQC_SPHINCSSHA2_192F = "sha2_192f";
	public static final String PQC_SPHINCSSHA2_192F_SIMPLE = "sha2_192f_simple";
	public static final String PQC_SPHINCSSHA2_192S = "sha2_192s";
	public static final String PQC_SPHINCSSHA2_192S_SIMPLE = "sha2_192s_simple";
	public static final String PQC_SPHINCSSHA2_256F = "sha2_256f";
	public static final String PQC_SPHINCSSHA2_256F_SIMPLE = "sha2_256f_simple";
	public static final String PQC_SPHINCSSHA2_256S = "sha2_256s";
	public static final String PQC_SPHINCSSHA2_256S_SIMPLE = "sha2_256s_simple";
	public static final String PQC_SPHINCSSHAKE_128F = "shake_128f";
	public static final String PQC_SPHINCSSHAKE_128F_SIMPLE = "shake_128f_simple";
	public static final String PQC_SPHINCSSHAKE_128S = "shake_128s";
	public static final String PQC_SPHINCSSHAKE_128S_SIMPLE = "shake_128s_simple";
	public static final String PQC_SPHINCSSHAKE_192F = "shake_192f";
	public static final String PQC_SPHINCSSHAKE_192F_SIMPLE = "shake_192f_simple";
	public static final String PQC_SPHINCSSHAKE_192S = "shake_192s";
	public static final String PQC_SPHINCSSHAKE_192S_SIMPLE = "shake_192s_simple";
	public static final String PQC_SPHINCSSHAKE_256F = "shake_256f";
	public static final String PQC_SPHINCSSHAKE_256F_SIMPLE = "shake_256f_simple";
	public static final String PQC_SPHINCSSHAKE_256S = "shake_256s";
	public static final String PQC_SPHINCSSHAKE_256S_SIMPLE = "shake_256s_simple";

	byte [] inputFileBytes;
	byte [] publicKeyFileBytes;
	byte [] signatureFileBytes;
	byte [] textAreaBytes;
	
	String parameterSet;
	String workingDirectoryPath;
	
	Map<String, DilithiumParameterSpec> dilithiumStrToParams;
	Map<String, KyberParameterSpec> kyberStrToParams;
	Map<String, FalconParameterSpec> falconStrToParams;
	Map<String, BIKEParameterSpec> bikeStrToParams;
	Map<String, HQCParameters> hqcStrToParams;
	Map<String, CMCEParameterSpec> mecelieceStrToParams;
	Map<String, SPHINCSPlusParameterSpec> sphincsStrToParams;
	
	public PostQuantumCryptoParams()
	{
		dilithiumStrToParams = new HashMap<>();
		kyberStrToParams = new HashMap<>();
		falconStrToParams = new HashMap<>();
		bikeStrToParams = new HashMap<>();
		hqcStrToParams = new HashMap<>();
		mecelieceStrToParams = new HashMap<>();
		sphincsStrToParams = new HashMap<>();
		
		dilithiumStrToParams.put("Dilithium2", DilithiumParameterSpec.dilithium2);
		dilithiumStrToParams.put("Dilithium3", DilithiumParameterSpec.dilithium3);
		dilithiumStrToParams.put("Dilithium5", DilithiumParameterSpec.dilithium5);
		
		kyberStrToParams.put("Kyber512", KyberParameterSpec.kyber512);
		kyberStrToParams.put("Kyber768", KyberParameterSpec.kyber768);
		kyberStrToParams.put("Kyber1024", KyberParameterSpec.kyber1024);
		
		falconStrToParams.put("Falcon512", FalconParameterSpec.falcon_512);
		falconStrToParams.put("Falcon1024", FalconParameterSpec.falcon_1024);
		
		bikeStrToParams.put("Bike128", BIKEParameterSpec.bike128);
		bikeStrToParams.put("Bike192", BIKEParameterSpec.bike192);
		bikeStrToParams.put("Bike256", BIKEParameterSpec.bike256);
		
		hqcStrToParams.put("Hqc128", HQCParameters.hqc128);
		hqcStrToParams.put("Hqc192", HQCParameters.hqc192);
		hqcStrToParams.put("Hqc256", HQCParameters.hqc256);

		mecelieceStrToParams.put("Mceliece348864", CMCEParameterSpec.mceliece348864);
		mecelieceStrToParams.put("Mceliece348864f", CMCEParameterSpec.mceliece348864f);
		mecelieceStrToParams.put("Mceliece460896", CMCEParameterSpec.mceliece460896);
		mecelieceStrToParams.put("Mceliece460896f", CMCEParameterSpec.mceliece460896f);
		mecelieceStrToParams.put("Mceliece6688128", CMCEParameterSpec.mceliece6688128);
		mecelieceStrToParams.put("Mceliece6688128f", CMCEParameterSpec.mceliece6688128f);
		mecelieceStrToParams.put("Mceliece6960119", CMCEParameterSpec.mceliece6960119);
		mecelieceStrToParams.put("Mceliece6960119f", CMCEParameterSpec.mceliece6960119f);
		mecelieceStrToParams.put("Mceliece8192128", CMCEParameterSpec.mceliece8192128);
		mecelieceStrToParams.put("Mceliece8192128f", CMCEParameterSpec.mceliece8192128f);
		
		sphincsStrToParams.put("haraka_128f", SPHINCSPlusParameterSpec.haraka_128f);
		sphincsStrToParams.put("haraka_128f_simple", SPHINCSPlusParameterSpec.haraka_128f_simple);
		sphincsStrToParams.put("haraka_128s", SPHINCSPlusParameterSpec.haraka_128s);
		sphincsStrToParams.put("haraka_128s_simple", SPHINCSPlusParameterSpec.haraka_128s_simple);
		sphincsStrToParams.put("haraka_192f", SPHINCSPlusParameterSpec.haraka_192f);
		sphincsStrToParams.put("haraka_192f_simple", SPHINCSPlusParameterSpec.haraka_192f_simple);
		sphincsStrToParams.put("haraka_192s", SPHINCSPlusParameterSpec.haraka_192s);
		sphincsStrToParams.put("haraka_192s_simple", SPHINCSPlusParameterSpec.haraka_192s_simple);
		sphincsStrToParams.put("haraka_256f", SPHINCSPlusParameterSpec.haraka_256f);
		sphincsStrToParams.put("haraka_256f_simple", SPHINCSPlusParameterSpec.haraka_256f_simple);
		sphincsStrToParams.put("haraka_256s", SPHINCSPlusParameterSpec.haraka_256s);
		sphincsStrToParams.put("haraka_256s_simple", SPHINCSPlusParameterSpec.haraka_256s_simple);
		sphincsStrToParams.put("sha2_128f", SPHINCSPlusParameterSpec.sha2_128f);
		sphincsStrToParams.put("sha2_128f_robust", SPHINCSPlusParameterSpec.sha2_128f_robust);
		sphincsStrToParams.put("sha2_128s", SPHINCSPlusParameterSpec.sha2_128s);
		sphincsStrToParams.put("sha2_128s_robust", SPHINCSPlusParameterSpec.sha2_128s_robust);
		sphincsStrToParams.put("sha2_192f", SPHINCSPlusParameterSpec.sha2_192f);
		sphincsStrToParams.put("sha2_192f_robust", SPHINCSPlusParameterSpec.sha2_192f_robust);
		sphincsStrToParams.put("sha2_192s", SPHINCSPlusParameterSpec.sha2_192s);
		sphincsStrToParams.put("sha2_192s_robust", SPHINCSPlusParameterSpec.sha2_192s_robust);
		sphincsStrToParams.put("sha2_256f", SPHINCSPlusParameterSpec.sha2_256f);
		sphincsStrToParams.put("sha2_256f_robust", SPHINCSPlusParameterSpec.sha2_256f_robust);
		sphincsStrToParams.put("sha2_256s", SPHINCSPlusParameterSpec.sha2_256s);
		sphincsStrToParams.put("sha2_256s_robust", SPHINCSPlusParameterSpec.sha2_256s_robust);
		sphincsStrToParams.put("shake_128f", SPHINCSPlusParameterSpec.shake_128f);
		sphincsStrToParams.put("shake_128f_robust", SPHINCSPlusParameterSpec.shake_128f_robust);
		sphincsStrToParams.put("shake_128s", SPHINCSPlusParameterSpec.shake_128s);
		sphincsStrToParams.put("shake_128s_robust", SPHINCSPlusParameterSpec.shake_128s_robust);
		sphincsStrToParams.put("shake_192f", SPHINCSPlusParameterSpec.shake_192f);
		sphincsStrToParams.put("shake_192f_robust", SPHINCSPlusParameterSpec.shake_192f_robust);
		sphincsStrToParams.put("shake_192s", SPHINCSPlusParameterSpec.shake_192s);
		sphincsStrToParams.put("shake_192s_robust", SPHINCSPlusParameterSpec.shake_192s_robust);
		sphincsStrToParams.put("shake_256f", SPHINCSPlusParameterSpec.shake_256f);
		sphincsStrToParams.put("shake_256f_robust", SPHINCSPlusParameterSpec.shake_256f_robust);
		sphincsStrToParams.put("shake_256s", SPHINCSPlusParameterSpec.shake_256s);
		sphincsStrToParams.put("shake_256s_robust", SPHINCSPlusParameterSpec.shake_256s_robust);
	}

	public String getWorkingDirectoryPath() 
	{
		return workingDirectoryPath;
	}

	public void setWorkingDirectoryPath(String workingDirectoryPath) 
	{
		this.workingDirectoryPath = workingDirectoryPath;
	}

	public String getParameterSet() 
	{
		return parameterSet;
	}

	public void setParameterSet(String parameterSet) 
	{
		this.parameterSet = parameterSet;
	}

	public Map<String, DilithiumParameterSpec> getDilithiumStrToParams() 
	{
		return dilithiumStrToParams;
	}

	public Map<String, KyberParameterSpec> getKyberStrToParams() 
	{
		return kyberStrToParams;
	}

	public Map<String, FalconParameterSpec> getFalconStrToParams() 
	{
		return falconStrToParams;
	}

	public Map<String, BIKEParameterSpec> getBikeStrToParams() 
	{
		return bikeStrToParams;
	}

	public Map<String, HQCParameters> getHqcStrToParams() 
	{
		return hqcStrToParams;
	}

	public Map<String, CMCEParameterSpec> getMecelieceStrToParams() 
	{
		return mecelieceStrToParams;
	}

	public Map<String, SPHINCSPlusParameterSpec> getSphincsStrToParams() 
	{
		return sphincsStrToParams;
	}

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
