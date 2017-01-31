/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package messages;

import java.net.Socket;

import tools.Constants;
import tools.IOStreams;
import tools.Tools;
import beans.FileStored;
import beans.User;
import command.Command;
import command.ShareFileCommand;

/**
 * ShareFile is the class used to send a share command with others TCs. It provides the
 * methods to retrieve a file description inside the database, create the share
 * message, and send it with the file to the TCs.
 * 
 * @author Majdi BEN FREDJ, TORKHANI Rami
 */
public class ShareFile {

	public static void shareFile(String fileGID, int userGID, User myInfo, String source, String privateKey){

		/**
		 * Establishes the connection with the TCs and send the file with the share
		 * message.
		 * 
		 * @param fileGID
		 *            the file's path to share.
		 * @param userGID
		 *           userGID to whom we share the file. Those users need
		 *            to be in the User table.
		 * @param myInfo 
		 * 			My information used to create the connection          
		 */

		try {
			//int myGid = myInfo.getUserGID();
			
			User user = GetUser.getUser(userGID, myInfo);
			
			/* get the file from the TCELL */
			FileStored fileToShare = GetFile.getFile(fileGID, myInfo);
			
			/*SHARE FILE to TCELL*/
			/* socket connection to the recipient TCELL */
			Socket socket = new Socket(user.getTCellIP(), user.getPort());
			/* Creation of the stream */
			IOStreams stream = new IOStreams(socket);
			
			/* send the share command to the recipient TCELL */
			Command shareFileCommand = new ShareFileCommand(Constants.CMD_SHARE_FILE, fileToShare, source, privateKey);
			stream.getOutputStream().writeObject(shareFileCommand);
			int status = stream.getInputStream().readInt();
			Tools.interpretStatus( status );

			stream.close();
			socket.close();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println("recipient TCell is not connected! The file can not be sent!");
		}

	}
}