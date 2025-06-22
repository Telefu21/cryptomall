package io.ozgard.cryptomall.model;

import java.security.SecureRandom;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.SecretWithEncapsulation;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.pqc.crypto.hqc.HQCKEMExtractor;
import org.bouncycastle.pqc.crypto.hqc.HQCKEMGenerator;
import org.bouncycastle.pqc.crypto.hqc.HQCKeyGenerationParameters;
import org.bouncycastle.pqc.crypto.hqc.HQCKeyPairGenerator;
import org.bouncycastle.pqc.crypto.hqc.HQCParameters;
import org.bouncycastle.pqc.crypto.hqc.HQCPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.hqc.HQCPublicKeyParameters;
import org.springframework.stereotype.Component;

@Component
public class PQCKeyEncalpsulationHQC 
{
	private byte[] 	privateKeyBytes;
	private byte[] 	publicKeyBytes;
	private byte[] 	encapsulation;
	
	PQCKeyEncalpsulationHQC()
	{	  
	}

	public byte[] getPrivateKeyBytes() 
    {
		return privateKeyBytes;
	}

	public byte[] getPublicKeyBytes() 
	{
		return publicKeyBytes;
	}

	public void generatePublicPrivateKeys(HQCParameters parameterSpec)
    {
		AsymmetricCipherKeyPair keyPair = generateKeyPair(parameterSpec);
        
        privateKeyBytes = ((HQCPrivateKeyParameters) keyPair.getPrivate()).getEncoded();
        publicKeyBytes = ((HQCPublicKeyParameters) keyPair.getPublic()).getEncoded();
    }
    
    private AsymmetricCipherKeyPair generateKeyPair(HQCParameters parameterSpec) 
    {
        HQCKeyPairGenerator hqcKeyGen = new HQCKeyPairGenerator();
		HQCKeyGenerationParameters genParam = new HQCKeyGenerationParameters(new SecureRandom(), parameterSpec);
		hqcKeyGen.init(genParam);
		return hqcKeyGen.generateKeyPair();  
    }

    public HQCPrivateKeyParameters getPrivateKeyFromEncoded(byte[] encodedKey, HQCParameters hqcParameter) 
    {
    	return (new HQCPrivateKeyParameters(hqcParameter, encodedKey));
    }

    public HQCPublicKeyParameters getPublicKeyFromEncoded(byte[] encodedKey, HQCParameters hqcParameter)
    {   
    	return (new HQCPublicKeyParameters(hqcParameter, encodedKey));
    }
    
    public byte [] pqcGenerateKEMEncryptionKey(AsymmetricKeyParameter publicKey) 
    {
    	HQCKEMGenerator hqcKemGenerator = new HQCKEMGenerator(new SecureRandom());
        SecretWithEncapsulation secretKey = hqcKemGenerator.generateEncapsulated(publicKey);
        encapsulation = secretKey.getEncapsulation();
        return secretKey.getSecret();
    }

    public byte [] pqcGenerateKEMDecryptionKey(HQCPrivateKeyParameters privateKey, byte[] encapsulatedKey) 
    {
    	 HQCKEMExtractor hqcKemExtractor = new HQCKEMExtractor(privateKey);
    	 
         return hqcKemExtractor.extractSecret(encapsulatedKey);
    }

	public byte[] getEncapsulation() 
	{
		return encapsulation;
	}
}
