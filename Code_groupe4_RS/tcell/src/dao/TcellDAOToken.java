package dao;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.inria.database.QEPng;

import test.jdbc.Tools;
import beans.FileDesc;
import beans.MyInfo;
import beans.User;
import configuration.Configuration;

import shamir.Share;

/**
 * 
 * @author Majdi Ben Fredj
 * 
 *  * the TCell DB Manager.

 */
public class TcellDAOToken extends Tools{
	private static int fileIdGlobal=0;

	private static TcellDAOToken instance = null;
	
	
	// Renvoyer une instance de TcellDAOToken
		public static TcellDAOToken getInstance(boolean ignoreLoadQEP) {
			/**
			 * TcellDAOToken Creates a TCELL_DB instance
			 * @param ignoreLoadQEP
			 */

			if (instance == null) {
				synchronized (TcellDAOToken.class) {
					if (instance == null) {
						instance = new TcellDAOToken(ignoreLoadQEP);
					}
				}
			}
			return instance;
		}

	//Load JDBC driver, get a connexion, without installing execution plans
	
	public TcellDAOToken(boolean ignoreLoadQEP) {
		try {
			out = new PrintWriter(java.lang.System.out);
		    init();
			openConnection(Configuration.getConfiguration().getProperty("dbPath"), null);
			
			((org.inria.jdbc.Connection) db).bypassInitialization();
			
			Class<?>[] executionPlans = new Class[] { QEP.class };
			QEPng.loadExecutionPlans(TCell_QEP_IDs.class, executionPlans);
			if(!ignoreLoadQEP){
				QEPng.installExecutionPlans(db);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}




	public void DropTables() {
		/**
		 * Drop the tables of DB.
		 */
		try {
			Desinstall_DBMS_MetaData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void CreateTables() {
		/**
		 * Creates the tables in DB.
		 * 
		 */
		try {
			String schema = Schema.META;
			Install_DBMS_MetaData(schema.getBytes(),0);

			// load and install QEPs
			Class<?>[] executionPlans = new Class[] { QEP.class };
			QEPng.loadExecutionPlans(TCell_QEP_IDs.class, executionPlans);
			QEPng.installExecutionPlans(db);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Inserts a file's description
	 * 
	 * @param string
	 *            the global id of the file
	 * @param fileID
	 *            the file id of the file
	 * @param sKey
	 *            the secret key used to encrypt the file
	 * @param iv
	 *            the initialization vector
	 * @param type
	 *            the type of the file (store or share)
	 * @param descr
	 *            the description of the file
	 * @throws Exception
	 */
	public void insertFileDesc(String fileGID, String fileID, String sKey, String iv, String type, String descr) {

		try {
			java.sql.PreparedStatement ps = ((org.inria.jdbc.Connection) db).prepareStatement(TCell_QEP_IDs.QEP.EP_insertFileDesc);
			ps.setInt(1, fileIdGlobal++);
			ps.setString(2, fileGID);
			ps.setString(3, fileID);
			ps.setString(4, sKey);
			ps.setString(5, iv);
			ps.setString(6, type);
			ps.setString(7, descr);
			ps.executeUpdate();
			
			String query= "INSERT INTO File  VALUES ("+ fileGID+","+fileID+","+sKey+","+iv+","+type+","+descr+")";
			System.out.println("Executing query : " + query);			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Uncomment when the close function will be implemented
			// attemptToClose(ps);
		}

	}
	/**
	 * Inserts a user in the User table
	 * @param UserGID the user id
	 * @param TCellIP the TC's IP of the user
	 * @param PubKey the public key of the user
	 * @param tcellPort the TC's PORT of user
	 * @throws Exception
	 */

	public void insertUser(int userGID, String tcellIP, int tcellPort,String pubKey) {
		try {
			
			java.sql.PreparedStatement ps = ((org.inria.jdbc.Connection) db).prepareStatement(TCell_QEP_IDs.QEP.EP_insertUser);

			ps.setInt(1, userGID);
			ps.setString(2, tcellIP);
			ps.setInt(3, tcellPort);
			
			//convert String to blob
			Blob pubKeyBlob = db.createBlob();
			pubKeyBlob.setBytes(1, pubKey.getBytes());
			ps.setBlob(4, pubKeyBlob);
			
			ps.setNull(5, Types.CHAR);
			
			int a=ps.executeUpdate();
			System.out.println(a + "utilisateur est ajouté");
			String query= "INSERT INTO User  VALUES ("+userGID+","+  tcellIP +","+tcellPort +","+pubKey+")";
			System.out.println("Executing query : " + query);			


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Uncomment when the close function will be implemented
			// attemptToClose(ps);
		}

	}
	/**
	 * Deletes a file in the db
	 * @param fileGID the fileGID of the file
	 * @throws Exception
	 */
	public void deleteFileDescByFileGid(String fileGID) {
		try {
			java.sql.PreparedStatement ps = ((org.inria.jdbc.Connection) db).prepareStatement(TCell_QEP_IDs.QEP.EP_deleteFileDescByFileGid);
			ps.setString(1, fileGID);
			ps.executeUpdate();
			
			String query = "DELETE FROM File WHERE FILEGID ="+ fileGID+")";
			System.out.println("Executing query : " + query);			

			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Uncomment when the close function will be implemented
			// attemptToClose(ps);
		}

	}
	/**
	 * Deletes a user from the db
	 * @param userGID the userGID of the removed user
	 * @throws Exception
	 */
	public void deleteUserByUserGID(int userGID) {
		try {
			java.sql.PreparedStatement ps = ((org.inria.jdbc.Connection) db).prepareStatement(TCell_QEP_IDs.QEP.EP_deleteUserByUserGid);

			ps.setInt(1, userGID);
			ps.executeUpdate();
			
			String query = "DELETE FROM User WHERE IdGlobal ="+ userGID+")";
			System.out.println("Executing query : " + query);			

			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Uncomment when the close function will be implemented
			// attemptToClose(ps);
		}

	}
	/**
	 * check a file exist
	 * @param fileGID the fileGID of the file to check
	 * @throws Exception
	 */
	public boolean isFileGIDexists(String fileGID) {
		boolean result = false;

		try {
			java.sql.PreparedStatement ps = ((org.inria.jdbc.Connection) db).prepareStatement(TCell_QEP_IDs.QEP.EP_isFileGidExists);

			ps.setString(1, fileGID);
			ResultSet rs = ps.executeQuery();
			
			String query = "SELECT IdGlobal, FILEGID FROM File WHERE FILEGID = " + fileGID +")";
			System.out.println("Executing query : " + query);			

			
			if (rs.next()) {
				result = true;
			} else {
				result = false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Uncomment when the close function will be implemented
			// attemptToClose(ps);
		}
		return result;
	}
	
	
	
	/**
	 * Select USERGID, TCELLIP, PUBLICKEY from USER WHERE USERGID = 'userGID';
	 * @param userGID the user ID
	 * @return List<IP>
	 *    List of IPs <USERGID, TCELLIP, PUBLICKEY>
	 */
	public User getUserById( int userGID ) throws NumberFormatException {
		User user = null;

		try {
			java.sql.PreparedStatement ps = ((org.inria.jdbc.Connection) db).prepareStatement(TCell_QEP_IDs.QEP.EP_getUserById);
			ps.setInt(1, userGID);
			ResultSet rs = ps.executeQuery();
			
			String query = "SELECT IdGlobal, TCELLIP, PUBLICKEY,TCELLPORT FROM USER WHERE IdGlobal = "+ userGID +")";
			System.out.println("Executing query : " + query);			

			
			//Retrieve TCellIP  and PublicKey from USER table in TCellDB
			while (rs.next()) {
				int UserGID =rs.getInt(1);
				String TCellIP = rs.getString(2);
				
				//convert Blob to String
				Blob myPubKeyBlob = rs.getBlob(3);
				byte[] bPubKey = myPubKeyBlob.getBytes(1, (int) myPubKeyBlob.length());
				String pubKey = new String(bPubKey);
				
				int port = rs.getInt(4);
				user = new User(UserGID,TCellIP, port, pubKey);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Uncomment when the close function will be implemented
			// attemptToClose(ps);
		}

		return user;                      
	}
	
	
	/**
	 * SELECT FILEID, SECRETKEY, IV FROM FILE WHERE FILEGID = 'fileGID';
	 * @param fileGID the file ID
	 * @return FileDesc 
	 *    The File description <FILEID, SECRETKEY, IV>
	 */
	public FileDesc getFileDescByGid(String fileGID) {
		FileDesc result = null;

		try {
			java.sql.PreparedStatement ps = ((org.inria.jdbc.Connection) db).prepareStatement(TCell_QEP_IDs.QEP.EP_getFileDescByGid);
			ps.setString(1, fileGID);
			ResultSet rs = ps.executeQuery();

			String query = "SELECT FILEID, SECRETKEY, IV FROM FILE WHERE FILEGID ="+ fileGID +")";
			System.out.println("Executing query : " + query);			


			while (rs.next()) {
				String fileID = rs.getString(1);
				String sKey = rs.getString(2);
				String iv = rs.getString(3);
				result = new FileDesc(fileGID,fileID, sKey, iv,"","");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Uncomment when the close function will be implemented
			// attemptToClose(ps);

		}

		return result;
	}
	/**
	 * Select USERGID, TCELLIP, PUBLICKEY from USER ;
	 * 
	 * @return List<IP>
	 *    List of IPs <USERGID, TCELLIP, PUBLICKEY>
	 */
	public List<Integer> getAllUsersGID() {

		List<Integer> result = new ArrayList<Integer>();

		try {
			java.sql.PreparedStatement ps = ((org.inria.jdbc.Connection) db).prepareStatement(TCell_QEP_IDs.QEP.EP_getAllUsersGid);
			ResultSet rs = ps.executeQuery();
			String query = "SELECT IdGlobal from USER";
			System.out.println("Executing query : " + query);			

			while (rs.next()) {
				result.add(rs.getInt(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Uncomment when the close function will be implemented
			// attemptToClose(ps);
			
		}

		return result;

	}

	/**
	 * Select USERGID, TCELLIP, PUBLICKEY, TCELLPORT from USER ;
	 * 
	 * @return List<IP>
	 *    List of IPs <USERGID, TCELLIP, PUBLICKEY>
	 */
	public List<User> getAllUsers() {

		List<User> result = new ArrayList<User>();

		try {
			java.sql.PreparedStatement ps = ((org.inria.jdbc.Connection) db).prepareStatement(TCell_QEP_IDs.QEP.EP_getAllUsers);
			ResultSet rs = ps.executeQuery();
			String query = "SELECT IdGlobal, TCELLIP, PUBLICKEY, TCELLPORT from USER";
			System.out.println("Executing query : " + query);			

			while (rs.next()) {
				int UserGID =rs.getInt(1);
				String TCellIP = rs.getString(2);
				
				//convert Blob to String
				Blob myPubKeyBlob = rs.getBlob(3);
				byte[] bPubKey = myPubKeyBlob.getBytes(1, (int) myPubKeyBlob.length());
				String pubKey = new String(bPubKey);
				
				int port = rs.getInt(4);
				User user = new User(UserGID,TCellIP, port, pubKey);
				result.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Uncomment when the close function will be implemented
			// attemptToClose(ps);
			
		}

		return result;

	}
	/**
	 * SELECT FILEID, SECRETKEY, IV FROM FILE;
	 * @param fileGID the file ID
	 * @return FileDesc 
	 *    The File description <FILEID, SECRETKEY, IV>
	 */
	public ArrayList<FileDesc> getAllFiles() {

		ArrayList<FileDesc> result = new ArrayList<FileDesc>();

		try {
			java.sql.PreparedStatement ps = ((org.inria.jdbc.Connection) db).prepareStatement(TCell_QEP_IDs.QEP.EP_getAllFiles);
			ResultSet rs = ps.executeQuery();
			
			String query = "SELECT FILEGID, TYPE, DESCRIPTION FROM FILE";
			System.out.println("Executing query : " + query);			

			
			while (rs.next() == true) {
				String fileGID = rs.getString(1);
				String type = rs.getString(2);
				String Description = rs.getString(3);
				result.add(new FileDesc(fileGID, "", "", "", type, Description));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Uncomment when the close function will be implemented
			// attemptToClose(ps);
			
		}

		return result;
	}
	
	/**
	 * SELECT IdGlobal, MYTCELLIP, MYTCELLPORT, MYPUBLICKEY, MYPRIVATEKEY FROM MyInfo;
	 * 
	 * @return MyInfo 
	 *    MyInfo <myGid, myTcellIp, myTcellPort, myPubKey, myPrivKey>
	 */
	
	public MyInfo getMyInfo() {
		
		MyInfo myInfo = null;
		try {
			java.sql.PreparedStatement ps = ((org.inria.jdbc.Connection) db).prepareStatement(TCell_QEP_IDs.QEP.EP_getMyInfo);
			ResultSet rs = ps.executeQuery();

			String query = "SELECT IdGlobal, MYTCELLIP, MYTCELLPORT, MYPUBLICKEY, MYPRIVATEKEY FROM MyInfo";
			System.out.println("Executing query : " + query);

			while (rs.next()) {

				int myGid = rs.getInt(1);
				String myTcellIp = rs.getString(2);
				int myTcellPort = rs.getInt(3);
				
				//convert Blob to String
				Blob myPubKeyBlob = rs.getBlob(4);
				byte[] bPubKey = myPubKeyBlob.getBytes(1, (int) myPubKeyBlob.length());
				String myPubKey = new String(bPubKey);
				
				//convert Blob to String
				Blob myPrivKeyBlob = rs.getBlob(5);
				byte[] bPrivKey = myPrivKeyBlob.getBytes(1, (int) myPrivKeyBlob.length());
				String myPrivKey = new String(bPrivKey);
				
				myInfo = new MyInfo(myGid, myTcellIp, myTcellPort, myPubKey, myPrivKey);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Uncomment when the close function will be implemented
			// attemptToClose(ps);
		}

		return myInfo;
	}
	/**
	 * Inserts my informations in the MyInfo table
	 * @param myGID the user id
	 * @param myTcellIp the TC's IP of the user
	 * @param myPubKey the public key of the user
	 * @param myPrivKey the private key of the user
	 **/
	public void insertMyInfo(int myGid, String myTcellIp, int myTcellPort, String myPubKey,  String myPrivKey) {

		try {
			java.sql.PreparedStatement ps = ((org.inria.jdbc.Connection) db).prepareStatement(TCell_QEP_IDs.QEP.EP_insertMyInfo);
			ps.setInt(1, myGid);
			ps.setString(2, myTcellIp);
			ps.setInt(3, myTcellPort);
			
			//convert String to blob
			
			Blob pubKeyBlob = db.createBlob();
			if(!myPubKey.isEmpty()) pubKeyBlob.setBytes(1, myPubKey.getBytes());
			ps.setBlob(4, pubKeyBlob);
		
			//convert String to blob
			
			Blob privKeyBlob = db.createBlob();
			if(!myPrivKey.isEmpty()) privKeyBlob.setBytes(1, myPrivKey.getBytes());
			ps.setBlob(5, privKeyBlob);
			
			ps.executeUpdate();
			
			String query= "INSERT INTO MyInfo  VALUES ("+ myGid+","+myTcellIp+","+myTcellPort+","+myPubKey+","+myPrivKey+")";
			System.out.println("Executing query : " + query);			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Uncomment when the close function will be implemented
			// attemptToClose(ps);
		}

	}
	
	/**
	 * update my recovered information in the MyInfo table
	 * @param myGID the user id
	 * @param myTcellIp the TC's IP of the user
	 * @param myPubKey the public key of the user
	 * @param myPrivKey the private key of the user
	 **/
	public void deleteMyInfo(int myGid) {

		try {
			java.sql.PreparedStatement ps = ((org.inria.jdbc.Connection) db).prepareStatement(TCell_QEP_IDs.QEP.EP_deleteMyInfo);
			
			ps.setInt(1, myGid);
			
			ps.executeUpdate();
			
			String query= "DELETE FROM MyInfo WHERE idGlobal = "+ myGid;
			System.out.println("Executing query : " + query);			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Uncomment when the close function will be implemented
			// attemptToClose(ps);
		}

	}	
	/**********************************************Log data****************************************************************/
	public void printAllFiles() {

		try {
			java.sql.PreparedStatement ps = ((org.inria.jdbc.Connection) db).prepareStatement(TCell_QEP_IDs.QEP.EP_getAllFiles);
			ResultSet rs = ps.executeQuery();
			
			String query = "SELECT FILEGID, TYPE, DESCRIPTION FROM FILE";
			System.out.println("Executing query : " + query);			

			Tools.lireResultSet(rs , out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Uncomment when the close function will be implemented
			// attemptToClose(ps);
			
		}
	}

	
	
	public void printAllUsers() {

		try {
			java.sql.PreparedStatement ps = ((org.inria.jdbc.Connection) db).prepareStatement(TCell_QEP_IDs.QEP.EP_getAllUsers);
			ResultSet rs = ps.executeQuery();
			
			String query = "SELECT IdGlobal, TCELLIP, PUBLICKEY,TCELLPORT FROM User";
			System.out.println("Executing query : " + query);			

			lireResultSet(rs , out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Uncomment when the close function will be implemented
			// attemptToClose(ps);
			
		}
	}
	
	public void printMyInfo() {

		try {
			java.sql.PreparedStatement ps = ((org.inria.jdbc.Connection) db).prepareStatement(TCell_QEP_IDs.QEP.EP_getMyInfo);
			ResultSet rs = ps.executeQuery();
			
			String query = " SELECT IdGlobal, MYTCELLIP, MYTCELLPORT, MYPUBLICKEY, MYPRIVATEKEY FROM MyInfo ";
			System.out.println("Executing query : " + query);			

			lireResultSet(rs,out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Uncomment when the close function will be implemented
			// attemptToClose(ps);
			
		}
	}
	
	
	public void printAllFilesDesc() {

		try {
			java.sql.PreparedStatement ps = ((org.inria.jdbc.Connection) db).prepareStatement(TCell_QEP_IDs.QEP.EP_getAllFilesDesc);
			ResultSet rs = ps.executeQuery();
			
			String query = "SELECT * FROM FILE";
			System.out.println("Executing query : " + query);			

			lireResultSet(rs,out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
	}

	/**
	 * Inserts a shared key
	 * @param userGID user id
	 * @param index shared key class attribute (see class Shared)
	 * @param encrShare shared key class attribute (see class Shared)
	 * @param share shared key class attribute (see class Shared)
	 * @param proofc shared key class attribute (see class Shared)
	 * @param proofr shared key class attribute (see class Shared)
	 * @param ucode shared key class attribute (see class Shared)
	 */

	public void insertSharedKey(int userGID, Share part) {
		try {
			
			int index = part.getIndex();
			String encrShare = part.getEncryptedShare().toString();
			String share = part.getShare().toString();
			String proofc = part.getProofc().toString();
			String proofr = part.getProofr().toString();
			byte[] ucode = part.getU();
			
			java.sql.PreparedStatement ps = ((org.inria.jdbc.Connection) db).prepareStatement(TCell_QEP_IDs.QEP.EP_insertSharedKey);

			ps.setInt(1, userGID);
			ps.setInt(2, index);
			ps.setString(3, encrShare);
			ps.setString(4, share);
			ps.setString(5, proofc);
			ps.setString(6, proofr);
			
			//convert String to blob
			Blob UBlob = db.createBlob();
			UBlob.setBytes(1, ucode);
			ps.setBlob(7, UBlob);
			
			ps.executeUpdate();
			
			System.out.println("Shared Shamir Key added");
			String query= "INSERT INTO SharedKey  VALUES ("+userGID+","+  index +","+encrShare +","+share+","+proofc+","+proofr+","+ucode +")";
			System.out.println("Executing query : " + query);			


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Uncomment when the close function will be implemented
			// attemptToClose(ps);
		}

	}
	
	/**
	 * SELECT IND, ENCRYPTEDSHARE, SHARE, PROOFC, PROOFR, UCODE FROM SharedKey WHERE IdGlobal = 'userID';
	 * @param userID the user ID
	 * @return the shared key of userID
	 */
	public Share getSharedKeyById( int userID ){
		Share sharedKey = null;
		
		try {
			java.sql.PreparedStatement ps = ((org.inria.jdbc.Connection) db).prepareStatement(TCell_QEP_IDs.QEP.EP_getSharedKeyById);
			ps.setInt(1, userID);
			ResultSet rs = ps.executeQuery();
			
			String query = "SELECT IND, ENCRYPTEDSHARE, SHARE, PROOFC, PROOFR, UCODE FROM SharedKey WHERE IdGlobal = "+ userID +")";
			System.out.println("Executing query : " + query);			

			
			while (rs.next()) {
				int index = rs.getInt(1);
				BigInteger encrShare = new BigInteger(rs.getString(2));
				BigInteger share = new BigInteger(rs.getString(3));
				BigInteger proofc = new BigInteger(rs.getString(4));
				BigInteger proofr = new BigInteger(rs.getString(5));
				
				//convert Blob to byte[]
				Blob UBlob = rs.getBlob(6);
				byte[] ucode = UBlob.getBytes(1, (int) UBlob.length());
				
				sharedKey = new Share(index, encrShare, share, proofc, proofr, ucode);
				
			}
		
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Uncomment when the close function will be implemented
			// attemptToClose(ps);
		}
		
		return sharedKey;
	}


	/*************************************************************************************************************/

	

	private void attemptToClose(PreparedStatement ps) {
		try {
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
