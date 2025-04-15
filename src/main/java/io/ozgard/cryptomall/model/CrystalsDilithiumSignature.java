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
	CrystalsDilithiumSignature()
	{	
        String dataToSignString = "The quick brown fox jumps over the lazy dog";
        byte[] dataToSign = dataToSignString.getBytes(StandardCharsets.UTF_8);

        // as there are 3 parameter sets available the program runs all of them
        DilithiumParameterSpec[] dilithiumParameterSpecs = {
                DilithiumParameterSpec.dilithium2,
                DilithiumParameterSpec.dilithium3,
                DilithiumParameterSpec.dilithium5
        };

        // statistics
        int nrOfSpecs = dilithiumParameterSpecs.length;
        String[] parameterSpecName = new String[nrOfSpecs];
        int[] privateKeyLength = new int[nrOfSpecs];
        int[] publicKeyLength = new int[nrOfSpecs];
        int[] signatureLength = new int[nrOfSpecs];
        boolean[] signaturesVerified = new boolean[nrOfSpecs];

        String out = "\n\n****************************************\n";
        for (int i = 0; i < nrOfSpecs; i++) {
            // generation of the Chrystals Dilithium key pair
            DilithiumParameterSpec dilithiumParameterSpec = dilithiumParameterSpecs[i];
            String dilithiumParameterSpecName = dilithiumParameterSpec.getName();
            parameterSpecName[i] = dilithiumParameterSpecName;
            out += "\n" + "Chrystals Dilithium signature with parameterset " + dilithiumParameterSpecName;
            KeyPair keyPair = generateChrystalsDilithiumKeyPair(dilithiumParameterSpec);

            // get private and public key
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            // storing the key as byte array
            byte[] privateKeyByte = privateKey.getEncoded();
            byte[] publicKeyByte = publicKey.getEncoded();
            out += "\n" + "\ngenerated private key length: " + privateKeyByte.length;
            out += "\n" + "generated public key length:  " + publicKeyByte.length;
            privateKeyLength[i] = privateKeyByte.length;
            publicKeyLength[i] = publicKeyByte.length;

            // generate the keys from a byte array
            PrivateKey privateKeyLoad = getChrystalsDilithiumPrivateKeyFromEncoded(privateKeyByte);
            PublicKey publicKeyLoad = null;
			try 
			{
				publicKeyLoad = getChrystalsDilithiumPublicKeyFromEncoded(publicKeyByte);
			} 
			catch (InvalidKeyException | SignatureException e) 
			{
				e.printStackTrace();
			}

            out += "\n" + "\n* * * sign the dataToSign with the private key * * *";
            byte[] signature = pqcChrystalsDilithiumSignature(privateKeyLoad, dataToSign);
            out += "\n" + "signature length: " + signature.length + " data: " + bytesToHex(signature);
            signatureLength[i] = signature.length;

            out += "\n" + "\n* * * verify the signature with the public key * * *";
            boolean signatureVerified = pqcChrystalsDilithiumVerification(publicKeyLoad, dataToSign, signature);
            out += "\n" + "the signature is verified: " + signatureVerified;
            signaturesVerified[i] = signatureVerified;
            out += "\n\n****************************************\n";
        }

        out += "\n" + "Test results";
        out += "\n" + "parameter spec name  priKL   pubKL    sigL  sigV" + "\n";
        for (int i = 0; i < nrOfSpecs; i++) {
            String out1 = String.format("%-20s%6d%8d%8d%6b%n", parameterSpecName[i], privateKeyLength[i], publicKeyLength[i], signatureLength[i], signaturesVerified[i]);
            out += out1;
        }
        out += "\n" + "Legend: priKL privateKey length, pubKL publicKey length, sigL signature length, sigV signature verified\n";
        out += "\n****************************************\n";
	}

    private KeyPair generateChrystalsDilithiumKeyPair(DilithiumParameterSpec dilithiumParameterSpec) 
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

    private PrivateKey getChrystalsDilithiumPrivateKeyFromEncoded(byte[] encodedKey) 
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

    private PublicKey getChrystalsDilithiumPublicKeyFromEncoded(byte[] encodedKey) throws InvalidKeyException, SignatureException 
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

    private byte[] pqcChrystalsDilithiumSignature(PrivateKey privateKey, byte[] dataToSign) 
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

    private boolean pqcChrystalsDilithiumVerification(PublicKey publicKey, byte[] dataToSign, byte[] signature) 
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

    private String bytesToHex(byte[] bytes) 
    {
        StringBuffer result = new StringBuffer();
        for (byte b : bytes) result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }
}
