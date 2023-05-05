import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class Expense extends JFrame implements ActionListener {

    private JTextField nameField, amountField;
    private JButton submitButton;
    private JTextArea summaryArea;

    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    private Map<String, Double> expenses = new HashMap<>();

    public Expense() {
        super("Expense Tracker");

        // create database connection
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/expensetracker", "root", "12345");
            pstmt = conn.prepareStatement("INSERT INTO expenses (ExpenseAmount, ExpenseDescription, ExpenseDate) VALUES (?, ?, ?)");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        // set up form
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Expense description:"));
        nameField = new JTextField();
        panel.add(nameField);
        panel.add(new JLabel("Expense Amount:"));
        amountField = new JTextField();
        panel.add(amountField);
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        panel.add(submitButton);
        summaryArea = new JTextArea(10, 20);
        summaryArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(summaryArea);
        panel.add(scrollPane);

        add(panel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String ExpenseDescription = nameField.getText();
        String ExpenseAmount = amountField.getText();

        if (ExpenseDescription.isEmpty() || ExpenseAmount.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter expense name and amount.");
            return;
        }

        double expenseAmount = Double.parseDouble(ExpenseAmount);

        try {
            pstmt.setString(1, ExpenseAmount);
            pstmt.setString(2, ExpenseDescription);
            pstmt.setString(3, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            pstmt.executeUpdate();

            if (expenses.containsKey(ExpenseDescription)) {
                expenses.put(ExpenseDescription, expenses.get(ExpenseDescription) + expenseAmount);
            } else {
                expenses.put(ExpenseDescription, expenseAmount);
            }

            summaryArea.setText("");
            double totalExpense = 0.0;
            for (Map.Entry<String, Double> expense : expenses.entrySet()) {
                String expenseSummary = String.format("%-20s $%.2f\n", expense.getKey(), expense.getValue());
                summaryArea.append(expenseSummary);
                totalExpense += expense.getValue();
            }
            summaryArea.append("-----------------\n");
            String totalExpenseSummary = String.format("%-20s $%.2f\n", "Total Expense", totalExpense);
            summaryArea.append(totalExpenseSummary);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        nameField.setText("");
        amountField.setText("");
    }

    public static void main(String[] args) {
        new Expense();
    }
}
