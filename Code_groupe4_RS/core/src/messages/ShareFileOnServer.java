package messages;

import java.net.Socket;

import beans.User;
import command.Command;
import command.ShareFileOnServerCommand;
import tools.Constants;
import tools.IOStreams;
import tools.Tools;

/**
 * ShareFileOnServer Command sends a stored file or a received file from 
 * a ShareFileCommand to the Recovery Server
 * 
 * @author Groupe 4 - 2015-2016
 */
public class ShareFileOnServer {

	public static void shareFileOnServer(String file, String fileName, String skey, String iv, User user, String command){

		try {
			
			//SHARE FILE to RS
			// socket connection to the recipient TCELL
			Socket socketServer = new Socket("127.0.0.1", 8880);
			// Creation of the stream
			IOStreams streamServer = new IOStreams(socketServer);
			
			int userID = user.getUserGID();
			String gid = userID + "|" + fileName;
			
			// send the share command to the recipient TCELL
			Command shareFileOnServerCommand = new ShareFileOnServerCommand(Constants.CMD_SHARE_FILE_ON_SERVER, file, gid, skey, iv, userID, command);
			streamServer.getOutputStream().writeObject(shareFileOnServerCommand);
			int statusServer = streamServer.getInputStream().readInt();
			Tools.interpretStatusServer( statusServer );

			streamServer.close();
			socketServer.close();
						
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println("Recovery Server not connected!");
		}
	}
}
