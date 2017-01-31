/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;


import java.sql.SQLException;

import dao.TcellDAO;


/**
 * Initialization of the DataBase.
 * 
 * @author Majdi Ben Fredj
 */
public class DatabaseMain {
	/**
	 * Initialization of db.
	 * 
	 * @param args
	 */


	public static void main(String[] args)
			 {
		
		try {
			try{
				TcellDAO.getInstance().DropTables();
			}
			catch(SQLException e){
				System.err.println("Empty DB!!");
			}
			TcellDAO.getInstance().CreateTables();

		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
			
	}

}

