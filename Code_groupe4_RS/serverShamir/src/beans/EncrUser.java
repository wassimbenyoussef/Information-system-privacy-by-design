package beans;

import java.io.Serializable;

public class EncrUser implements Serializable {

	private String encrUserGID;
	private String encrTCellIP;
	private String encrPort;
	private String encrPubKey;
	
	public EncrUser(String encrUserGID, String encrTCellIP, String encrPort, String encrPubKey) {
		super();
		this.encrUserGID = encrUserGID;
		this.encrTCellIP = encrTCellIP;
		this.encrPort = encrPort;
		this.encrPubKey = encrPubKey;
	}
	
	public String getEncrUserGID() {
		return encrUserGID;
	}
	
	public void setEncrUserGID(String encrUserGID) {
		this.encrUserGID = encrUserGID;
	}
	
	public String getEncrTCellIP() {
		return encrTCellIP;
	}
	
	public void setEncrTCellIP(String encrTCellIP) {
		this.encrTCellIP = encrTCellIP;
	}
	
	public String getEncrPubKey() {
		return encrPubKey;
	}
	
	public void setEncrPubKey(String encrPubKey) {
		this.encrPubKey = encrPubKey;
	}
	
	public String getEncrPort() {
		return encrPort;
	}
	
	public void setEncrPort(String encrPort) {
		this.encrPort = encrPort;
	}
	
}
