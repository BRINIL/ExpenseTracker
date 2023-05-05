import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

public class ExpenseTrackerLogin {
    public static void main(String[] args) {
        // Establish the database connection
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/expense_tracker";
            String user = "root";
            String password = "12345";
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }

        try (// Prompt the user to log in
        Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            // Check if the user exists
            int userId = -1;
            try {
                PreparedStatement stmt = conn.prepareStatement("SELECT id FROM user WHERE username=? AND password=?");
                stmt.setString(1, username);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    userId = rs.getInt("id");
                    System.out.println("Welcome " + username + "!");
                } else {
                    System.out.println("Invalid username or password");
                    System.exit(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.exit(1);
            }

            // Retrieve the user's expenses
            try {
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM expense WHERE user_id=?");
                stmt.setInt(1, userId);
                ResultSet rs = stmt.executeQuery();
                System.out.println("Your expenses:");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    double amount = rs.getDouble("amount");
                    String category = rs.getString("category");
                    Date date = rs.getDate("date");
                    System.out.println(id + " | " + name + " | " + amount + " | " + category + " | " + date);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        // Close the database connection
        try {
            conn.close();
            System.out.println("Disconnected from database");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
