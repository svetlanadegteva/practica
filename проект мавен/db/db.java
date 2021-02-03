package db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class db {
	public static Connection getSQLServerConnection() throws IOException, ClassNotFoundException, SQLException { 
	    	Connection connection = null;
	    		try {
	    		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
	    		connection=DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\admin\\Documents\\site.accdb");
	    		System.out.println("Succesfully connected");
	    }
	    catch(SQLException sqlex){
	    		sqlex.printStackTrace();
	    		}  
	        System.out.println(connection);
	        return connection;
	    } 
	} 
