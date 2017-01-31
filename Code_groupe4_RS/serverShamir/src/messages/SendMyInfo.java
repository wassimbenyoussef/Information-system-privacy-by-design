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
			Socket socket = new Socket(myInfo.getMyTcellIp(), myInfo.getMyTcellPort());
			// Creation of the stream
			IOStreams stream = new IOStreams(socket);
			
			// send the myinfo command to the recipient TCELL
			Command sendMyInfoCommand = new SendMyInfoCommand(Constants.CMD_SEND_MYINFO, myInfo, encrUsers);
			stream.getOutputStream().writeObject(sendMyInfoCommand);
			int status = stream.getInputStream().readInt();
			Tools.interpretStatus( status );

			stream.close();
			socket.close();
									
		} catch (IOException ex) {
			ex.printStackTrace();
			System.err.println("Error sharig information!!");
		} 
	}
}
