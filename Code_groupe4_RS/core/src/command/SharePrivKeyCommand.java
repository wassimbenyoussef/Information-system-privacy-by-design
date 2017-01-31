package command;

public class SharePrivKeyCommand extends Command{

	private int[] receivers;
	
	public SharePrivKeyCommand(int numCommand, int[] receivers) {
		super(numCommand);
		this.receivers = receivers;
	}

	public int[] getReceivers() {
		return receivers;
	}

	public void setReceivers(int[] receivers) {
		this.receivers = receivers;
	}
	
	
}
