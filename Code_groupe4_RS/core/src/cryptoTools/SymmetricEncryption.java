/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptoTools;

import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import tools.Constants;

/**
 * Symmetric encryption
 * 
 * @author Athanasia Katsouraki
 * 
 */
public class SymmetricEncryption 
{
	/**
	 * Encrypts a file and writes the result as a new file
	 * @param bytes the byte array, corresponding to the decrypted file
	 * @param skey the secret key
	 * @param output the OutputStream object to write the encrypted file
	 * @param iv_bytes the initialization vector
	 * @throws Exception 
	 */
	public static void encryptFile(byte[] bytes, SecretKey skey, OutputStream output, byte[] iv_bytes ) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, InvalidAlgorithmParameterException
	{
		if ( iv_bytes == null || iv_bytes.length != Constants.IV_LENGTH )
		{
			System.err.println("ERROR : initialization vector is not correct");
			return;
		}

		SecureRandom rnd = new SecureRandom();
		rnd.nextBytes( iv_bytes );
		AlgorithmParameterSpec iv = new IvParameterSpec( iv_bytes );
		System.out.println("Symmetric Encryption starts now...");
		Cipher ce = Cipher.getInstance("AES/CBC/PKCS5Padding");
		ce.init( Cipher.ENCRYPT_MODE, skey, iv );

		CipherOutputStream cOutputStream = new CipherOutputStream(output, ce);
		cOutputStream.write( bytes );

		cOutputStream.close();
	}

}

