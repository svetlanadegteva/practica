package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List; 
import model.tovar;
 
public class DBUtils {
 
    
 
    public static List<tovar> queryProduct(Connection conn) throws SQLException {
        String sql = "Select a.id, a.name, a.price, a.count, a.description from tovar a ";
 System.out.println("что-то тут такое"+conn);
 Statement st = conn.createStatement();
 ResultSet rs = st.executeQuery(sql);
        List<tovar> list = new ArrayList<tovar>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            double price = rs.getDouble("price");
            int count = rs.getInt("count");
            String description = rs.getString("description");
            tovar tovar = new tovar(id, name, price,count,description);
            //tovar.setId(id);
            //tovar.setName(name);
            //tovar.setPrice(price);
            list.add(tovar);
        }
        return list;
    }
 
    public static tovar findProduct(Connection conn, int id) throws SQLException {
        String sql = "Select a.id, a.name, a.price, a.count, a.description from tovar a where a.id=?";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, id);
 
        ResultSet rs = pstm.executeQuery();
 
        while (rs.next()) {
            String name = rs.getString("name");
            double price = rs.getDouble("price");
            int count = rs.getInt("count");
            String description = rs.getString("description");
                
            tovar tovar = new tovar(id, name, price,count,description);
            return tovar;
        }
        return null;
    }
 
    public static void updateProduct(Connection conn, tovar tovar) throws SQLException {
        String sql = "Update tovar set name =?, price=?, count=?, description=? where id=? ";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
 
        pstm.setString(1, tovar.getName());
        pstm.setDouble(2, tovar.getPrice());
        pstm.setInt(3, tovar.getCount());
        pstm.setString(4, tovar.getDescription());
        pstm.setInt(5, tovar.getId());
        pstm.executeUpdate();
    }
 
    public static void insertProduct(Connection conn, tovar product) throws SQLException {
        String sql = "Insert into tovar(id, name,price,count,description) values (?,?,?,?,?)";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
 
        pstm.setInt(1, product.getId());
        pstm.setString(2, product.getName());
        pstm.setDouble(3, product.getPrice());
        pstm.setInt(4, product.getCount());
        pstm.setString(5, product.getDescription());
 
        pstm.executeUpdate();
    }
 
    public static void deleteProduct(Connection conn, int id) throws SQLException {
        String sql = "Delete From tovar where id= ?";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
 
        pstm.setInt(1, id);
 
        pstm.executeUpdate();
    }

}
