import java.sql.*;

public class ExpenseManager {
private Connection connection;
public ExpenseManager(String url, String username, String password) {
    // Establish a connection to the database
    try {
        connection = DriverManager.getConnection(url, username, password);
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public void addExpense(String description, double amount, String date, String category) {
    // Add a new expense to the database
    String query = "INSERT INTO expenses (description, amount, date, category) VALUES (?, ?, ?, ?)";
    try {
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, description);
        statement.setDouble(2, amount);
        statement.setString(3, date);
        statement.setString(4, category);
        statement.executeUpdate();
        statement.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public void deleteExpense(int id) {
    // Delete an expense from the database
    String query = "DELETE FROM expenses WHERE id = ?";
    try {
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        statement.executeUpdate();
        statement.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public void updateExpense(int id, String description, double amount, String date, String category) {
    // Update an expense in the database
    String query = "UPDATE expenses SET description = ?, amount = ?, date = ?, category = ? WHERE id = ?";
    try {
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, description);
        statement.setDouble(2, amount);
        statement.setString(3, date);
        statement.setString(4, category);
        statement.setInt(5, id);
        statement.executeUpdate();
        statement.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public ResultSet getExpenses() {
    // Retrieve all expenses from the database
    String query = "SELECT * FROM expenses";
    try {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        return resultSet;
    } catch (SQLException e) {
        e.printStackTrace();
        return null;
    }
}

public void closeConnection() {
    // Close the database connection
    try {
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}
