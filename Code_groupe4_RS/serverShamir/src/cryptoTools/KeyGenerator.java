package cryptoTools;

//import Tools.Tools;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.bouncycastle.util.encoders.Base64;

/**
 * KeyGenerator is able to get Public/ Private keys and save them in files.
 * Also, it can retrieve the keys any time.
 * 
 * @author Athanasia Katsouraki
 */
public class KeyGenerator {

	// key generator - public-private keys
	public KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
	public KeyPair generatedKeyPair = keyGen.genKeyPair();

	public void setKeyGen(KeyPairGenerator keyGen) {
		keyGen.initialize(1024);
	}

	public KeyGenerator() throws NoSuchAlgorithmException {
	}

	/**
	 * get public key
	 * 
	 * @param keyPair
	 */
	public PublicKey getPublicKey(KeyPair keyPair) {
		PublicKey pub = keyPair.getPublic();
		System.out.println("Public Key: " + getHexString(pub.getEncoded()));
		return pub;
	}

	/**
	 * get private key
	 * 
	 * @param keyPair
	 */
	public PrivateKey getPrivateKey(KeyPair keyPair) {
		PrivateKey priv = keyPair.getPrivate();
		System.out.println("Private Key: " + getHexString(priv.getEncoded()));

		return priv;
	}

	/**
	 * get hex string
	 * 
	 * @param b
	 * @return
	 */
	public String getHexString(byte[] b) {
		String result = "";
		for (int i = 0; i < b.length; i++) {
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}

	/**
	 * Store Public Key in a file.
	 * 
	 * @param path
	 * @param keyPair
	 * @throws IOException
	 */
	public void SavePublicKey(String path, KeyPair keyPair) throws IOException {
		System.out.println("Save Public Key is in process...");
		PublicKey publicKey = keyPair.getPublic();
		// Store Public Key.
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(
				publicKey.getEncoded());

		FileOutputStream fos = new FileOutputStream(path);
		fos.write(x509EncodedKeySpec.getEncoded());
		fos.close();
		System.out.println("Public Key was saved successfully!!");
	}

	/**
	 * Store Private Key in a file.
	 * 
	 * @param path
	 * @param keyPair
	 * @throws IOException
	 */
	public void SavePrivateKey(String path, KeyPair keyPair) throws IOException {
		System.out.println("Save Private Key is in process...");
		PrivateKey privateKey = keyPair.getPrivate();
		// Store Private Key.
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
				privateKey.getEncoded());

		FileOutputStream fos = new FileOutputStream(path);
		fos.write(pkcs8EncodedKeySpec.getEncoded());
		fos.close();
		System.out.println("Private Key was saved successfully!!");
	}

	/**
	 * Load Public Key from a file.
	 * 
	 * @param path
	 * @param algorithm
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public PublicKey LoadPublicKey(String path, String algorithm)
			throws IOException, NoSuchAlgorithmException,
			InvalidKeySpecException {
		// Read Public Key.
		File filePublicKey = new File(path);
		FileInputStream fis = new FileInputStream(path);
		byte[] encodedPublicKey = new byte[(int) filePublicKey.length()];
		fis.read(encodedPublicKey);
		fis.close();

		// Generate PublicKey.
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
				encodedPublicKey);
		PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

		return publicKey;
	}

	/**
	 * Public Key to String.
	 * 
	 * @param pubKey
	 * @throws IOException
	 */
	public String PublicKeyToString(PublicKey pubKey) throws IOException {

		byte[] encodedPublicKey = new byte[(int) pubKey.getEncoded().length];
		encodedPublicKey = pubKey.getEncoded();
		encodedPublicKey = Base64.encode(encodedPublicKey);
		String encodedPubKey = new String(encodedPublicKey, "UTF-8");

		return encodedPubKey;
	}

	/**
	 * Load Public Key from a DB.
	 * 
	 * @param stringPubKey
	 * @param algorithm
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public PublicKey StringToPublicKey(String stringPubKey, String algorithm)
			throws IOException, NoSuchAlgorithmException,
			InvalidKeySpecException {
		// Read Public Key.
		byte[] decodedPublicKey = new byte[(int) stringPubKey.length()];
		decodedPublicKey = stringPubKey.getBytes();
		decodedPublicKey = Base64.decode(decodedPublicKey);
		// Generate PublicKey.
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
				decodedPublicKey);
		PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

		return publicKey;
	}

	/**
	 * Load Private Key from a file
	 * 
	 * @param path
	 * @param algorithm
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public PrivateKey LoadPrivateKey(String path, String algorithm)
			throws IOException, NoSuchAlgorithmException,
			InvalidKeySpecException {

		// Read Private Key.
		File filePrivateKey = new File(path);
		FileInputStream fis = new FileInputStream(path);
		byte[] encodedPrivateKey = new byte[(int) filePrivateKey.length()];
		fis.read(encodedPrivateKey);
		fis.close();

		// Generate PrivateKey.
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

		PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(
				encodedPrivateKey);
		PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

		return privateKey;
	}

}
