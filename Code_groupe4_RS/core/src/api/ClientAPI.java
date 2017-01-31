package api;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import messages.GetFileDesc;
import messages.ReadFile;
import messages.SendRecoverRequestFile;
import messages.SendRecoverRequestShamir;
import messages.ShareFile;
import messages.SharePrivKey;
import messages.StoreFile;
import tools.Constants;
import beans.User;





/**
 * USER API
 * 
 */
public class ClientAPI
{


	public static void storeFile(String fileID, User user ) {
		StoreFile.storeFile(fileID, user );
	}


	public static ArrayList<String> getFileDesc(User user) {
		return GetFileDesc.getFileDesc(user);
	}


	public static void readFile( String fileGID, User user ) {
		ReadFile.readFile(false, fileGID, user );
	}
	
	public static void shareFile(String fileGID, int userGID, User myInfo) throws UnknownHostException, IOException {
		ShareFile.shareFile( fileGID, userGID, myInfo, Constants.FROM_TCELL, "");
	}
	
	public static void recoverFromFile(String privPath, User user) {
		SendRecoverRequestFile.RecoverFromFile(privPath, user);
	}
	
	public static void recoverFromShamir(int[] contacts, User user) {
		SendRecoverRequestShamir.RecoverFromShamir(contacts, user);
	}
	
	public static void sharePrivKey(int[] receivers, User user) {
		SharePrivKey.sharePrivKey(receivers, user);
	}
}
