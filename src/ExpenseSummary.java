import java.sql.*;
import javax.swing.*;

public class ExpenseSummary extends JFrame {

    private JTextArea summaryArea;

    public ExpenseSummary() {
        super("Expense Summary");

        // create database connection
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/expensetracker", "root", "12345");

            // retrieve expense summary data
            String query = "SELECT ExpenseID, SUM(ExpenseAmount) AS total FROM expenses GROUP BY ExpenseDescription";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            // create form
            JPanel panel = new JPanel();
            panel.add(new JLabel("Expense Summary:"));
            panel.add(new JLabel(" "));
            summaryArea = new JTextArea(20, 30);
            summaryArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(summaryArea);
            panel.add(scrollPane);
            add(panel);

            // populate summary area
            double totalExpense = 0.0;
            while (rs.next()) {
                String expenseName = rs.getString("ExpenseDescription");
                double expenseTotal = rs.getDouble("total");
                String expenseSummary = String.format("%-20s $%.2f\n", expenseName, expenseTotal);
                summaryArea.append(expenseSummary);
                totalExpense += expenseTotal;
            }
            summaryArea.append("-----------------\n");
            String totalExpenseSummary = String.format("%-20s $%.2f\n", "Total Expense", totalExpense);
            summaryArea.append(totalExpenseSummary);

            // close resources
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ExpenseSummary();
    }
}
