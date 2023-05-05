import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static void main(String[] args) {
        Connection conn = null;
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Establish the database connection
            String url = "jdbc:mysql://localhost:3306/?user=root";
            String user = "root";
            String password = "annam561$";
            conn = DriverManager.getConnection(url, user, password);

            // Do something with the connection
            System.out.println("Connected to the database!");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Close the connection
            try {
                if (conn != null) {
                    conn.close();
                    System.out.println("Disconnected from database");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
