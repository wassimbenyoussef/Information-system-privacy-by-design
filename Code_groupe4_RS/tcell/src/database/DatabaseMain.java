/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import configuration.Configuration;
import tools.Constants;
import tools.Tools;
import cryptoTools.KeyManager;
import dao.TcellDAOToken;

/**
 * Initialization of the DataBase.
 * 
 * @author Majdi Ben Fredj
 */
public class DatabaseMain {
	/**
	 * Initialization of db.
	 * 
	 * @param args
	 * @throws NoSuchAlgorithmException
	 * @throws FileNotFoundException
	 */


	public static void main(String[] args) throws NoSuchAlgorithmException,
			FileNotFoundException {

		if (System.getProperty("configurationFilePath") == null) {
			System.out.println("'configurationFilePath' system property is not defined");
			return;
		}
		try {
			System.getProperties().setProperty("jdbc.port", Configuration.getConfiguration().getProperty("dbPort"));
			TcellDAOToken.getInstance(true);
			TcellDAOToken.getInstance(false).DropTables();
			TcellDAOToken.getInstance(false).CreateTables();
			

			String contactsFilePath = Configuration.getConfiguration().getProperty("contactsFilePath");

			if (!Tools.isFileExists(contactsFilePath)) {
				System.err.println("ERROR : " + contactsFilePath + " does not exists");
				return;
			}

			// Open the file
			// Insert Values into tables.
			// Retrieve Contacts' Info to Insert in the USER table
			// ContactsFilePath: -- meta/contacts/ContactsTCellDB.txt
			String KeyPath = Configuration.getConfiguration().getProperty("keyPath");
			FileInputStream fstream = new FileInputStream(contactsFilePath);
			// Get the contacts info of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;

			// Read File Line By Line
			KeyManager keygen;
			while ((strLine = br.readLine()) != null) {
				if (strLine != "") {
					keygen = new KeyManager();
					String[] tokens = strLine.split(":");
					String TCellIP = tokens[0];
					String TCellPort = tokens[1];
					int UserID = Integer.parseInt(tokens[2]);
					String PublicKeyPath = KeyPath + Constants.PUB_KEY_PREFIX + UserID + Constants.KEY_EXT;
					// Load the corresponding public key
					PublicKey pubKey = keygen.LoadPublicKey(PublicKeyPath, Constants.RSA_ALG);
					// public key to String
					String strPubkey = keygen.PublicKeyToString(pubKey);
					// Insert User in the TCellDB
					TcellDAOToken.getInstance(false).insertUser(UserID, TCellIP, Integer.parseInt(TCellPort), strPubkey);
				}
			}
			br.close();
			in.close();
			
			//TcellDAOToken.getInstance(false).insertFileDesc("10|apps/APP10/Encrypted_readMeTest.txt", "tcells\\TCELL10\\Encrypted_readMeTest.txt", "mAPK1g3R1S3PnudZTWraYw==", "tZn25lo6lX+dohSnWL0JRA==", "STORE", "my file");
			
			
			// Init MyInfo
			int myGid = Integer.parseInt(Configuration.getConfiguration().getProperty("userGID"));
			String myTcellIp = Configuration.getConfiguration().getProperty("tcellIP") ;
			int myTcellPort = Integer.parseInt(Configuration.getConfiguration().getProperty("port"));
			
			//load myPubKey
			keygen = new KeyManager();
			String MyPublicKeyPath = KeyPath + Constants.PUB_KEY_PREFIX + myGid + Constants.KEY_EXT;
			PublicKey pubKey = keygen.LoadPublicKey(MyPublicKeyPath, Constants.RSA_ALG);
			String myPubkey = keygen.PublicKeyToString(pubKey);

			//load myPrivKey
			keygen = new KeyManager();
			String MyPrivateKeyPath = KeyPath + Constants.PR_KEY_PREFIX + myGid + Constants.KEY_EXT;
			PrivateKey privKey = keygen.LoadPrivateKey(MyPrivateKeyPath, Constants.RSA_ALG);
			String myPrivkey = keygen.PrivateKeyToString(privKey);
			
			TcellDAOToken.getInstance(false).insertMyInfo(myGid, myTcellIp, myTcellPort, myPubkey, myPrivkey);
			
			TcellDAOToken.getInstance(false).printAllUsers();
			TcellDAOToken.getInstance(false).printMyInfo();
			
			TcellDAOToken.getInstance(false).printAllFilesDesc();
			
			TcellDAOToken.getInstance(false).Save_DBMS_on_disk();
			
			TcellDAOToken.getInstance(false).Shutdown_DBMS();

		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
			
	}

}

