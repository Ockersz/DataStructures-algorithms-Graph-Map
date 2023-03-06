package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DatabaseConnection {
    
     private static Connection con;
    
    public static Connection getConnection()
    {
        try{
            String path = "jdbc:mysql://localhost/sl_distance_calculator";
            con = DriverManager.getConnection(path,"root","root");
            
        }catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Database Error "+ex.getMessage() ,"Error",JOptionPane.ERROR_MESSAGE);
            
        }
        return con;
    }
}
