/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import configuration.Configuration;
import dao.TcellDAOToken;

/**
 * Initialization of the DataBase.
 * 
 * @author Majdi Ben Fredj
 */
public class DatabaseRecover {
	/**
	 * Initialization of db.
	 * 
	 * @param args
	 * @throws NoSuchAlgorithmException
	 * @throws FileNotFoundException
	 */


	public static void main(String[] args) throws NoSuchAlgorithmException,
			FileNotFoundException {

		try {
			System.getProperties().setProperty("jdbc.port", Configuration.getConfiguration().getProperty("dbPort"));
			TcellDAOToken.getInstance(true);
			TcellDAOToken.getInstance(false).DropTables();
			TcellDAOToken.getInstance(false).CreateTables();
			
			
			// Init MyInfo
			int myGid = Integer.parseInt(Configuration.getConfiguration().getProperty("userGID"));
			int myTcellPort = Integer.parseInt(Configuration.getConfiguration().getProperty("port"));
			
			
			TcellDAOToken.getInstance(false).insertMyInfo(myGid, "", myTcellPort, "", "");

			
			TcellDAOToken.getInstance(false).Save_DBMS_on_disk();
			
			TcellDAOToken.getInstance(false).Shutdown_DBMS();
			
			System.out.println("done");
			
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
			
	}

}

