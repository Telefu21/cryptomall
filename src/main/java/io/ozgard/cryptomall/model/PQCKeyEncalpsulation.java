package io.ozgard.cryptomall.model;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.KeyGenerator;

import org.bouncycastle.jcajce.SecretKeyWithEncapsulation;
import org.bouncycastle.jcajce.spec.KEMExtractSpec;
import org.bouncycastle.jcajce.spec.KEMGenerateSpec;
import org.springframework.stereotype.Component;

@Component
public class PQCKeyEncalpsulation
{
	private byte[] 	privateKeyBytes;
	private byte[] 	publicKeyBytes;
	private byte[] 	encapsulation;
	String 			algorithm;
	
	PQCKeyEncalpsulation()
	{	
        
	}

    public void setAlgorithm(String algorithm) 
    {
		this.algorithm = algorithm;
	}

	public byte[] getPrivateKeyBytes() 
    {
		return privateKeyBytes;
	}

	public byte[] getPublicKeyBytes() 
	{
		return publicKeyBytes;
	}

	public void generatePublicPrivateKeys(AlgorithmParameterSpec parameterSpec)
    {
        KeyPair keyPair = generateKeyPair(parameterSpec);
        
        privateKeyBytes = keyPair.getPrivate().getEncoded();
        publicKeyBytes = keyPair.getPublic().getEncoded();
    }
    
    private KeyPair generateKeyPair(AlgorithmParameterSpec parameterSpec) 
    {
        try 
        {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(algorithm, "BCPQC");
            kpg.initialize(parameterSpec, new SecureRandom());
            return kpg.generateKeyPair();
        } 
        catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException | NoSuchProviderException e) 
        {
            e.printStackTrace();
            return null;
        }  
    }

    public PrivateKey getPrivateKeyFromEncoded(byte[] encodedKey) 
    {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(encodedKey);
        KeyFactory keyFactory = null;
        
        try 
        {
            keyFactory = KeyFactory.getInstance(algorithm, "BCPQC");
            return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        } 
        catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeySpecException e) 
        {
            e.printStackTrace();
            return null;
        }
    }

    public PublicKey getPublicKeyFromEncoded(byte[] encodedKey)
    {   
        try 
        {
        	X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(encodedKey);
        	
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm, "BCPQC");
            
            return keyFactory.generatePublic(x509EncodedKeySpec);
        } 
        catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeySpecException e) 
        {
            e.printStackTrace();
            return null;
        }
    }
    
    public byte [] pqcGenerateKEMEncryptionKey(PublicKey publicKey) 
    {
    	KeyGenerator keyGen = null;
    	
        try 
        {
            keyGen = KeyGenerator.getInstance(algorithm, "BCPQC");
            keyGen.init(new KEMGenerateSpec((PublicKey) publicKey, "Secret"));
            SecretKeyWithEncapsulation secretKey = (SecretKeyWithEncapsulation) keyGen.generateKey();
            encapsulation = secretKey.getEncapsulation();
            return secretKey.getEncoded();
        } 
        catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException e) 
        {
            e.printStackTrace();
            return null;
        }
    }

    public byte [] pqcGenerateKEMDecryptionKey(PrivateKey privateKey, byte[] encapsulatedKey) 
    {
    	KeyGenerator keyGen = null;
    	
        try 
        {
            keyGen = KeyGenerator.getInstance(algorithm, "BCPQC");
            keyGen.init(new KEMExtractSpec((PrivateKey) privateKey, encapsulatedKey, "Secret"), new SecureRandom());
            return ((SecretKeyWithEncapsulation) keyGen.generateKey()).getEncoded();
        } 
        catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException e) 
        {
            e.printStackTrace();
            return null;
        }
    }

	public byte[] getEncapsulation() 
	{
		return encapsulation;
	}
}
