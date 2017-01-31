package messages;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

import beans.User;
import command.Command;
import command.ShareKeyCommand;
import shamir.Share;
import tools.Constants;
import tools.IOStreams;
import tools.Tools;

/**
 * ShareKey Command sends to the chosen User a part of the key
 * 
 * @author Groupe 4 - 2015-2016
 */
public class ShareKey {

	public static void shareKey(int myGid, User userToShare, Share part) throws ConnectException, NullPointerException{

		try {
			
			//send the share engine to RS
			Socket socket = new Socket( userToShare.getTCellIP(), userToShare.getPort());
			IOStreams stream = new IOStreams(socket);
			
			// send the share command to the recipient TCELL
			Command shareKeyCommand = new ShareKeyCommand(Constants.CMD_SHARE_KEY, myGid, part);
			stream.getOutputStream().writeObject(shareKeyCommand);
			int status = stream.getInputStream().readInt();
			Tools.interpretStatus( status );

			stream.close();
			socket.close();
						
		} catch (IOException ex) {
			ex.printStackTrace();
			System.err.println("Recovery Server not connected!");
		} 
	}
	
}
