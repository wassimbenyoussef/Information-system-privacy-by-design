package beans;

import java.io.Serializable;

/**
 * FileDesc records.
 * 
 * @author Athanasia Katsouraki
 * 
 */

public class FileDesc implements Serializable {
	public String fileGID;
	public String fileID;
	public String sKey;
	public String iv;
	public String type;
	public String descr;


	public FileDesc(String fileGID, String fileID, String sKey, String iv, String type, String descr)
	{
		this.fileGID=fileGID;
		this.fileID=fileID;
		this.sKey=sKey;
		this.iv = iv;
		this.type=type;
		this.descr=descr;            
	}

	
	public FileDesc(String fileID,String sKey, String iv, String descr){
		this.fileID=fileID;
		this.sKey=sKey;
		this.iv=iv;
	}
	
	
	public FileDesc(String fileGID,String type, String descr){
		this.fileGID=fileGID;
		this.type=type;      
		this.descr = descr;
	}
	
}


