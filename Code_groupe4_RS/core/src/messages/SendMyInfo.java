package messages;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import beans.EncrUser;
import beans.MyInfo;
import command.Command;
import command.SendMyInfoCommand;
import tools.Constants;
import tools.IOStreams;
import tools.Tools;

public class SendMyInfo {

	public static void sendMyInfo(MyInfo myInfo, List<EncrUser> encrUsers){

		try {
			
			// socket connection to the recipient TCELL
			Socket socketServer = new Socket("127.0.0.1", 8880);
			// Creation of the stream
			IOStreams streamServer = new IOStreams(socketServer);
			
			// send the myinfo command to the recipient TCELL
			Command sendMyInfoCommand = new SendMyInfoCommand(Constants.CMD_SEND_MYINFO, myInfo, encrUsers);
			streamServer.getOutputStream().writeObject(sendMyInfoCommand);
			int statusServer = streamServer.getInputStream().readInt();
			Tools.interpretStatusServer( statusServer );

			streamServer.close();
			socketServer.close();
									
		} catch (IOException ex) {
			ex.printStackTrace();
			System.err.println("Error sharig information!!");
		} 
	}
}
