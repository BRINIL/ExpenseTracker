import javax.swing.*;
import javax.swing.table.TableModel;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ExpenseTrackerLogin {
    private JFrame loginFrame;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public ExpenseTrackerLogin() {
        loginFrame = new JFrame("Expense Tracker - Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel usernameLabel = new JLabel("Username:");
        panel.add(usernameLabel, constraints);

        constraints.gridx = 1;
        usernameField = new JTextField();
        panel.add(usernameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        JLabel passwordLabel = new JLabel("Password:");
        panel.add(passwordLabel, constraints);

        constraints.gridx = 1;
        passwordField = new JPasswordField();
        panel.add(passwordField, constraints);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a username and password.");
                    return;
                }

                User user = authenticate(username, password);

                if (user == null) {
                    JOptionPane.showMessageDialog(null, "Incorrect username or password.");
                } else {
                    loginFrame.dispose();
                    new ExpenseTrackerGUI(user).open();
                }
            }
        });

        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.insets = new Insets(20, 10, 10, 10);
        panel.add(loginButton, constraints);

        loginFrame.add(panel);
        loginFrame.pack();
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);
    }

    private User authenticate(String username, String password) {
        String url = "jdbc:mysql://localhost:3306/expensetracker";
        String user = "root";
        String pass = "12345";

        try {
            Connection con = DriverManager.getConnection(url, user, pass);
            String query = "SELECT * FROM user WHERE userID =? AND password =?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User();
            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        new ExpenseTrackerLogin();
    }
}

class ExpenseTrackerGUI {
    private JFrame mainFrame;
    private User user;
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private JTable table;
    private JTextField expenseField;
    public JTextField getExpenseField() {
        return expenseField;
    }

    public void setExpenseField(JTextField expenseField) {
        this.expenseField = expenseField;
    }

    private JTextField amountField;

    public JTextField getAmountField() {
        return amountField;
    }

    public void setAmountField(JTextField amountField) {
        this.amountField = amountField;
    }

    /**
     * @param user
     */
    public ExpenseTrackerGUI(User user) {
        this.user = user;
        mainFrame = new JFrame("Expense Tracker - " + user.getUsername());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        // Create table model and populate with user's expenses
        ExpenseTableModel tableModel = new ExpenseTableModel();
        tableModel.populate(user.getId());

        table = new JTable((TableModel) tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Create button panel for adding expenses
JPanel buttonPanel = new JPanel();
buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));

JLabel balanceLabel = new JLabel("Balance: " + tableModel.getBalance());
buttonPanel.add(balanceLabel);

buttonPanel.add(Box.createHorizontalGlue());

JButton addButton = new JButton("Add Expense");
addButton.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        String description = JOptionPane.showInputDialog("Enter description:");
        if (description == null || description.isEmpty()) {
            return;
        }

        String amountStr = JOptionPane.showInputDialog("Enter amount:");
        if (amountStr == null || amountStr.isEmpty()) {
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid amount.");
            return;
        }

        Expense expense = new Expense(description, amount, user.getId());
        tableModel.addExpense(expense);
        balanceLabel.setText("Balance: " + tableModel.getBalance());

        // Save expense to database
        String url = "jdbc:mysql://localhost:3306/expensetracker";
        String user = "root";
        String pass = "12345";

        try {
            Connection con = DriverManager.getConnection(url, user, pass);
            String query = "INSERT INTO expenses (description, amount, user_id) VALUES (?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, expense.getDescription());
            stmt.setDouble(2, expense.getAmount());
            stmt.setInt(3, expense.getUserId());
            stmt.executeUpdate();

            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
});
buttonPanel.add(addButton);

panel.add(buttonPanel, BorderLayout.PAGE_END);

mainFrame.add(panel);
mainFrame.pack();
mainFrame.setLocationRelativeTo(null);
mainFrame.setVisible(true);
    }

    public void open() {
    }
}
