package beans;

import java.io.Serializable;

/**
 * FileStore records.
 * @author Majdi Ben Fredj,TORKHANI Rami
 *   
 */

public class FileStored implements Serializable {
	private FileDesc fileDesc;
	private String file;

	public FileStored(FileDesc fileDesc, String file) {
		super();
		this.fileDesc = fileDesc;
		this.file = file;
	}

	public FileDesc getFileDesc() {
		return fileDesc;
	}

	public void setFileDesc(FileDesc fileDesc) {
		this.fileDesc = fileDesc;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

}
