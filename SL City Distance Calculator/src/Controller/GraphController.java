package Controller;

import Model.DatabaseConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class GraphController {

    public void createVertex(String cityName, double longitude, double latitude) {

        try {
            Statement st = DatabaseConnection.getConnection().createStatement();
            int i = st.executeUpdate("INSERT INTO `vertex`(`v_name`, `longitude`, `latitude`) VALUES ('"+cityName+"', '"+longitude+"', '"+latitude+"')");
            if (i == 1) {
                JOptionPane.showMessageDialog(null, "City Added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }
    
    public double getLongitude(String cityName){
        try {
            Statement st = DatabaseConnection.getConnection().createStatement();
            ResultSet rst = st.executeQuery("SELECT `longitude` FROM `vertex` where `v_name` = '"+cityName+"' ");
            while(rst.next()){
                return rst.getDouble(1);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return 0;
    }
    
    public double getLatitude(String cityName){
        try {
            
            Statement st = DatabaseConnection.getConnection().createStatement();
            ResultSet rst = st.executeQuery("SELECT `latitude` FROM `vertex` where `v_name` = '"+cityName+"' ");
            while(rst.next()){
                return rst.getDouble(1);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return 0;
    }

    public void createEdge(String source, String destination, int distance) {

        try {
            Statement st = DatabaseConnection.getConnection().createStatement();
            int i = st.executeUpdate("INSERT INTO `edge`(`e_source`, `e_destination`, `e_distance`) VALUES ('" + source + "','" + destination + "','" + distance + "')");
            if (i == 1) {
                JOptionPane.showMessageDialog(null, "Path Added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

}
