package messages;

import java.net.Socket;

import beans.User;
import command.Command;
import command.SendRecoverRequestFileCommand;
import tools.Constants;
import tools.IOStreams;
import tools.Tools;

public class SendRecoverRequestFile {

	public static void RecoverFromFile(String privPath, User user){

		try {
			
			System.out.println("Recovering files...");
			
			//Sends a recover request to RS
			// socket connection to the recipient TCELL
			Socket socketServer = new Socket("127.0.0.1", 8880);
			// Creation of the stream
			IOStreams streamServer = new IOStreams(socketServer);
			
			int userID = user.getUserGID();
			
			// send the recover command to the recipient TCELL
			Command recoverCommand = new SendRecoverRequestFileCommand(Constants.CMD_SEND_RECOVER_REQUEST_FILE, privPath, userID);
			streamServer.getOutputStream().writeObject(recoverCommand);
			int statusServer = streamServer.getInputStream().readInt();
			Tools.interpretStatusServer( statusServer );
			
			if (statusServer == Constants.SQL_OK || statusServer == Constants.OK)
				System.out.println("Files succesfully recovered from the RS!");

			streamServer.close();
			socketServer.close();
						
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println("Recovery Server not connected!");
		}
	}
}
