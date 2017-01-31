package tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.bouncycastle.util.encoders.Base64;


/**
 * Tools provides static utility methods. 
 * @author tranvan
 */

public class Tools 
{

	/**
	 * Checks if a file exists 
	 * @param fullFilename the file's path
	 * @return true if the file exists, false if not
	 */
	public static boolean isFileExists ( String fullFilename )
	{
		return new File(fullFilename).exists();
	}

	/**
	 * Create a directory in case it does not exist.
	 * @param path the file's path
	 * @return true if the directory has been created of it already exists, false if an error occured
	 */
	public static boolean createDir ( String path )
	{
		boolean res = true;
		File f = new File( path );
		// if the directory does not exist, create it
		if ( !f.exists() ) 
		{
			res = f.mkdir(); 
			if ( res )
				System.out.println("Directory " + path + " created" ); 
			else
				System.err.println("ERROR : cannot create directory " + path ); 
		}

		return res;
	}

	public static String getHome()
	{

		String home = System.getProperty("user.dir");
		return home+"/";
	}

	
	
	
	public static boolean containsChar(String s, char search) 
	{
		if (s.length() == 0)
			return false;
		else
			return s.charAt(0) == search || containsChar(s.substring(1), search);
	}

	public static int timesOfCharInStr(String str, char toCheck)
	{
		int count=0;
		for (int i = 0; i < str.length(); i++) 
		{
			if (str.charAt(i) == toCheck) 
				count++;  
		}
		return count;
	}

	/**
	 * Extract the file name from the fileID
	 * 
	 * @param fileName
	 *            the fileName
	 * @return the file name
	 */
	public static String getFileName(String fileName) {
		String fileID = null;
		fileName.replaceAll("\\\\", "\\/");

		int i = timesOfCharInStr(fileName, '/');
		if (containsChar(fileName, '/')) {
			fileID = fileName.split("\\/")[i];
		} else {
			fileID = fileName;
		}
		return fileID;
	}

	
	public static byte[] copyBytesStore(byte[] arr, int length)
	{
		byte[] newArr = null;
		if (arr.length == length)
			newArr = arr;
		else
		{
			newArr = new byte[length];
			for (int i = 0; i < length; i++)
			{
				newArr[i] = (byte) arr[i];
			}
		}
		return newArr;
	}


	/**
	 * Utility method to read the bytes of a file
	 * @param path the file's path
	 * @return a byte array filled by the content of the file
	 */
	public static byte[] readFileFromPath ( String path ) 
	{
		byte[] file = null;
		try 
		{
			FileInputStream inStream = new FileInputStream( path );
			int dataSize = inStream.available();
			file = new byte[ dataSize ];
			inStream.read( file );
			inStream.close();

		} catch (IOException ex) {
			System.err.println("ERROR : "+ path + " can not be read");
			return null;
		}

		return file;
	}

	/**
	 * Utility method to write some bytes inside a file
	 * @param path the file's path
	 * @param file the bytes to write
	 * @return true if the file has been written, false otherwise
	 */
	public static boolean writeFileFromPath( String path, byte[] file )
	{
		try
		{
			FileOutputStream outStream = new FileOutputStream( path ); 
			outStream.write( file );
			outStream.close();

		} catch (IOException ex) {
			System.err.println("ERROR : "+ path + " can not be written");
			ex.printStackTrace();
			return false;
		}

		return true;
	}



	/**
	 * Utility method to delete a file from a path
	 * @param path the file's path
	 * @return true if the file has been deleted, false otherwise
	 */
	public static boolean deleteFileFromPath( String path )
	{
		try
		{
			File file = new File( path );

			if(file.delete()){
				return true;
			}else
				System.err.println("Delete operation has failed");

		}catch(Exception e){
			System.err.println("Delete operation has failed");
			return false;
		}

		return true;
	}
	
	public static PrivateKey stringToPrivateKey(String stringPrivKey) throws IOException, NoSuchAlgorithmException,InvalidKeySpecException {
		// Read Public Key.
		byte[] decodedPrivateKey = new byte[(int) stringPrivKey.length()];
		decodedPrivateKey = stringPrivKey.getBytes();
		decodedPrivateKey = Base64.decode(decodedPrivateKey);
		// Generate PrivateKey.
		KeyFactory keyFactory = KeyFactory.getInstance(Constants.RSA_ALG);
		PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(decodedPrivateKey);
		PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

		return privateKey;
	}
	/**
	 * Convert an encoded String into a PublicKey object
	 * @param stringPubKey the encoded String
	 * @return a PublicKey object
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static PublicKey stringToPublicKey( String stringPubKey ) throws IOException, NoSuchAlgorithmException,InvalidKeySpecException 
	{
		// Read Public Key.
		byte[] decodedPublicKey = new byte[(int) stringPubKey.length()];
		decodedPublicKey = stringPubKey.getBytes();
		decodedPublicKey = Base64.decode(decodedPublicKey );
		// Generate PublicKey.
		KeyFactory keyFactory = KeyFactory.getInstance(Constants.RSA_ALG);
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(decodedPublicKey);
		PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

		return publicKey;
	}
	
	/**
	 * Print a message based on the received status
	 * @param status the received status
	 */
	public static void interpretStatus( int status )
	{
		switch ( status )
		{
			case Constants.OK_USER:
				System.out.println("Info successfully sent");
				break;
			case Constants.SHARED:
				System.out.println("Key successfully shared");
				break;
			case Constants.SHARED_ENGINE:
				System.out.println("Engine successfully sent");
				break;
			case Constants.OK: case Constants.SQL_OK:
				System.out.println("File correctly received by the TCell");
				break;
			case Constants.SQL_NOT_UNIQUE:
				System.err.println("WARNING : the file is already in the database");
				break;
			case Constants.SQL_KO: case Constants.KO:    
				System.err.println("ERROR : the database server has failed");
				break;
			default:
				System.err.println("Error");
				break;
		}
	}
	
	/**
	 * Print a message based on the received status from the RS
	 * @param status the received status
	 */
	public static void interpretStatusServer( int status )
	{
		switch ( status )
		{
			case Constants.OK_USER:
				System.out.println("User information correctly received by the RS");
				break;
			case Constants.OK: case Constants.SQL_OK:
				System.out.println("File correctly received by the RS");
				break;
			case Constants.SQL_NOT_UNIQUE:
				System.err.println("WARNING : the file is already in the database");
				break;
			case Constants.SQL_KO: case Constants.KO:    
				System.err.println("ERROR : the database server has failed");
				break;
			default:
				System.err.println("Error");
				break;
		}
	}


	/**
	 * Detects and returns the IP of my PC.
	 * 
	 * @return IP
	 */
	public String getMyIPAddress () 
	{
		String IP = "";
		try {
			InetAddress thisIp = InetAddress.getLocalHost();
			IP = thisIp.getHostAddress();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		return IP;
	}

}
