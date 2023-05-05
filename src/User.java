import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class User extends JFrame implements ActionListener {
    private static final String DB_URL = "jdbc:mysql://localhost/expensetracker";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "password";

    private JLabel userLabel, dateLabel, categoryLabel, amountLabel;
    private JTextField userField, dateField, categoryField, amountField;
    private JButton addButton, editButton, deleteButton, clearButton;
    private JTable expensesTable;

    public User() {
        setTitle("Expense Tracker");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        userLabel = new JLabel("User: ");
        dateLabel = new JLabel("Date: ");
        categoryLabel = new JLabel("Category: ");
        amountLabel = new JLabel("Amount: ");

        userField = new JTextField(20);
        dateField = new JTextField(20);
        categoryField = new JTextField(20);
        amountField = new JTextField(20);

        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        clearButton = new JButton("Clear");

        addButton.addActionListener(this);
        editButton.addActionListener(this);
        deleteButton.addActionListener(this);
        clearButton.addActionListener(this);

        expensesTable = new JTable();

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(userLabel);
        inputPanel.add(userField);
        inputPanel.add(dateLabel);
        inputPanel.add(dateField);
        inputPanel.add(categoryLabel);
        inputPanel.add(categoryField);
        inputPanel.add(amountLabel);
        inputPanel.add(amountField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        JPanel tablePanel = new JPanel(new GridLayout(1, 1));
        tablePanel.add(new JScrollPane(expensesTable));

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(tablePanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);

        refreshTable();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new User().setVisible(true));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            addExpense();
        } else if (e.getSource() == editButton) {
            addExpense();
        } else if (e.getSource() == deleteButton) {
            addExpense();
        } else if (e.getSource() == clearButton) {
            clearInputFields();
        }
    }

    private void clearInputFields() {
    }

    private void addExpense() {
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    private void refreshTable() {
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM expenses";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            Object DbUtils;
            expensesTable.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error refreshing table: " + e.getMessage());
        }
    }
}