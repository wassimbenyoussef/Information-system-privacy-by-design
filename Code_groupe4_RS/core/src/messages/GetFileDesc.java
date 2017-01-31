/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package messages;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import tools.Constants;
import tools.IOStreams;
import beans.FileDesc;
import beans.User;

import command.GetAllFilesDescCommand;

/**
 * GetFileDesc provides the method to ask for the list of all the files' descriptions stored on a TC. 
 * A file description consists of a fileGID, type and description.
 * 
 * @author tranvan
 */
public class GetFileDesc 
{

	/**
	 * Get the files' descriptions
	 * @return the list of the files' description, as a String
	 */
	
	public static ArrayList<String> getFileDesc(User user) {
		ArrayList<String> filesList = new ArrayList<String>();

		String TCellIP = user.getTCellIP();
		int TcellPort = user.getPort();

		try {
			Socket socket = new Socket(TCellIP, TcellPort);
			/* Creation of the stream */
			IOStreams stream = new IOStreams(socket);

			/* Send the command */

			GetAllFilesDescCommand getFileDescCmd = new GetAllFilesDescCommand(Constants.CMD_GET_FILES_DESC);
			stream.getOutputStream().writeObject(getFileDescCmd);

			int status = stream.getInputStream().readInt();

			if (status == Constants.OK) {
				List<FileDesc> listFilesDesc = (List<FileDesc>) stream.getInputStream().readObject();
				System.out.println("Here is the description of the "+ listFilesDesc.size() + " files stored in TCell : ");
				for (FileDesc fileDesc : listFilesDesc) {
					System.out.println("FileGID : " + fileDesc.fileGID + " Type : " + fileDesc.type + " Description : " + fileDesc.descr);
					filesList.add(fileDesc.fileGID);
				}

			} else {
				System.err.println("No file found");
			}

		} catch (Exception ex) {
			System.err.println("An error occured");
		}

		return filesList;
	}	
	
	
	
	



}
