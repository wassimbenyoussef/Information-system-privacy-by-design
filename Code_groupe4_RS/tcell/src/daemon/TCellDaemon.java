package daemon;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import beans.EncrUser;
import beans.MyInfo;
import beans.User;
import configuration.Configuration;
import cryptoTools.AsymmetricEncryption;
import dao.TcellDAOToken;
import messages.SendMyInfo;
import tools.Tools;
/**
 * Daemon in TrustedCell.
 * 
 * @author Athanasia Katsouraki
 */
public class TCellDaemon{


	public static void main(String[] args) throws IOException {
		
		//Vérifier si un fichier de configuration est défini
		if (System.getProperty("configurationFilePath") == null) {
			System.out.println("'configurationFilePath' system property is not defined");
			return;
		}
		
		System.getProperties().setProperty("jdbc.port", Configuration.getConfiguration().getProperty("dbPort"));
		
		MyInfo myInfo  = TcellDAOToken.getInstance(false).getMyInfo();
		
		int userGID = myInfo.getMyGid();
		int listenPort = myInfo.getMyTcellPort();
		String TcellIP = myInfo.getMyTcellIp();
		
		ServerSocket server = null;
		
		try {

			/* Creation of the server socket */
			server = new ServerSocket(listenPort);

			/* The server listens for new connections and accepts it */
			System.out.println("TCell Daemon started...");

			
			//Send user information to RS
			List<User> users = TcellDAOToken.getInstance(false).getAllUsers();
			
			// Encryption of the users (contacts) information

			List<EncrUser> encrUsers = new ArrayList<EncrUser>();

			if (!users.isEmpty()){
				PublicKey pubKey = Tools.stringToPublicKey(myInfo.getMyPubKey());
				
				for (User contact : users) {
					byte[] encrUserGID = AsymmetricEncryption.encryptBlockByBlock( Integer.toString(contact.getUserGID()).getBytes(), pubKey);
					byte[] encrTCellIP = AsymmetricEncryption.encryptBlockByBlock( contact.getTCellIP().getBytes(), pubKey);
					byte[] encrPort = AsymmetricEncryption.encryptBlockByBlock( Integer.toString(contact.getPort()).getBytes(), pubKey);
					byte[] encrPubKey = AsymmetricEncryption.encryptBlockByBlock( contact.getPubKey().getBytes(), pubKey);
					
					String strEncrUserGID = Base64.encode(encrUserGID);
					String strEncrTCellIP = Base64.encode(encrTCellIP);
					String strEncrPort = Base64.encode(encrPort);
					String strEncrPubKey = Base64.encode(encrPubKey);
					
					EncrUser encrUser = new EncrUser(strEncrUserGID, strEncrTCellIP, strEncrPort, strEncrPubKey);
					
					encrUsers.add(encrUser);
				}
			}
			
			MyInfo user = new MyInfo(userGID, TcellIP, listenPort, myInfo.getMyPubKey(), null);
			SendMyInfo.sendMyInfo(user, encrUsers);
			
				
			while (true) {
				System.out.println("\nWaiting for a connection from an APP or from other TCells");
				Socket clientSocket = server.accept();
				System.out.println("Accepted connection : " + clientSocket);

				/* For each socket, a new thread is created */
				ClientConnectionManager ccm = new ClientConnectionManager(clientSocket, userGID);
				ccm.start();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			server.close();
		}
	}
}