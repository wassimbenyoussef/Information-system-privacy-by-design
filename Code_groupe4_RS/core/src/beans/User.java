package beans;

import java.io.Serializable;

/**
 * User records.
 * 
 * @author Majdi Ben Fredj,TORKHANI Rami
 */
public class User implements Serializable {
	private int userGID;
	private String TCellIP;
	private String PubKey;
	private int port;

	public User(int userGID, String TCellIP) {
		this.userGID = userGID;
		this.TCellIP = TCellIP;
	}

	public User(int userGID, String TCellIP, String PubKey) {
		this.userGID = userGID;
		this.TCellIP = TCellIP;
		this.PubKey = PubKey;
	}

	public User(String tCellIP, int port) {
		this.TCellIP = tCellIP;
		this.port = port;
	}
	
	public User(String PubKey) {
		this.PubKey = PubKey;
	}

	public User(int userGID, String tCellIP, int port, String pubKey) {
		this.userGID = userGID;
		this.TCellIP = tCellIP;
		this.PubKey = pubKey;
		this.port = port;
	}

	public int getUserGID() {
		return userGID;
	}

	public void setUserGID(int userGID) {
		this.userGID = userGID;
	}

	public String getTCellIP() {
		return TCellIP;
	}

	public void setTCellIP(String tCellIP) {
		TCellIP = tCellIP;
	}

	public String getPubKey() {
		return PubKey;
	}

	public void setPubKey(String pubKey) {
		PubKey = pubKey;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
