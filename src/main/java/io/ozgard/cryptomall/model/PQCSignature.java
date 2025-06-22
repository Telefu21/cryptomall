package io.ozgard.cryptomall.model;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.springframework.stereotype.Component;

@Component
public class PQCSignature 
{
	private byte[] 	privateKeyBytes;
	private byte[] 	publicKeyBytes;
	String			algorithm;
	
	PQCSignature()
	{	
        
	}

    public String getAlgorithm() 
    {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) 
	{
		this.algorithm = algorithm;
	}

	public byte[] getPrivateKeyBytes() 
    {
		return privateKeyBytes;
	}

	public void setPrivateKeyBytes(byte[] privateKeyBytes) 
	{
		this.privateKeyBytes = privateKeyBytes;
	}

	public byte[] getPublicKeyBytes() 
	{
		return publicKeyBytes;
	}

	public void setPublicKeyBytes(byte[] publicKeyBytes) 
	{
		this.publicKeyBytes = publicKeyBytes;
	}

	public void generatePublicPrivateKeys(AlgorithmParameterSpec parameterSpec)
    {
        KeyPair keyPair = generateKeyPair(parameterSpec);
        
        setPrivateKeyBytes(keyPair.getPrivate().getEncoded());
        setPublicKeyBytes(keyPair.getPublic().getEncoded());
    }
    
    public byte[] generateSignature(byte[] dataToSign, byte[] privateKeyBytes)
    {

        PrivateKey privateKeyLoad = getPrivateKeyFromEncoded(privateKeyBytes);

        return pqcSignature(privateKeyLoad, dataToSign);
    }

    public boolean verifySignature(byte[] data, byte[] signature, byte[] publicKeyBytes)
    {
        PublicKey publicKeyLoad = null;

        try 
        {
            publicKeyLoad = getPublicKeyFromEncoded(publicKeyBytes);
        } 
        catch (InvalidKeyException | SignatureException e) 
        {
            e.printStackTrace();
        }

        return pqcVerification(publicKeyLoad, data, signature);
    }

    private KeyPair generateKeyPair(AlgorithmParameterSpec parameterSpec) 
    {
        try 
        {
            SecureRandom sr = new SecureRandom();
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(algorithm, "BCPQC");
            kpg.initialize(parameterSpec, sr);
            
            return kpg.generateKeyPair();
        } 
        catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException | NoSuchProviderException e) 
        {
            e.printStackTrace();
            return null;
        }  
    }

    private PrivateKey getPrivateKeyFromEncoded(byte[] encodedKey) 
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

    private PublicKey getPublicKeyFromEncoded(byte[] encodedKey) throws InvalidKeyException, SignatureException 
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

    private byte[] pqcSignature(PrivateKey privateKey, byte[] dataToSign) 
    {
        try 
        {
            Signature sig = Signature.getInstance(algorithm, "BCPQC");
            sig.initSign((PrivateKey) privateKey, new SecureRandom());
            sig.update(dataToSign, 0, dataToSign.length);
            byte[] signature = sig.sign();
            return signature;
        } 
        catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException | NoSuchProviderException e) 
        {
            e.printStackTrace();
            return null;
        }
    }

    private boolean pqcVerification(PublicKey publicKey, byte[] dataToSign, byte[] signature) 
    {
        try 
        {
            Signature sig = Signature.getInstance(algorithm, "BCPQC");
            sig.initVerify((PublicKey) publicKey);
            sig.update(dataToSign, 0, dataToSign.length);
            boolean result = sig.verify(signature);
            return result;
        } 
        catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException | NoSuchProviderException e) 
        {
            e.printStackTrace();
            return false;
        }
    }
}
