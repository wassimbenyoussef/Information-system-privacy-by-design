package command;

public class SendRecoverRequestFileCommand extends Command {
	private String privPath;
	private int userID;
	
	public SendRecoverRequestFileCommand(int numCommand, String privPath, int userID) {
		super(numCommand);
		this.privPath = privPath;
		this.userID = userID;
	}

	public String getPrivPath() {
		return privPath;
	}

	public void setPrivPath(String privPath) {
		this.privPath = privPath;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	
}
	