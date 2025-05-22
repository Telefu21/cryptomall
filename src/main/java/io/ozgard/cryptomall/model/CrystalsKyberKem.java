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
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.KeyGenerator;

import org.bouncycastle.jcajce.SecretKeyWithEncapsulation;
import org.bouncycastle.jcajce.spec.KEMExtractSpec;
import org.bouncycastle.jcajce.spec.KEMGenerateSpec;
import org.bouncycastle.pqc.jcajce.spec.KyberParameterSpec;
import org.springframework.stereotype.Component;

@Component
public class CrystalsKyberKem
{
	private byte[] privateKeyBytes;
	private byte[] publicKeyBytes;
	
	CrystalsKyberKem()
	{	
        
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

	public void generatePublicPrivateKeys(KyberParameterSpec kyberParameterSpec)
    {
        KeyPair keyPair = generateKeyPair(kyberParameterSpec);
        
        setPrivateKeyBytes(keyPair.getPrivate().getEncoded());
        setPublicKeyBytes(keyPair.getPublic().getEncoded());
    }
    
    private KeyPair generateKeyPair(KyberParameterSpec kyberParameterSpec) 
    {
        try 
        {
            SecureRandom sr = new SecureRandom();
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("KYBER", "BCPQC");
            kpg.initialize(kyberParameterSpec, sr);
            
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
            keyFactory = KeyFactory.getInstance("KYBER", "BCPQC");
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
        	
            KeyFactory keyFactory = KeyFactory.getInstance("KYBER", "BCPQC");
            
            return keyFactory.generatePublic(x509EncodedKeySpec);
        } 
        catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeySpecException e) 
        {
            e.printStackTrace();
            return null;
        }
    }
    
    public SecretKeyWithEncapsulation pqcGenerateChrystalsKyberEncryptionKey(PublicKey publicKey) 
    {
    	KeyGenerator keyGen = null;
    	
        try 
        {
            keyGen = KeyGenerator.getInstance("KYBER", "BCPQC");
            keyGen.init(new KEMGenerateSpec((PublicKey) publicKey, "AES"), new SecureRandom());
            SecretKeyWithEncapsulation secEnc = (SecretKeyWithEncapsulation) keyGen.generateKey();
            return secEnc;
        } 
        catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException e) 
        {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] pqcGenerateChrystalsKyberDecryptionKey(PrivateKey privateKey, byte[] encapsulatedKey) 
    {
        try 
        {
        	KeyGenerator keyGen = null;
            keyGen = KeyGenerator.getInstance("KYBER", "BCPQC");
            keyGen.init(new KEMExtractSpec((PrivateKey) privateKey, encapsulatedKey, "AES"), new SecureRandom());
            SecretKeyWithEncapsulation secEnc = (SecretKeyWithEncapsulation) keyGen.generateKey();
            return secEnc.getEncoded();
        } 
        catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException e) 
        {
            e.printStackTrace();
            return null;
        }
    }
}
