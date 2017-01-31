package messages;

import java.io.IOException;
import java.net.Socket;

import beans.User;
import command.Command;
import command.SharePrivKeyCommand;
import tools.Constants;
import tools.IOStreams;
import tools.Tools;

/**
 * SharePrivKey Command sends the private key to the chosen users with Shamir Method
 * 
 * @author Groupe 4 - 2015-2016
 */
public class SharePrivKey {

	public static void sharePrivKey(int[] receivers, User user){

		try {
			
			//send the share engine to RS
			Socket socket = new Socket( user.getTCellIP(), user.getPort());
			IOStreams stream = new IOStreams(socket);
			
			// send the share command to the recipient TCELL
			Command sharePrivKeyCommand = new SharePrivKeyCommand(Constants.CMD_SHARE_PRIV_KEY, receivers);
			stream.getOutputStream().writeObject(sharePrivKeyCommand);
			int status = stream.getInputStream().readInt();
			Tools.interpretStatus( status );

			stream.close();
			socket.close();
									
		} catch (IOException ex) {
			ex.printStackTrace();
			System.err.println("Error sharig private key!!");
		} 
	}
}
