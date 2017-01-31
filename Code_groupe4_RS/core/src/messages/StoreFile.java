package messages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.Socket;
import java.security.PublicKey;

import javax.crypto.SecretKey;

import tools.Constants;
import tools.IOStreams;
import tools.SymKeyTools;
import tools.Tools;
import beans.User;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import command.StoreFileCommand;
import configuration.Configuration;
import cryptoTools.AsymmetricEncryption;
import cryptoTools.SymmetricEncryption;

/**
 * StoreFile is the class used to store a file in a TC.
 * It provides the methods to send an encrypted file to a TC followed by an encrypted message containing the description of the file.
 * The file is encrypted by AES and the message is encrypted by RSA, with the TC's public key
 * 
 * @author Majdi Ben Fredj,TORKHANI Rami
 * 
 */
public class StoreFile 
{
	public static void storeFile(String fileToSendPath, User user ){

		/**
		 * Establishes the connection with the TCELL and send to file with the store message.
		 * @param fileToSendPath the path of the file to send
		 * @param user the addressee user
		 */
		try {
			/* The socket used to send the file and the messages */
			Socket socket;

			/* Extract the fileName from the path */
			String fileName = Tools.getFileName(fileToSendPath);
			
			/* Creation of the temporary directory */
			if ( !Tools.createDir(Configuration.getConfiguration().getProperty("appHomePath") + Constants.TMP_FILES ))
				return;

			System.out.println("File is being sent to TCell...");
			String destIP = user.getTCellIP();
			int destPort = user.getPort();

			/* Creates a stream socket and connects it to the specified port number at the specified IP address */
			socket = new Socket( destIP, destPort);

			/* Creation of the stream */
			IOStreams stream = new IOStreams( socket );

			/* Encryption of the file */
			SecretKey sKey = SymKeyTools.GenSymKey();
			byte[] iv = new byte[ Constants.IV_LENGTH ];
			byte[] encryptFile = encryptFile( fileToSendPath, sKey, iv );
			String encryptFilePath = Configuration.getConfiguration().getProperty("appHomePath") + Constants.TMP_FILES + Constants.SYM_ENCR_FILE_NAME + fileName;
			
			PublicKey pubKey = Tools.stringToPublicKey(user.getPubKey());
			byte[] encrSkey= AsymmetricEncryption.encryptBlockByBlock( sKey.getEncoded(), pubKey);
			
			StoreFileCommand storeCmd= new StoreFileCommand(Constants.CMD_STORE_FILE, Base64.encode(encrSkey), Base64.encode(iv), encryptFilePath, Base64.encode(encryptFile));

			//send command
			//stream.getOutputStream().writeInt(myGid);
			stream.getOutputStream().writeObject(storeCmd);
			
			//recieve status from the server
			int status = stream.getInputStream().readInt();
			Tools.interpretStatus( status );

			stream.close();
			socket.close();

			if (status == Constants.SQL_OK || status == Constants.OK){
				//send to the RS
				String file = Base64.encode(encryptFile);
				String EncryptedFileName = new File(encryptFilePath).getName();
				
				//enryption of the iv
				byte[] encrIv= AsymmetricEncryption.encryptBlockByBlock( iv, pubKey);
				
				ShareFileOnServer.shareFileOnServer(file, EncryptedFileName, Base64.encode(encrSkey), Base64.encode(encrIv), user, "STORE");
			}
				
			
			/* Delete the temporarily encrypted file */
			Tools.deleteFileFromPath(encryptFilePath);

		} catch (Exception ex) {
			System.err.println("ERROR : store file has failed");
		}

	}

	/**
	 * Encrypts the file.
	 * @param fullFileName the path of the file to encrypt
	 * @param sKey the SecretKey 
	 * @param iv the initialization vector
	 * @return the encrypted file
	 */
	public static byte[] encryptFile(String fullFileName, SecretKey sKey, byte[] iv)
	{
		byte [] encbytes = null;
		

		try
		{
			String filename = Tools.getFileName( fullFileName );
			FileInputStream	is = new FileInputStream(fullFileName); 

			String fullEncrName = Configuration.getConfiguration().getProperty("appHomePath") + Constants.TMP_FILES + Constants.SYM_ENCR_FILE_NAME+filename;
			FileOutputStream eos = new FileOutputStream( fullEncrName );
			//Encrypt the file
			int dataSize = is.available();
			byte[] inbytes = new byte[ dataSize ];
			is.read(inbytes);
			//Calling the Symmetric Encryption for encrypting the file
			SymmetricEncryption.encryptFile( inbytes,sKey, eos, iv );
			eos.flush();

			//Read the Outputstream
			encbytes = Tools.readFileFromPath( fullEncrName );
			if( encbytes == null )
			{
				System.err.println("ERROR : The file cannot be encrypted");
				is.close();
				eos.close();
				return null;
			}

			is.close();
			eos.close();

			System.out.println("Encryption is done..."+fullEncrName+" is now ready to be sent");


		} catch (Exception ex) {
			ex.printStackTrace();
		}


		return encbytes;
	}
}