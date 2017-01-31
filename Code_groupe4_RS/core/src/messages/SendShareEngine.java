package messages;

import java.net.Socket;

import command.Command;
import command.SendShareEngineCommand;
import shamir.PVSSEngine;
import tools.Constants;
import tools.IOStreams;
import tools.Tools;

/**
 * SendShareEngine Command sends to the RS the engine used to split end combine the shamir keys
 * 
 * @author Groupe 4 - 2015-2016
 */
public class SendShareEngine {

	public static void sendShareEngine(int userID, PVSSEngine engine){

		try {
			
			//send the share engine to RS
			Socket socketServer = new Socket( "127.0.0.1", 8880);
			IOStreams streamServer = new IOStreams(socketServer);
			
			Command sendShareEngineCommand = new SendShareEngineCommand(Constants.CMD_SEND_SHARE_ENGINE, userID, engine);
			streamServer.getOutputStream().writeObject(sendShareEngineCommand);
			int statusServer = streamServer.getInputStream().readInt();
			Tools.interpretStatus( statusServer );

			streamServer.close();
			socketServer.close();
						
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println("Recovery Server not connected!");
		}
	}
}
