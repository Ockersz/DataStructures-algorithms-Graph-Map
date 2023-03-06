package Controller;

import Model.DatabaseConnection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class GraphController {

    public void createVertex(String cityName) {

        try {
            Statement st = DatabaseConnection.getConnection().createStatement();
            int i = st.executeUpdate("INSERT INTO `vertex`(`v_name`) VALUES ('" + cityName + "')");
            if (i == 1) {
                JOptionPane.showMessageDialog(null, "City Added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

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
