package messages;

import java.net.Socket;

import tools.Constants;
import tools.IOStreams;
import tools.Tools;
import beans.FileRead;
import beans.User;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import command.Command;
import command.ReadFileCommand;

import configuration.Configuration;

/**
 * ReadFile provides the method to ask for a decrypted file to a TC. 
 * @author tranvan
 */
public class ReadFile 
{

	/**
	 * readfile Reads the file described by a fileGID 
	 * @param FileGID the global ID of the file 
	 */
	public static void readFile(Boolean IsTcell, String FileGID, User user) 
	{

		String TCellIP = user.getTCellIP();
		int TcellPort = user.getPort();
		
			try 
			{
				Socket socket = new Socket( TCellIP, TcellPort );
				/* Creation of the stream */
				IOStreams stream = new IOStreams( socket );

				/* Send the command */
				Command readFileCmd = new ReadFileCommand(Constants.CMD_READ_FILE, FileGID);
				stream.getOutputStream().writeObject(readFileCmd);

					FileRead file = (FileRead) stream.getInputStream().readObject();
					
										
					if (IsTcell){
						file.setFilePath(Configuration.getConfiguration().getProperty("tcellPath") + Tools.getFileName(file.getFilePath()));
					}else{
						file.setFilePath(Configuration.getConfiguration().getProperty("appHomePath") + Tools.getFileName(file.getFilePath()));;
					}
					Tools.writeFileFromPath( file.getFilePath(), Base64.decode(file.getFile()));// à vérifier(base64.decode ??)
					System.out.println("File available at " + file.getFilePath());
				
				
				stream.close();

			} catch (Exception ex) {
				System.err.println("This file cannot be read");
			}
	}
}

