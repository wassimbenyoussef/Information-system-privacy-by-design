package messages;

import java.net.ConnectException;
import java.net.Socket;
import java.util.Vector;

import beans.MyInfo;
import command.Command;
import command.RequestSharedKeyCommand;
import shamir.Share;
import tools.Constants;
import tools.IOStreams;

public class RequestSharedKey {
	public static void requestSharedKey(int userID, Vector<Share> partVector, MyInfo user){

		try {
			String userTCellIP = user.getMyTcellIp();
			int userPort = user.getMyTcellPort();
			
			Socket socket = null;
			
			try{
				socket = new Socket( userTCellIP, userPort);
			}
			catch(ConnectException e){
				System.err.println("User " + userID + " unavailable\n");
				e.printStackTrace();
			}
			catch(NullPointerException e){
				System.err.println("User " + userID +" unavailable\n");
				e.printStackTrace();
			}
			
			IOStreams stream = new IOStreams(socket);
			
			// send the share command to the recipient TCELL
			Command requestShareKeyCommand = new RequestSharedKeyCommand(Constants.CMD_REQUEST_SHARED_KEY, userID);
			stream.getOutputStream().writeObject(requestShareKeyCommand);
			
			Share received = (Share) stream.getInputStream().readObject();
			System.out.println("------ " + received.toString());
			try{
				partVector.add(received.getIndex(), received);
			}
			catch (ArrayIndexOutOfBoundsException e){
				partVector.setSize(received.getIndex());
				partVector.add(received.getIndex(), received);
			}
		
			
			System.out.println("Part read!!");
			
			stream.close();
			socket.close();
									
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println("Error sharig information!!");
		} 
	}
}
