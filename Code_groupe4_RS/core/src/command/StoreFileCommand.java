package command;

/**
 * StoreFileCommand  This command contains the sKey,iv,filePath,file 
 *
 */
public class StoreFileCommand extends Command {

	// store
	private String sKey;
	private String iv;
	private String filePath;
	private String file;

	public StoreFileCommand(int numCommand, String sKey, String iv, String filePath, String file) {
		super(numCommand);
		this.sKey = sKey;
		this.iv = iv;
		this.filePath = filePath;
		this.file = file;
	}

	public String getsKey() {
		return sKey;
	}

	public void setsKey(String sKey) {
		this.sKey = sKey;
	}

	public String getIv() {
		return iv;
	}

	public void setIv(String iv) {
		this.iv = iv;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

}
