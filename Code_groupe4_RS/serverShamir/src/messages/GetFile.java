package messages;

import java.net.Socket;

import tools.Constants;
import tools.IOStreams;
import beans.FileStored;
import beans.User;
import command.Command;
import command.GetFileToShareCommand;

/**
 * ClientGetFile is able to send a GETFILE command to the TCell 
 * @author Majdi Ben Fredj,TOKRHNANI Rami
 */
public class GetFile {

	public static FileStored getFile(String fileGID, User myInfo) {
		/**
		 * getFile
		 * @param fileGID
		 * @param myInfo
		 */

		FileStored fileToShare = null;

		try {
			/* socket connection to my TCELL */
			Socket socket = new Socket(myInfo.getTCellIP(), myInfo.getPort());
			IOStreams stream = new IOStreams(socket);

			/* get the file from the TCELL */
			Command getFileToShareCommand = new GetFileToShareCommand(
					Constants.CMD_GET_FILE_TO_SHARE, fileGID);
			stream.getOutputStream().writeObject(getFileToShareCommand);
			fileToShare = (FileStored) stream.getInputStream().readObject();
			
			stream.close();
			socket.close();
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return fileToShare;

	}

}
