package dao;

import java.math.BigInteger;

//import java.io.PrintWriter;
//import java.util.ArrayList;
//import java.util.List;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import beans.EncrUser;
import beans.FileDesc;
import beans.MyInfo;
import shamir.PVSSEngine;
import shamir.PublicInfo;
import tools.Constants;

/**
 * 
 * @author Majdi Ben Fredj
 * 
 *  * the TCell DB Manager.

 */
public class TcellDAO{

	private static TcellDAO instance = null;
	
	Connection c = null;
	
	int idFile = 0;
	int idUserInfo = 0;
	int idUserContacts = 0;
	int idEngine = 0;
	
	// Renvoyer une instance de TcellDAOToken
		public static TcellDAO getInstance() {
			/**
			 * TcellDAOToken Creates a TCELL_DB instance
			 * @param ignoreLoadQEP
			 */

			if (instance == null) {
				synchronized (TcellDAO.class) {
					if (instance == null) {
						instance = new TcellDAO();
					}
				}
			}
			return instance;
		}

	//Load JDBC driver, get a connexion, without installing execution plans
	
	public TcellDAO() {
		try {
			Class.forName("org.sqlite.JDBC");
	        c = DriverManager.getConnection("jdbc:sqlite:testShamir.db");
	        System.out.println("Opened database successfully ");
			idFile = this.CountFileRows();
			idUserInfo = this.CountUserInfoRows();
			idUserContacts = this.CountUserContactsRows();
			idEngine = this.CountEngineRows();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Count the number of rows of FILE table
	 */
	public int CountFileRows() {
		Statement stmt = null;
		int result = 0;
		
		try {
			stmt = c.createStatement();
			
	        String query = "SELECT COUNT(*) AS nbr FROM FILE";
	        ResultSet rs = stmt.executeQuery(query);
	        
	        
	        while (rs.next()) {
				int nbr = rs.getInt("nbr");
				result = nbr;
			}
			
			rs.close();
	        stmt.close();
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Count the number of rows of USERINFO table
	 */
	public int CountUserInfoRows() {
		Statement stmt = null;
		int result = 0;
		
		try {
			stmt = c.createStatement();
			
	        String query = "SELECT COUNT(*) AS nbr FROM USERINFO";
	        ResultSet rs = stmt.executeQuery(query);
	        
	        
	        while (rs.next()) {
				int nbr = rs.getInt("nbr");
				result = nbr;
			}
			
			rs.close();
	        stmt.close();
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Count the number of rows of USERCONTTCS table
	 */
	public int CountUserContactsRows() {
		Statement stmt = null;
		int result = 0;
		
		try {
			stmt = c.createStatement();
			
	        String query = "SELECT COUNT(*) AS nbr FROM USERCONTACTS";
	        ResultSet rs = stmt.executeQuery(query);
	        
	        
	        while (rs.next()) {
				int nbr = rs.getInt("nbr");
				result = nbr;
			}
			
			rs.close();
	        stmt.close();
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	/**
	 * Count the number of rows of USER table
	 */
	public int CountEngineRows() {
		Statement stmt = null;
		int result = 0;
		
		try {
			stmt = c.createStatement();
			
	        String query = "SELECT COUNT(*) AS nbr FROM SHAREENGINE";
	        ResultSet rs = stmt.executeQuery(query);
	        
	        
	        while (rs.next()) {
				int nbr = rs.getInt("nbr");
				result = nbr;
			}
			
			rs.close();
	        stmt.close();
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public void DropTables() throws SQLException {
		/**
		 * Drop the tables of DB.
		 */
		Statement stmt = null;
		
		stmt = c.createStatement();
        String sql = "DROP TABLE FILE; DROP TABLE USERINFO; DROP TABLE USERCONTACTS; DROP TABLE SHAREENGINE";
        stmt.executeUpdate(sql);
        
        System.out.println("Executing query : " + sql);	
        
        stmt.close();
	}

	/**
	 * Creates the tables in DB.
	 * 
	 */
	public void CreateTables() {
		Statement stmt = null;
		try {
			stmt = c.createStatement();
	        String sql = "CREATE TABLE FILE " +
	                     "(ID INT PRIMARY KEY     NOT NULL," +
	                     " UserID         INT     NOT NULL, "+ //user ID
	                     " GID            TEXT    NOT NULL, "+ //UserID|filename
	                     " SKey           TEXT    NOT NULL, "+ //secret key for encrypting the file (encrypted)
	                     " IV             TEXT    NOT NULL, "+ //iv used for encrypting the file (encrypted)
	                     " COMMAND        TEXT    NOT NULL);"+ //command coming from
	                     "CREATE TABLE USERINFO " +
	                     "(ID INT PRIMARY KEY     NOT NULL, " +
	                     " UserID         INT     NOT NULL, " + //user ID
	                     " TcellIP        TEXT    NOT NULL, " + //user IP
	                     " Port           INT     NOT NULL, " + //user port
	                     " PubKey         TEXT    NOT NULL);" + //user pubKey
	                     "CREATE TABLE USERCONTACTS " +
	                     "(ID INT PRIMARY KEY     NOT NULL, " +
	                     " UserID         INT     NOT NULL, " + //user ID
	                     " CUserID        TEXT    NOT NULL, " + //contact ID (encrypted)
	                     " CTcellIP       TEXT    NOT NULL, " + //contact IP (encrypted)
	                     " CPort          TEXT    NOT NULL, " + //contact port (encrypted)
	                     " CPubKey        TEXT    NOT NULL);" + //contact pubKey (encrypted)
	                     "CREATE TABLE SHAREENGINE " +
	                     "(ID INT PRIMARY  KEY     NOT NULL, " +
	                     " UserID          INT     NOT NULL, " + //user ID
	                     " Nb_available    INT     NOT NULL, " + //Share Engine attribute
	                     " Nb_required     INT     NOT NULL, " + //Share Engine attribute
	                     " GroupPrimeOrder TEXT    NOT NULL, " + //Share Engine attribute
	                     " Generator1      TEXT    NOT NULL, " + //Share Engine attribute
	                     " Generator2      TEXT    NOT NULL);";  //Share Engine attribute
	        
	        stmt.executeUpdate(sql);
	        
	        System.out.println("Executing query : " + sql);	
	        
		    stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * Inserts in the FILE table
	 * @param userID the user id
	 * @param gid the UserID concatenated with the file name
	 * @param skey the secret key used for the the file encryption
	 * @param iv the iv used for the the file encryption
	 * @param command the filename command
	 * @throws Exception
	 */
	public void insertFile(int userID, String gid, String SKey, String iv, String command) {
		Statement stmt = null;
		try {

			stmt = c.createStatement();
			String sql = "INSERT INTO FILE VALUES ("+idFile+", "+userID+", '"+gid+"', '"+SKey+"', '"+iv+"', '"+command+"')";
			stmt.executeUpdate(sql);
			
			System.out.println("Executing query : " + sql);	
			
			stmt.close();
		    idFile++;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Inserts in the USERINFO table
	 * @param userID the user id
	 * @param TcellIP user ip
	 * @param port the user port
	 * @throws Exception
	 */
	public void insertUserInfo(MyInfo user) {
		Statement stmt = null;
		try {

			int userID = user.getMyGid();
			String TcellIP = user.getMyTcellIp();
			int port = user.getMyTcellPort();
			String pubKey = user.getMyPubKey();
			
			stmt = c.createStatement();
			String sql = "INSERT INTO USERINFO VALUES ("+idUserInfo+", "+userID+", '"+TcellIP+"', "+port+", '"+pubKey+"')";
			stmt.executeUpdate(sql);
			
			System.out.println("Executing query : " + sql);	
			
			stmt.close();
		    idUserInfo++;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Inserts in the USERCONTACTS table
	 * @param userID the user id
	 * @param user contact
	 * @throws Exception
	 */
	public void insertUserContacts(int userID, EncrUser encrUser) {
		Statement stmt = null;
		try {
			 
			String cUserID = encrUser.getEncrUserGID(); 
			String cTcellIP = encrUser.getEncrTCellIP();
			String cPort = encrUser.getEncrPort();
			String cPubKey = encrUser.getEncrPubKey();
			
			stmt = c.createStatement();
			String sql = "INSERT INTO USERCONTACTS VALUES ("+idUserContacts+", "+userID+", '"+cUserID+"', '"+cTcellIP+"', '"+cPort+"', '"+cPubKey+"')";
			stmt.executeUpdate(sql);
			
			System.out.println("Executing query : " + sql);	
			
			stmt.close();
		    idUserContacts++;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Inserts in the SHAREENGINE table
	 * @param userID the user id
	 * @param engine the share engine
	 * @throws Exception
	 */
	public void insertShareEngine(int userID, PVSSEngine engine) {
		Statement stmt = null;
		try {
			
			int nb_available= engine.getPublicInfo().getN();
			int nb_required = engine.getPublicInfo().getT();
			String groupPrimeOrder = engine.getPublicInfo().getGroupPrimeOrder().toString();
			String generator1 = engine.getPublicInfo().getGeneratorg().toString();
			String generator2 = engine.getPublicInfo().getGeneratorG().toString();
			
			stmt = c.createStatement();
			String sql = "INSERT INTO SHAREENGINE VALUES ("+idEngine+", "+userID+", "+nb_available+", "+nb_required+", '"+groupPrimeOrder+"', '"+generator1+"', '"+generator2+"')";
			stmt.executeUpdate(sql);
			
			System.out.println("Executing query : " + sql);	
			
			stmt.close();
		    idEngine++;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Deletes a file in the db
	 * @param fileGID the gid of the file
	 * @throws Exception
	 */
	public void deleteByGID(String gid) {
		
		Statement stmt = null;
		
		try {
			stmt = c.createStatement();
			
			String query = "DELETE FROM FILE WHERE GID = '"+ gid + "'";
			stmt.executeUpdate(query);
			
			System.out.println("Executing query : " + query);		

			stmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * SELECT FILEID, SECRETKEY, IV FROM FILE WHERE FILEGID = 'fileGID';
	 * @param fileGID the file ID
	 * @return FileDesc 
	 *    The File description <FILEID, SECRETKEY, IV>
	 */
	public FileDesc getFileDescByGid(String fileGID) {
		
		FileDesc result = null;
		Statement stmt = null;
		
		try {
			stmt = c.createStatement();

			String query = "SELECT SKey, IV FROM FILE WHERE GID ='"+ fileGID +"'";
			ResultSet rs = stmt.executeQuery(query);
			
			System.out.println("Executing query : " + query);
			
			String fileID = Constants.RS_PATH + fileGID;
			
			while (rs.next()) {
				String SKey = rs.getString(1);
				String iv = rs.getString(2);
				result = new FileDesc(fileGID,fileID, SKey, iv,"","");
			}
			
			rs.close();
			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	/**
	 * SELECT GID FROM FILE WHERE UserID = 'userID';
	 * @return gid 
	 */
	public ArrayList<String> getGidByUserID(int userID) {

		ArrayList<String> result = new ArrayList<String>();
		Statement stmt = null;
		
		try {
			stmt = c.createStatement();
			
			String query = "SELECT GID FROM FILE WHERE UserID = '"+ userID +"'";
			ResultSet rs = stmt.executeQuery(query);
			
			System.out.println("Executing query : " + query);			

			
			while (rs.next()) {
				String gid = rs.getString("gid");
				result.add(gid);
			}
			
			rs.close();
			stmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	/**
	 * SELECT TcellIP, Port, PubKey FROM USERINFO WHERE UserID = ' userID '
	 * @return Tcellip 
	 */
	public MyInfo getUserByID(int userID) {

		MyInfo result = null;
		
		Statement stmt = null;
		
		try {
			stmt = c.createStatement();
			
			String query = "SELECT TcellIP, Port, PubKey FROM USERINFO WHERE UserID = '"+ userID +"';";
			ResultSet rs = stmt.executeQuery(query);
			
			System.out.println("Executing query : " + query);			

			
			while (rs.next()) {
				String tcellIP = rs.getString("TcellIP");
				int port = rs.getInt("Port");
				String pubKey = rs.getString("PubKey");
				result = new MyInfo(userID, tcellIP, port, pubKey, null);
			}
			
			rs.close();
			stmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	/**
	 * SELECT CUserID, CTcellIP, CPort, CPubKey FROM USERCONTACTS WHERE UserID = ' userID '
	 * @return port 
	 */
	public List<EncrUser> getContactsByUserID(int userID) {

		List<EncrUser> result = new ArrayList<>();
		
		Statement stmt = null;
		
		try {
			stmt = c.createStatement();
			String query = "SELECT CUserID, CTcellIP, CPort, CPubKey FROM USERCONTACTS WHERE UserID = '"+ userID +"';";
			ResultSet rs = stmt.executeQuery(query);
			
			System.out.println("Executing query : " + query);			

			
			while (rs.next()) {
				String cUserID = rs.getString("CUserID");
				String cTcellIP = rs.getString("CTcellIP");
				String cPort = rs.getString("CPort");
				String cPubKey = rs.getString("CPubKey");
				
				EncrUser encrUser = new EncrUser(cUserID, cTcellIP, cPort, cPubKey);
				result.add(encrUser);
			}
			
			rs.close();
			stmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	/**
	 * SELECT SKey FROM FILE WHERE GID = ' gid '
	 * @return SKey
	 */
	public String getSKeyByGID(String gid) {

		String result = null;
		
		Statement stmt = null;
		
		try {
			stmt = c.createStatement();
			
			String query = "SELECT SKey FROM FILE WHERE GID = '"+ gid +"'";
			ResultSet rs = stmt.executeQuery(query);
			
			System.out.println("Executing query : " + query);			

			
			while (rs.next()) {
				String SKey = rs.getString("SKey");
				result = SKey;
			}
			
			rs.close();
			stmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	/**
	 * SELECT Nb_available, Nb_required, GroupPrimeOrder, Generator1, Generator2 FROM SHAREENGINE WHERE USERID = ' userid '
	 * @return iv
	 */
	public PVSSEngine getEngineByUserID(int userID) {

		PVSSEngine result = null;
		
		Statement stmt = null;
		
		try {
			stmt = c.createStatement();
			
			String query = "SELECT Nb_available, Nb_required, GroupPrimeOrder, Generator1, Generator2 FROM SHAREENGINE WHERE USERID = '"+ userID +"'";
			ResultSet rs = stmt.executeQuery(query);
			
			System.out.println("Executing query : " + query);			

			
			while (rs.next()) {
				int nb_available= rs.getInt(1); 
				int nb_required = rs.getInt(2);
				BigInteger groupPrimeOrder = new BigInteger(rs.getString(3));
				BigInteger generator1 = new BigInteger(rs.getString(4));
				BigInteger generator2 = new BigInteger(rs.getString(5));
				
				PublicInfo pi = new PublicInfo(nb_available, nb_required, groupPrimeOrder, generator1, generator2);
				PVSSEngine engine = new PVSSEngine(pi);
				
				result = engine;
			}
			
			rs.close();
			stmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	/**
	 * SELECT  FROM FILE WHERE GID = ' gid '
	 * @return iv
	 */
	public String getIVByGID(String gid) {

		String result = null;
		
		Statement stmt = null;
		
		try {
			stmt = c.createStatement();
			
			String query = "SELECT IV FROM FILE WHERE GID = '"+ gid +"'";
			ResultSet rs = stmt.executeQuery(query);
			
			System.out.println("Executing query : " + query);			

			
			while (rs.next()) {
				String iv = rs.getString("IV");
				result = iv;
			}
			
			rs.close();
			stmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	/**
	 * check a file exist
	 * @param gid the gid of the file to check
	 * @throws Exception
	 */
	public boolean isGIDexists(String gid) {
		boolean result = false;

		Statement stmt = null;
		
		try {
			stmt = c.createStatement();
			
			String query = "SELECT ID, GID FROM FILE WHERE GID = '" + gid +"'";
			ResultSet rs = stmt.executeQuery(query);
			
			System.out.println("Executing query : " + query);			

			
			if (rs.next()) {
				result = true;
			} else {
				result = false;
			}
			
			rs.close();
			stmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * check a user exist
	 * @param userid the userid of the user to check
	 * @throws Exception
	 */
	public boolean isUserInfoExists(int userid) {
		boolean result = false;

		Statement stmt = null;
		
		try {
			stmt = c.createStatement();
			
			String query = "SELECT ID, USERID FROM USERINFO WHERE USERID = '" + userid +"'";
			ResultSet rs = stmt.executeQuery(query);
			
			System.out.println("Executing query : " + query);			

			
			if (rs.next()) {
				result = true;
			} else {
				result = false;
			}
			
			rs.close();
			stmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	/**
	 * check a user exist
	 * @param userid the userid of the user to check
	 * @throws Exception
	 */
	public boolean isUserContactsExists(int userid, String cUserid) {
		boolean result = false;

		Statement stmt = null;
		
		try {
			stmt = c.createStatement();
			
			String query = "SELECT ID, USERID, CUSERID FROM USERCONTACTS WHERE USERID = '" + userid +"' AND CUSERID = '" + cUserid +"'";
			ResultSet rs = stmt.executeQuery(query);
			
			System.out.println("Executing query : " + query);			

			
			if (rs.next()) {
				result = true;
			} else {
				result = false;
			}
			
			rs.close();
			stmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	/**********************************************Log data****************************************************************/
	public void printFile() {
		Statement stmt = null;
		try {
			stmt = c.createStatement();
			String query = "SELECT * FROM FILE";
			ResultSet rs = stmt.executeQuery(query);
			
			
			System.out.println("Executing query : " + query);			

			 while ( rs.next() ) {
		           int id = rs.getInt("id");
		           int userid  = rs.getInt("userid");
		           String  gid = rs.getString("gid");
		           String  SKey = rs.getString("SKey");
		           String  iv = rs.getString("IV");
		           String  command = rs.getString("command");
		           System.out.println( "ID = " + id );
		           System.out.println( "UserID = " + userid );
		           System.out.println( "GID = " + gid );
		           System.out.println( "SKey = " + SKey );
		           System.out.println( "IV = " + iv );
		           System.out.println( "Command = " + command );
		           System.out.println();
		        }
			 rs.close();
		     stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void printUserInfo() {
		Statement stmt = null;
		try {
			stmt = c.createStatement();
			String query = "SELECT * FROM USERINFO";
			ResultSet rs = stmt.executeQuery(query);
			
			
			System.out.println("Executing query : " + query);			

			 while ( rs.next() ) {
		           int id = rs.getInt("id");
		           int userid  = rs.getInt("userid");
		           String tcellIP = rs.getString("TcellIP");
		           int port = rs.getInt("Port");
		           String pubKey = rs.getString("PubKey");
		           System.out.println( "ID = " + id );
		           System.out.println( "UserID = " + userid );
		           System.out.println( "TcellIP = " + tcellIP );
		           System.out.println( "Port = " + port );
		           System.out.println( "PubKey = " + pubKey );
		           System.out.println();
		        }
			 rs.close();
		     stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void printUserContacts() {
		Statement stmt = null;
		try {
			stmt = c.createStatement();
			String query = "SELECT * FROM USERCONTACTS";
			ResultSet rs = stmt.executeQuery(query);
			
			
			System.out.println("Executing query : " + query);			

			 while ( rs.next() ) {
		           int id = rs.getInt("id");
		           int userid  = rs.getInt("UserID");
		           String cUserid  = rs.getString("CUserid");
		           String cTcellIP = rs.getString("CTcellIP");
		           String cPort = rs.getString("CPort");
		           String cPubKey = rs.getString("CPubKey");
		           System.out.println( "ID = " + id );
		           System.out.println( "UserID = " + userid );
		           System.out.println( "cUserID = " + cUserid );
		           System.out.println( "cTcellIP = " + cTcellIP );
		           System.out.println( "cPort = " + cPort );
		           System.out.println( "cPubKey = " + cPubKey );
		           System.out.println();
		        }
			 rs.close();
		     stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*************************************************************************************************************/

	

	private void closeConnection() {
		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
