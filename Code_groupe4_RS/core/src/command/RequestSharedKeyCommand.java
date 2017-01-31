package command;

public class RequestSharedKeyCommand extends Command {
	private int userID;

	public RequestSharedKeyCommand(int numCommand, int userID) {
		super(numCommand);
		this.userID = userID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	
}
