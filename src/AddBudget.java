import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AddBudget extends JFrame {

    private JPanel mainPanel;
    private JTextField expenseDateField;
    private JTextField expenseAmountField;
    private JButton addButton;
    private JButton summaryButton;
    private JTable expenseTable;
    private JScrollPane tableScrollPane;

    private DefaultTableModel tableModel;
    private Connection connection;

    public AddBudget() {
        super("Expense Tracker");

        // Set up the main panel
        mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        // Set up the expense date and amount fields
        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        JLabel expenseDateLabel = new JLabel("Expense Date:");
        expenseDateField = new JTextField();
        JLabel expenseAmountLabel = new JLabel("Expense Amount:");
        expenseAmountField = new JTextField();
        inputPanel.add(expenseDateLabel);
        inputPanel.add(expenseDateField);
        inputPanel.add(expenseAmountLabel);
        inputPanel.add(expenseAmountField);
        mainPanel.add(inputPanel, BorderLayout.NORTH);

        // Set up the add and summary buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        addButton = new JButton("Add Expense");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addExpense();
            }
        });
        summaryButton = new JButton("Show Summary");
        summaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSummary();
            }
        });
        buttonPanel.add(addButton);
        buttonPanel.add(summaryButton);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Set up the expense table
        tableModel = new DefaultTableModel(new String[]{"Expense Date", "Expense Amount"}, 0);
        expenseTable = new JTable(tableModel);
        tableScrollPane = new JScrollPane(expenseTable);
        mainPanel.add(tableScrollPane, BorderLayout.SOUTH);

        // Connect to the database
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/expensetracker", "username", "password");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to connect to database", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Set up the JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addExpense() {
        String expenseDate = expenseDateField.getText();
        double expenseAmount = Double.parseDouble(expenseAmountField.getText());

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO expenses (ExpenseDate, ExpenseAmount) VALUES (?, ?)");
            statement.setString(1, expenseDate);
            statement.setDouble(2, expenseAmount);
            statement.executeUpdate();
            tableModel.addRow(new Object[]{expenseDate, expenseAmount});
            expenseDateField.setText("");
            expenseAmountField.setText("");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to add expense", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showSummary() {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT ExpenseDate, SUM(ExpenseAmount) FROM expenses GROUP BY ExpenseDate");
            ResultSet resultSet = statement.executeQuery();
            double totalExpense = 0.0;
            tableModel.setRowCount(0);
            while (resultSet.next()) {
                String expenseDate = resultSet.getString("ExpenseDate");
                double expenseAmount = resultSet.getDouble("SUM(ExpenseAmount)");
                tableModel.addRow(new Object[]{expenseDate, expenseAmount});
                totalExpense += expenseAmount;
                }
                JOptionPane.showMessageDialog(this, String.format("Total expenses: $%.2f", totalExpense), "Expense Summary", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to show summary", "Error", JOptionPane.ERROR_MESSAGE);
                }
                }
                public static void main(String[] args) {
                    new AddBudget();
                }
            }                
