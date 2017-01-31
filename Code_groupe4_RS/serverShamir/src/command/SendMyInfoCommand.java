package command;

import java.util.List;

import beans.EncrUser;
import beans.MyInfo;

public class SendMyInfoCommand extends Command {

	private MyInfo myInfo;
	private List<EncrUser> encrUsers;
	
	public SendMyInfoCommand(int numCommand, MyInfo myInfo, List<EncrUser> encrUsers) {
		super(numCommand);
		this.myInfo = myInfo;
		this.encrUsers = encrUsers;
	}

	
	public MyInfo getMyInfo() {
		return myInfo;
	}


	public void setMyInfo(MyInfo myInfo) {
		this.myInfo = myInfo;
	}


	public List<EncrUser> getEncrUsers() {
		return encrUsers;
	}


	public void setEncrUsers(List<EncrUser> encrUsers) {
		this.encrUsers = encrUsers;
	}
	
}
