package command;

import shamir.PVSSEngine;

public class SendShareEngineCommand extends Command {

	private int userID;
	private PVSSEngine engine;
	
	public SendShareEngineCommand(int numCommand, int userID, PVSSEngine engine) {
		super(numCommand);
		this.userID = userID;
		this.engine = engine;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public PVSSEngine getEngine() {
		return engine;
	}

	public void setEngine(PVSSEngine engine) {
		this.engine = engine;
	}
	
	
}
