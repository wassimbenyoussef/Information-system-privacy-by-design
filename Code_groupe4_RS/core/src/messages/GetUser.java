package messages;

import java.net.Socket;

import tools.Constants;
import tools.IOStreams;
import beans.User;

import command.Command;
import command.GetUserCommand;

/**
 * ClientGetUser is able to send a GETUSER command to the TCell 
 * @author Majdi Ben Fredj,TORKHANI Rami 
 */
public class GetUser {

	public static User getUser(int userGID, User myInfo) {
		/**
		 * getUser
		 * @param userGID
		 * @param myInfo
		 */

		User user = null;
		try {
			/* socket connection to my TCELL */
			Socket socket = new Socket(myInfo.getTCellIP(), myInfo.getPort());
			IOStreams stream = new IOStreams(socket);

			/* get the user from the TCELL */
			Command getUserCommand = new GetUserCommand(Constants.CMD_GET_USER,
					userGID);
			stream.getOutputStream().writeObject(getUserCommand);
			user = (User) stream.getInputStream().readObject();

			stream.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}
}
