package command;

import shamir.PVSSEngine;
import shamir.Share;

public class ShareKeyCommand extends Command {
	private int userID;
	private Share part;

	public ShareKeyCommand(int numCommand, int userID, Share part) {
		super(numCommand);
		this.userID = userID;
		this.part = part;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public Share getPart() {
		return part;
	}

	public void setPart(Share part) {
		this.part = part;
	}
}
