/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daemon;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import tools.Constants;
import tools.IOStreams;
import tools.Tools;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import beans.EncrUser;
import beans.FileDesc;
import beans.FileStored;
import beans.MyInfo;
import beans.User;
import messages.RequestSharedKey;
import messages.SendMyInfo;
import messages.ShareFile;
import shamir.PVSSEngine;
import shamir.ShamirShare;
import shamir.Share;
import command.Command;
import command.GetFileToShareCommand;
import command.ShareFileOnServerCommand;
import command.SendMyInfoCommand;
import command.SendRecoverRequestFileCommand;
import command.SendRecoverRequestShamirCommand;
import command.SendShareEngineCommand;
import cryptoTools.KeyGenerator;
import cryptoTools.KeyManager;
import dao.TcellDAO;

/**
 * ThreadServer in TCell. It receives commands from clients and executes the
 * associated actions.
 *
 * @author Athanasia Katsouraki, Majdi Ben Fredj, TORKHANI Rami
 *
 */
public class ClientConnectionManager extends Thread {
	Socket socket;
	
	public ClientConnectionManager(Socket s) {
		/**
		 * Creates a ThreadServer instance
		 * @param socket the client socket
		 */
		this.socket = s;
	}

	@Override
	public void run() {
		try {
			IOStreams ioStreams = new IOStreams(socket);
			Command cmd = readCommand(ioStreams);
			
			switch (cmd.getNumCommand()) {
			 
			 case Constants.CMD_SEND_MYINFO:
					MyInfo receivedUser = ((SendMyInfoCommand) cmd).getMyInfo();
				 	int receivedUserID = receivedUser.getMyGid();
					List<EncrUser> users = ((SendMyInfoCommand) cmd).getEncrUsers();
					
					//insert the user info if it doesn't exist
					if (!TcellDAO.getInstance().isUserInfoExists(receivedUserID))
				 		TcellDAO.getInstance().insertUserInfo(receivedUser);
				 	
					
					for (EncrUser user : users){
						if (!TcellDAO.getInstance().isUserContactsExists(receivedUserID, user.getEncrUserGID()))
							TcellDAO.getInstance().insertUserContacts(receivedUserID, user);
					}
				
				 	//Send status
					ioStreams.getOutputStream().writeInt(Constants.OK_USER);
					TcellDAO.getInstance().printUserInfo();
					TcellDAO.getInstance().printUserContacts();
			 break;
			 
			 case Constants.CMD_SHARE_FILE_ON_SERVER:
					// Write received file
					byte[] receivedFile = Base64.decode(((ShareFileOnServerCommand) cmd).getFile());
					String gid = ((ShareFileOnServerCommand) cmd).getGid();
					writeReceivedFile(gid , receivedFile);
					
					// Insert received metaData if it doesn't already exist
					if (TcellDAO.getInstance().isGIDexists(gid))
						TcellDAO.getInstance().deleteByGID(gid);
					
					insertReceivedMetaData(cmd);
	 
					//Send status
					ioStreams.getOutputStream().writeInt(Constants.OK);
					TcellDAO.getInstance().printFile();
			 break;
			 
			 case Constants.CMD_GET_FILE_TO_SHARE:
				 	sendFileToShare(((GetFileToShareCommand) cmd).getFileGID(),ioStreams);
				 
			 break;
			 
			 case Constants.CMD_SEND_RECOVER_REQUEST_FILE:
				 	int userID_F = ((SendRecoverRequestFileCommand) cmd).getUserID();
				 	
				 	String PrivateKeyPath_F = ((SendRecoverRequestFileCommand) cmd).getPrivPath();
				 	
				 	KeyGenerator keyGenarator_F = new KeyGenerator();
					PrivateKey privKey = keyGenarator_F.LoadPrivateKey(PrivateKeyPath_F, Constants.RSA_ALG);
					
					KeyManager keyManager_F = new KeyManager();
					String privKeyString = keyManager_F.PrivateKeyToString(privKey);
					
					ArrayList<String> userFiles_F = TcellDAO.getInstance().getGidByUserID(userID_F);
					
					User server_F = new User("127.0.0.1", 8880);
					
					for (int i = 0 ; i < userFiles_F.size() ; i++)
						ShareFile.shareFile(userFiles_F.get(i), userID_F, server_F, Constants.FROM_RS, privKeyString);
					
					//Send user information to TCELL
					List<EncrUser> users_F = TcellDAO.getInstance().getContactsByUserID(userID_F);
					MyInfo user_F = TcellDAO.getInstance().getUserByID(userID_F);
					
					user_F.setMyPrivKey(privKeyString);
					
					SendMyInfo.sendMyInfo(user_F, users_F);
					
					//Send status
					ioStreams.getOutputStream().writeInt(Constants.OK);
					TcellDAO.getInstance().printFile();
					
					System.out.println("Files recovered for user: "+userID_F);
				 break;
			 	 
			 	 case Constants.CMD_SEND_RECOVER_REQUEST_SHAMIR:
			 		int userID_S = ((SendRecoverRequestShamirCommand) cmd).getUserID();
				 	int[] contacts = ((SendRecoverRequestShamirCommand) cmd).getContacts();
				 	
			 		Vector<Share> partVector = new Vector<>();

					for (int contact : contacts)
						{
						MyInfo user = TcellDAO.getInstance().getUserByID(contact);
						
						RequestSharedKey.requestSharedKey(userID_S, partVector, user);
						
						}
					
					PVSSEngine userEngine = TcellDAO.getInstance().getEngineByUserID(userID_S);
					ShamirShare ss = new ShamirShare();
					ss.setEngine(userEngine);
					
					Share[] parts = new Share[partVector.size()];
					
					for (int i=0; i< partVector.size(); i++){
						parts[i] = partVector.elementAt(i);
						if (parts[i] != null) System.out.println("****** " + parts[i].toString());
					}
					
					String myRecoveredPrivKey = ss.shamirRecover(parts);
					
					ArrayList<String> userFiles_S = TcellDAO.getInstance().getGidByUserID(userID_S);
					
					User server_S = new User("127.0.0.1", 8880);
					
					for (int i = 0 ; i < userFiles_S.size() ; i++)
						ShareFile.shareFile(userFiles_S.get(i), userID_S, server_S, Constants.FROM_RS, myRecoveredPrivKey);
					
					//Send user information to TCELL
					List<EncrUser> users_S = TcellDAO.getInstance().getContactsByUserID(userID_S);
					MyInfo user_S = TcellDAO.getInstance().getUserByID(userID_S);
					user_S.setMyPrivKey(myRecoveredPrivKey);
					SendMyInfo.sendMyInfo(user_S, users_S);
					
					//Send status
					ioStreams.getOutputStream().writeInt(Constants.OK);
					TcellDAO.getInstance().printFile();
					
					System.out.println("Files recovered for user: "+userID_S);
			 		 
			 	 break;
				 
			 	 case Constants.CMD_SEND_SHARE_ENGINE:
				 	int userID_Engine = ((SendShareEngineCommand) cmd).getUserID();
			 		PVSSEngine engine = ((SendShareEngineCommand) cmd).getEngine();
			 		
			 		TcellDAO.getInstance().insertShareEngine(userID_Engine, engine);
			 		
			 		ioStreams.getOutputStream().writeInt(Constants.SHARED_ENGINE);
			 		
				 break;
			 
			default:
				System.out.println("unknown command");
				break;
			}
			
			ioStreams.close();
			socket.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * insertReceivedMetaData inserting the file described in the database
	 * @param fileDesc
	 */
	private void insertReceivedMetaData(Command cmd) {
		try {
			int userID = ((ShareFileOnServerCommand) cmd).getUserID();
			String gid = ((ShareFileOnServerCommand) cmd).getGid();
			String command = ((ShareFileOnServerCommand) cmd).getCommand();
			
			String encrIv = ((ShareFileOnServerCommand) cmd).getIv();
			String StrEncryptedSkey =  ((ShareFileOnServerCommand) cmd).getSkey();

			TcellDAO.getInstance().insertFile(userID, gid, StrEncryptedSkey, encrIv, command);
		}
		catch(Exception e) {
			e.printStackTrace();
			return;
		}
	}


	public Command readCommand(IOStreams stream) {
		 /**
		   * readCommand Receives Command.
		   * @param ioStreams
		   * 				 an IOStreams object
		   */
		Command cmd = null;
		try {
			cmd = (Command) stream.getInputStream().readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cmd;
	}

	/**
	   * Writes the received file.
	   * @param 
	   * 		filePath the file's path
	   * @param 	
	   * 		file the file itself
	   */
	public void writeReceivedFile(String gid, byte[] file) {
		FileOutputStream fos;
		try {
			// Creation of the received folder
			String tcellpath = Constants.RS_PATH;
			if (!Tools.createDir(tcellpath))
				return;

			/* Write the file in the received folder */
			fos = new FileOutputStream(tcellpath + gid);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			bos.write(file);
			System.out.println("The file '" + tcellpath + gid + "' (" + file.length + " bytes) has been received successfully!");
			bos.close();
			fos.close();

		} catch (FileNotFoundException ex) {
			System.err.println("ERROR : " + gid + " not found");
			return;
		} catch (IOException ex) {
			System.err.println("ERROR : can not write the file " + gid);
			return;
		}
	}
	
	/**
	    * Sends a file to a client through a stream.
	    * @param ioStreams
	    * 			    an IOStreams object
	    * @param fileGID 
	    * 				the FullFileName of the requested File
	    * @throws IOException 
	    */
	private void sendFileToShare(String fileGID, IOStreams ioStreams) {
		try {
			FileDesc fileDesc = TcellDAO.getInstance().getFileDescByGid(fileGID);
			Path path = Paths.get(fileDesc.fileID);
			byte[] file = Files.readAllBytes(path);
			FileStored fileToShare = new FileStored(fileDesc, Base64.encode(file)); 
			
			ioStreams.getOutputStream().writeObject(fileToShare);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
