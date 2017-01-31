/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptoTools;

import java.security.PublicKey;

import javax.crypto.Cipher;

import tools.Constants;

/**
 * Asymmetric encryption 
 * 
 * @author Athanasia Katsouraki
 * 
 */
public class AsymmetricEncryption {


	/**
	 * Encryption by separating the input into blocks.
	 * 
	 * @param inbytes the bytes to encrypt
	 * @param pubKey the public key
	 * @return the encrypted bytes
	 * @throws Exception 
	 */
	public static byte[] encryptBlockByBlock( byte[] inbytes, PublicKey pubKey ) {
		int encryptedBlockSize = Constants.RSA_KEY_LENGTH / 8;
		int numOfBlocks = (int) Math.ceil(((double)inbytes.length)/ Constants.RSA_PLAIN_BLOCK_SIZE );
		byte[] tmpInBytes;
		byte[] encryptedStoreMessage = new byte[ encryptedBlockSize*numOfBlocks] ;

		byte[] tmpEncBytes = null;
		int upperBound =0;

		for(int i=0; i<numOfBlocks; i++)
		{
			if( (i+1)*Constants.RSA_PLAIN_BLOCK_SIZE < inbytes.length )
			{
				upperBound = Constants.RSA_PLAIN_BLOCK_SIZE;
				tmpInBytes = new byte[ Constants.RSA_PLAIN_BLOCK_SIZE ];
			}
			else
			{
				upperBound = inbytes.length - i* Constants.RSA_PLAIN_BLOCK_SIZE;
				tmpInBytes = new byte[ inbytes.length - i* Constants.RSA_PLAIN_BLOCK_SIZE ];
			}

			System.arraycopy(inbytes, i*Constants.RSA_PLAIN_BLOCK_SIZE, tmpInBytes, 0, upperBound);

			tmpEncBytes = encrypt(tmpInBytes, pubKey);
			if( tmpEncBytes == null)
				return null;

			System.arraycopy(tmpEncBytes, 0, encryptedStoreMessage, i*encryptedBlockSize, encryptedBlockSize);
		}
		return encryptedStoreMessage;
	}
	
	/**
	 * Encrypts an RSA block
	 * @param bytes the byte array to encrypt
	 * @param pubKey the public key
	 * @return the encrypted byte array
	 * @throws Exception
	 */
	private static byte[] encrypt(byte[] bytes, PublicKey pubKey) {
		Cipher ce;

		byte[] cipherText = null;
		try {
			ce = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			ce.init(Cipher.ENCRYPT_MODE, pubKey);

			cipherText = ce.doFinal(bytes);
		} catch (Exception ex) {
			System.err.println("Encryption failed");
			return null;
		}
		return cipherText;
	}

}