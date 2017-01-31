/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptoTools;

import java.io.OutputStream;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/**
 * Symmetric decryption
 * 
 * @author Athanasia Katsouraki
 * 
 */
public class SymmetricDecryption 
{

	/**
	 * Decrypts a file and writes the result as a new file
	 * @param bytes the byte array, corresponding to the encrypted file
	 * @param skey the secret key
	 * @param output the OutputStream object to write the decrypted file
	 * @param iv_bytes the initialization vector
	 * @throws Exception 
	 */
	public static void decryptFile(byte[] bytes, SecretKey skey, OutputStream output, byte[] iv_bytes ) throws Exception 
	{
		AlgorithmParameterSpec iv = new IvParameterSpec( iv_bytes );

		Cipher cd = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cd.init( Cipher.DECRYPT_MODE, skey, iv );

		CipherOutputStream cOutputStream = new CipherOutputStream(output, cd);
		cOutputStream.write(bytes, 0, bytes.length);
		cOutputStream.close();
	}
}

