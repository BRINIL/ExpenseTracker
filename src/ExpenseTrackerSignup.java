import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ExpenseTrackerSignup {
    private JFrame frame;
    private JTextField userIDTextField;
    private JPasswordField passwordField;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ExpenseTrackerSignup window = new ExpenseTrackerSignup();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 
     */
    public ExpenseTrackerSignup() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Expense Tracker");
        frame.setBounds(100, 100, 400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel);
        GridBagLayout layout = new GridBagLayout();
        layout.columnWidths = new int[]{0, 0, 0};
        layout.rowHeights = new int[]{0, 0, 0};
        layout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        layout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        panel.setLayout(layout);

        JLabel userIDLabel = new JLabel("User ID:");
        GridBagConstraints gbc_userIDLabel = new GridBagConstraints();
        gbc_userIDLabel.anchor = GridBagConstraints.EAST;
        gbc_userIDLabel.insets = new Insets(0, 0, 5, 5);
        gbc_userIDLabel.gridx = 0;
        gbc_userIDLabel.gridy = 0;
        panel.add(userIDLabel, gbc_userIDLabel);

        userIDTextField = new JTextField();
        GridBagConstraints gbc_userIDTextField = new GridBagConstraints();
        gbc_userIDTextField.insets = new Insets(0, 0, 5, 0);
        gbc_userIDTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_userIDTextField.gridx = 1;
        gbc_userIDTextField.gridy = 0;
        panel.add(userIDTextField, gbc_userIDTextField);
        userIDTextField.setColumns(10);

        // User ID label
        JLabel userLabel = new JLabel("Username:");
        GridBagConstraints gbc_userLabel = new GridBagConstraints();
        gbc_userLabel.anchor = GridBagConstraints.EAST;
        gbc_userLabel.insets = new Insets(0, 0, 5, 5);
        gbc_userLabel.gridx = 0;
        gbc_userLabel.gridy = 0;
        panel.add(userLabel, gbc_userLabel);

        // User ID text field
        JTextField userTextField = new JTextField();
        GridBagConstraints gbc_userTextField = new GridBagConstraints();
        gbc_userTextField.insets = new Insets(0, 0, 5, 0);
        gbc_userTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_userTextField.gridx = 1;
        gbc_userTextField.gridy = 0;
        panel.add(userTextField, gbc_userTextField);
        userTextField.setColumns(10);

        JLabel passwordLabel = new JLabel("Password:");
        GridBagConstraints gbc_passwordLabel = new GridBagConstraints();
        gbc_passwordLabel.anchor = GridBagConstraints.EAST;
        gbc_passwordLabel.insets = new Insets(0, 0, 0, 5);
        gbc_passwordLabel.gridx = 0;
        gbc_passwordLabel.gridy = 1;
        panel.add(passwordLabel, gbc_passwordLabel);

        passwordField = new JPasswordField();
        GridBagConstraints gbc_passwordField = new GridBagConstraints();
        gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
        gbc_passwordField.gridx = 1;
        gbc_passwordField.gridy = 1;
        panel.add(passwordField, gbc_passwordField);


        // Create the Signup button and add an action listener to it
JButton signupButton = new JButton("Signup");
signupButton.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        String userID = userIDTextField.getText();
        String password = new String(passwordField.getPassword());
        if (userID.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a userID and password.");
        } else {
            try {
                // Connect to the database
                String url = "jdbc:mysql://localhost:3306/expensetracker";
                String user = "root";
                String dbPassword = "12345";
                Connection conn = DriverManager.getConnection(url, user, dbPassword);

                // Check if the user already exists
                String query = "SELECT COUNT(*) FROM user WHERE userID = ?";
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setString(1, userID);
                ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                int count = resultSet.getInt(1);
                if (count > 0) {
                    JOptionPane.showMessageDialog(null, "User already exists.");
                } else {
                    // Insert the new user's information into the database
                    String insertQuery = "INSERT INTO user (userID, password) VALUES (?, ?)";
                    PreparedStatement insertStatement = conn.prepareStatement(insertQuery);
                    insertStatement.setString(1, userID);
                    insertStatement.setString(2, password);
                    insertStatement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Signup successful. Please login.");

                    // Clear the input fields
                    userIDTextField.setText("");
                    passwordField.setText("");
                }

                // Close the database connection
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
});
panel.add(signupButton);
    }
}

