package command;

import java.io.Serializable;


/**
 * Class Command ,class used to instantiate an order 
 *  @Example :
 * 			  0:STORE_FILE
 * 			  1:SHARE_FILE
 * 			  2:GETFILEDESC
 * 			  3:READFILE
 * 			  4:GETFILETOSHARE
 *            5:GETUSER
 */
public class Command implements Serializable {
	protected int numCommand;

	public Command(int numCommand) {
		this.numCommand = numCommand;
	}

	public int getNumCommand() {
		return numCommand;
	}

	public void setNumCommand(int numCommand) {
		this.numCommand = numCommand;
	}

}