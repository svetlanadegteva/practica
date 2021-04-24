package db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
 
public class ConnectionUtils {
 
    public static Connection getConnection() 
              throws ClassNotFoundException, SQLException, IOException {

        return db.getSQLServerConnection();
         
    }
     
    public static void closeQuietly(Connection conn) {
        try {
            conn.close();
        } catch (Exception e) {
        }
    }
 
    public static void rollbackQuietly(Connection conn) {
        try {
            conn.rollback();
        } catch (Exception e) {
        	System.out.println(e);
        }
    }

}
