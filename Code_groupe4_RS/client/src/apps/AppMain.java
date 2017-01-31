package apps;

import java.io.IOException;
import java.security.PublicKey;
import java.util.ArrayList;

import tools.Constants;
import api.ClientAPI;
import beans.User;
import configuration.Configuration;
import cryptoTools.KeyManager;


public class AppMain 
{
	/**
	 * APP Main
	 * 
	 * @author Majdi Ben Fredj
	 * 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		int userGID = Integer.parseInt(Configuration.getConfiguration().getProperty("myGID"));
		String tCellIP = Configuration.getConfiguration().getProperty("myIP");
		int port = Integer.parseInt(Configuration.getConfiguration().getProperty("myPort"));

		User user= null;

		// load user PubKey
		try {
			String KeyPath = Configuration.getConfiguration().getProperty("keyPath");
			KeyManager keygen = new KeyManager();
			String publicKeyPath = KeyPath + Constants.PUB_KEY_PREFIX + userGID + Constants.KEY_EXT;
			PublicKey pubKey = keygen.LoadPublicKey(publicKeyPath, Constants.RSA_ALG);
			String pubkey = keygen.PublicKeyToString(pubKey);

			user = new User(userGID, tCellIP, port, pubkey);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/**
		 * To choose the wanted example:
		 * 
		 * 0: Store 2 files in TCell and RS receiving them
		 * 	  (run initDB_S->daemon_S and initDB_X->daemon_X)
		 * 1: Share the files to two other TCells and RS receiving them
		 *    (run daemon_S and daemon_X and initDB_Y->daemon_Y and initDB_Z->daemon_Z)
		 * 2: Recovering the files by giving my private key path (must run the empty database)
		 *    (run daemon_S and initDB_X_rec->daemon_X)
		 * 3: Share my private key to my friends
		 * 	  (run daemon_S and daemon_X and (friends) initDB_Y->daemon_Y ... )
		 * 4: Recovering the files by giving my friends' gid (must run the empty database)
		 *    (run daemon_S and initDB_X_rec->daemon_X and (friends) initDB_Y->daemon_Y ... )
		 * 5: Same as 4 with another friends
		 *    (run daemon_S and initDB_X_rec->daemon_X and (friends) initDB_Y->daemon_Y ... )
		 */
		int test = 0;
		
		ArrayList<String> files = null;
		
		switch (test){
			case 0:
				// STOREFILE
				ClientAPI.storeFile("/home/user/SIPD_Shamir/machines/Client10/Image.jpg", user);
				ClientAPI.storeFile("/home/user/SIPD_Shamir/machines/Client10/1_TP_SIPD_2016.pdf", user);
				
				// GETFILEDESC
				files = new ArrayList<>();
				ClientAPI.getFileDesc(user);
			break;
			
			case 1:		
				// GETFILEDESC
				files = new ArrayList<>();
				files = ClientAPI.getFileDesc(user);
				
				// SHAREFILE
				for (int i = 0 ; i < files.size() ; i++)
					ClientAPI.shareFile(files.get(i), 11, user);
				
				// SHAREFILE
				for (int i = 0 ; i < files.size() ; i++)
					ClientAPI.shareFile(files.get(i), 13, user);
			break;
				
			case 2:
				// RECOVERYFROMFILE
				ClientAPI.recoverFromFile("/home/user/SIPD_Shamir/machines/Keys/priv10.key", user);			
							
				// GETFILEDESC
				files = new ArrayList<>();
				files = ClientAPI.getFileDesc(user);
				
				// READFILE
				for (int i = 0 ; i < files.size() ; i++)
					ClientAPI.readFile(files.get(i), user);
			break;
			
			case 3:
				// SHAREPRIVKEY
				int[] receivers = {11, 13, 14};
				ClientAPI.sharePrivKey(receivers, user);
			break;
			
			case 4:
				// RECOVERYFROMSHAMIR
				int[] contacts1 = {11, 13};
				ClientAPI.recoverFromShamir(contacts1, user);
				
				// GETFILEDESC
				files = new ArrayList<>();
				files = ClientAPI.getFileDesc(user);
				
				// READFILE
				for (int i = 0 ; i < files.size() ; i++)
					ClientAPI.readFile(files.get(i), user);
			break;
			
			case 5:
				// RECOVERYFROMSHAMIR
				int[] contacts2 = {13, 14};
				ClientAPI.recoverFromShamir(contacts2, user);
				
				// GETFILEDESC
				files = new ArrayList<>();
				files = ClientAPI.getFileDesc(user);
				
				// READFILE
				for (int i = 0 ; i < files.size() ; i++)
					ClientAPI.readFile(files.get(i), user);
			break;
		}
				
		System.out.println(" Dooooone");
	}
}