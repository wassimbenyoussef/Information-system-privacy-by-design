package command;

/**
 * ReadFileCommand This command contains the fileGID of the FILEDESC requested
 *
 */
public class ReadFileCommand extends Command {

	//readFile
    private String fileGID;
    
    
	public ReadFileCommand(int numCommand, String fileGID) {
		super(numCommand);
		this.fileGID=fileGID;
	}


	public String getFileGID() {
		return fileGID;
	}


	public void setFileGID(String fileGID) {
		this.fileGID = fileGID;
	}

}
