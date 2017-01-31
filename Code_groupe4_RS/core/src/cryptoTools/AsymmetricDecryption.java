package cryptoTools;

import java.security.PrivateKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;

import tools.Constants;
import tools.Tools;

/**
 * Asymmetric decryption
 * 
 * @author Athanasia Katsouraki
 * 
 */

public class AsymmetricDecryption {

	
	/**
	 * Decrypt a byte array block by block.
	 * 
	 * @param encBytes
	 *            the byte array to decrypt
	 * @param prKey
	 *            the private key
	 * @return the decrypted byte array
	 * @throws Exception
	 */
	public static byte[] decryptBlockByBlock(byte[] encBytes, PrivateKey prKey) throws Exception {
		
		byte[] decMessage;
		try {
			int blockSize = Constants.RSA_KEY_LENGTH / 8;
			int numOfBlocks = (int) Math.ceil(((double) encBytes.length) / blockSize);

			byte[] tmpInBytes = new byte[blockSize];
			byte[] decryptedStoreMessage = new byte[blockSize * numOfBlocks];
			byte[] tmpDecBytes = null;
			int c = 0;

			for (int i = 0; i < numOfBlocks; i++) {
				tmpInBytes = new byte[blockSize];

				for (int j = 0; j < blockSize; j++) {
					if (i * blockSize + j >= encBytes.length) {
						System.err.println("ERROR : the decryption has failed. Please check if the public and private keys are correct");
						return null;
					}
					tmpInBytes[j] = encBytes[i * blockSize + j];
				}

				tmpDecBytes = decrypt(tmpInBytes, prKey);

				for (int j = 0; j < tmpDecBytes.length; j++){
					decryptedStoreMessage[c++] = tmpDecBytes[j];
				}

			}
			 decMessage = Tools.copyBytesStore(decryptedStoreMessage, c);
		} catch (BadPaddingException ex) {
			System.err.println("ERROR : the decryption has failed. Please check if the public and private keys are correct");
			return null;
		}

		return decMessage;
	}

	/**
	 * Decrypts an RSA block
	 * 
	 * @param bytes
	 *            the byte array to decrypt
	 * @param prKey
	 *            the private key
	 * @return the decrypted byte array
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] bytes, PrivateKey prKey) throws Exception {

		Cipher cd;

		byte[] cipherText = null;
		try {

			cd = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cd.init(Cipher.DECRYPT_MODE, prKey);
			cipherText = cd.doFinal(bytes);

		} catch (Exception e) {
			System.err.println("ERROR : the decryption has failed. Please check if the public and private keys are correct");
			return null;
		}
		return cipherText;
	}
}
