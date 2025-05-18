package io.ozgard.cryptomall.model;

public interface ISignature 
{
	public byte [] getPrivateKeyBytes();
	public byte [] getPublicKeyBytes();
	public byte [] generateSignature(byte[] dataToSign, byte[] privateKeyBytes);
	public boolean verifySignature(byte[] data, byte[] signature, byte[] publicKeyBytes);
}
