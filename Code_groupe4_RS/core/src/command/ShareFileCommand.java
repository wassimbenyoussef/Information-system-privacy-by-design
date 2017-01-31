package command;

import beans.FileStored;
/**
 * ShareFileCommand This command contains the fileToShare which itself 
 * contains the file and the FILEDESC and the source (from tcell or from RS)
 * *  */
public class ShareFileCommand extends Command {

	private FileStored fileToShare;
	private String source;
	private String privateKey;
	// shareFile
	
	public ShareFileCommand(int numCommand, FileStored fileToShare, String source, String privateKey) {
		super(numCommand);
		this.fileToShare = fileToShare;
		this.source = source;
		this.privateKey = privateKey;
	}

	public FileStored getFileToShare() {
		return fileToShare;
	}

	public void setFileToShare(FileStored fileToShare) {
		this.fileToShare = fileToShare;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKeyPath) {
		this.privateKey = privateKeyPath;
	}

}
