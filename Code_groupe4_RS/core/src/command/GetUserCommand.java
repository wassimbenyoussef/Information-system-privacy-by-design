package command;

/**
 *   GetUserCommand This command contains the userGID of the user requested 
 *  
 */
public class GetUserCommand extends Command {
	
	private int userGID;

	public GetUserCommand(int numCommand, int userGID) {
		super(numCommand);
		this.userGID = userGID;
	}

	public int getUserGID() {
		return userGID;
	}

	public void setUserGID(int userGID) {
		this.userGID = userGID;
	}
	
}