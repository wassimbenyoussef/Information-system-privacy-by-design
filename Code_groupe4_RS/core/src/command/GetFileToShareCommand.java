package command;

/**
 * GetFileToShareCommand This command contains the fileGID of the file requested 
 * 
 */
public class GetFileToShareCommand extends Command {

	private String fileGID;

	public GetFileToShareCommand(int numCommand, String fileGID) {
		super(numCommand);
		this.fileGID = fileGID;
	}

	public String getFileGID() {
		return fileGID;
	}

	public void setFileGID(String fileGID) {
		this.fileGID = fileGID;
	}

}
