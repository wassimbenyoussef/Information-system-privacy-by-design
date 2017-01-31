package command;

public class SendRecoverRequestShamirCommand extends Command {
	private int[] contacts;
	private int userID;
	
	public SendRecoverRequestShamirCommand(int numCommand, int[] contacts, int userID) {
		super(numCommand);
		this.contacts = contacts;
		this.userID = userID;
	}

	public int[] getContacts() {
		return contacts;
	}

	public void setContacts(int[] contacts) {
		this.contacts = contacts;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	
}
