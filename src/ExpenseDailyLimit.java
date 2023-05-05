import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ExpenseDailyLimit extends JFrame implements ActionListener {
    private JLabel dailyExpenseLabel, currentExpenseLabel;
    private JTextField dailyExpenseField, currentExpenseField;
    private JButton submitButton;
    private Connection connection;

    public ExpenseDailyLimit() {
        super("Expense Reminder");

        // Set up GUI components
        dailyExpenseLabel = new JLabel("Daily Expense Limit:");
        currentExpenseLabel = new JLabel("Current Expenses:");
        dailyExpenseField = new JTextField(10);
        currentExpenseField = new JTextField(10);
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);

        // Set up layout
        JPanel mainPanel = new JPanel(new GridLayout(3, 2));
        mainPanel.add(dailyExpenseLabel);
        mainPanel.add(dailyExpenseField);
        mainPanel.add(currentExpenseLabel);
        mainPanel.add(currentExpenseField);
        mainPanel.add(submitButton);

        // Set up database connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/expensetracker", "root", "");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        // Add components to frame
        add(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            // Get daily expense limit and current expenses from GUI
            double dailyExpenseLimit = Double.parseDouble(dailyExpenseField.getText());
            double currentExpenses = Double.parseDouble(currentExpenseField.getText());

            try {
                // Get current date from database
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT CURDATE() as currentDate");
                resultSet.next();
                String currentDate = resultSet.getString("currentDate");

                // Get total expenses for current date
                PreparedStatement totalStatement = connection.prepareStatement("SELECT SUM(ExpenseAmount) as totalExpense FROM expenses WHERE ExpenseDate = ?");
                totalStatement.setString(1, currentDate);
                ResultSet totalResultSet = totalStatement.executeQuery();
                totalResultSet.next();
                double totalExpenses = totalResultSet.getDouble("totalExpense");

                // Check if daily expense limit has been exceeded
                if (currentExpenses + totalExpenses > dailyExpenseLimit) {
                    JOptionPane.showMessageDialog(this, "You have exceeded your daily expense limit!");
                } else {
                    JOptionPane.showMessageDialog(this, "You have not exceeded your daily expense limit.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new ExpenseDailyLimit();
    }
}
