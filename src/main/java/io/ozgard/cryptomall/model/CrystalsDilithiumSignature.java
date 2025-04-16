package io.ozgard.cryptomall.model;

import java.nio.charset.StandardCharsets;
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
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.bouncycastle.pqc.jcajce.provider.BouncyCastlePQCProvider;
import org.bouncycastle.pqc.jcajce.spec.DilithiumParameterSpec;
import org.springframework.stereotype.Component;

@Component
public class CrystalsDilithiumSignature 
{
	private byte[] privateKeyByte;
	private byte[] publicKeyByte;
	
	CrystalsDilithiumSignature()
	{	
        
	}

    public byte[] getPrivateKeyByte() 
    {
		return privateKeyByte;
	}

	public void setPrivateKeyByte(byte[] privateKeyByte) 
	{
		this.privateKeyByte = privateKeyByte;
	}

	public byte[] getPublicKeyByte() 
	{
		return publicKeyByte;
	}

	public void setPublicKeyByte(byte[] publicKeyByte) 
	{
		this.publicKeyByte = publicKeyByte;
	}

	public void generatePublicPrivateKeys(DilithiumParameterSpec dilithiumParameterSpec)
    {
        KeyPair keyPair = generateKeyPair(dilithiumParameterSpec);
        
        setPrivateKeyByte(keyPair.getPrivate().getEncoded());
        setPublicKeyByte(keyPair.getPublic().getEncoded());
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

    private KeyPair generateKeyPair(DilithiumParameterSpec dilithiumParameterSpec) 
    {
        try 
        {
            SecureRandom sr = new SecureRandom();
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("Dilithium");
            kpg.initialize(dilithiumParameterSpec, sr);
            
            return kpg.generateKeyPair();
        } 
        catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException e) 
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
            keyFactory = KeyFactory.getInstance("Dilithium", "BCPQC");
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
        	
            KeyFactory keyFactory = KeyFactory.getInstance("Dilithium", "BCPQC");
            
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
            Signature sig = Signature.getInstance("Dilithium");
            sig.initSign((PrivateKey) privateKey, new SecureRandom());
            sig.update(dataToSign, 0, dataToSign.length);
            byte[] signature = sig.sign();
            return signature;
        } 
        catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) 
        {
            e.printStackTrace();
            return null;
        }
    }

    private boolean pqcVerification(PublicKey publicKey, byte[] dataToSign, byte[] signature) 
    {
        try 
        {
            Signature sig = Signature.getInstance("Dilithium");
            sig.initVerify((PublicKey) publicKey);
            sig.update(dataToSign, 0, dataToSign.length);
            boolean result = sig.verify(signature);
            return result;
        } 
        catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) 
        {
            e.printStackTrace();
            return false;
        }
    }
}
