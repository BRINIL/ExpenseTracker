<<<<<<< Updated upstream
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExpenseTrackerLogin {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField signupUsernameField;
    private JPasswordField signupPasswordField;
    private JTextField signupConfirmPasswordField;
    private JButton loginButton;
    private JButton signupButton;

    private String currentUser;

    public ExpenseTrackerLogin() {
        // Create the frame
        frame = new JFrame("Expense Tracker");
        frame.setSize(500, 300);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the login form
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);

        // Create the login button
        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Check if the credentials are valid
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (validateUser(username, password)) {
                    currentUser = username;
                    openExpenseTracker();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password.");
                }
            }
        });

        // Create the signup form
        JPanel signupPanel = new JPanel();
        signupPanel.setLayout(new GridLayout(4, 2));

        JLabel signupUsernameLabel = new JLabel("Username:");
        signupUsernameField = new JTextField();

        JLabel signupPasswordLabel = new JLabel("Password:");
        signupPasswordField = new JPasswordField();

        JLabel signupConfirmPasswordLabel = new JLabel("Confirm Password:");
        signupConfirmPasswordField = new JPasswordField();

        signupPanel.add(signupUsernameLabel);
        signupPanel.add(signupUsernameField);
        signupPanel.add(signupPasswordLabel);
        signupPanel.add(signupPasswordField);
        signupPanel.add(signupConfirmPasswordLabel);
        signupPanel.add(signupConfirmPasswordField);

        // Create the signup button
        signupButton = new JButton("Signup");
        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Validate the input fields and create a new user
                String username = signupUsernameField.getText();
                String password = new String(signupPasswordField.getPassword());
                String confirmPassword = new String(((JPasswordField) signupConfirmPasswordField).getPassword());
                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "All fields are required.");
                } else if (password.equals(confirmPassword)) {
                    if (createUser(username, password)) {
                        JOptionPane.showMessageDialog(frame, "User created successfully.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Username already taken.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Passwords do not match.");
                }
            }
        });
        

        // Create a tabbed pane to switch between login and signup
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Login", loginPanel);
        tabbedPane.add("Signup", signupPanel);

        // Add the components to the frame
        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.add(loginButton, BorderLayout.SOUTH);
    }

    private boolean validateUser(String username, String password) {
        // Connect to the database
        try {
            String url = "jdbc:mysql://localhost:3306/expensetracker";
            String user = "root";
            String dbPassword = "1234";
            Connection conn = DriverManager.getConnection(url, user, dbPassword);

            // Check if the username and password match
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user WHERE username = ? AND password = ?");
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                rs.close();
                stmt.close();
                conn.close();
                return true;
            } else {
                rs.close();
                stmt.close();
                conn.close();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean createUser(String userID, String password) {
        // Connect to the database
        try {
            String url = "jdbc:mysql://localhost:3306/expensetracker";
            String user = "root";
            String dbPassword = "1234";
            Connection conn = DriverManager.getConnection(url, user, dbPassword);


            // Check if the username is taken
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user WHERE userID = ?");
            stmt.setString(1, userID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                rs.close();
                stmt.close();
                conn.close();
                return false;
            }

            // Add the new user to the database
            stmt = conn.prepareStatement("INSERT INTO users (userID, password) VALUES (?, ?)");
            stmt.setString(1, userID);
            stmt.setString(2, password);
            stmt.executeUpdate();
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    // other code

    
    private void openExpenseTracker() {
        // Implement the expense tracker GUI here
        JOptionPane.showMessageDialog(frame, "Logged in as " + currentUser);
    }
    
    public void show() {
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        ExpenseTrackerLogin expenseTracker = new ExpenseTrackerLogin();
        expenseTracker.show();
=======
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
public class ExpenseTrackerLogin extends JFrame implements AncestorListener {
JLabel userLabel, passwordLabel;
JTextField userTextField;
JPasswordField passwordField;
JButton loginButton, resetButton, signupButton;

// Database connection parameters
String url = "jdbc:mysql://localhost:3306/mydatabase";
String username = "root";
String password = "password";

ExpenseTrackerLogin() {
    userLabel = new JLabel("Username");
    userLabel.setBounds(50, 50, 100, 30);

    passwordLabel = new JLabel("Password");
    passwordLabel.setBounds(50, 100, 100, 30);

    userTextField = new JTextField();
    userTextField.setBounds(150, 50, 150, 30);

    passwordField = new JPasswordField();
    passwordField.setBounds(150, 100, 150, 30);

    loginButton = new JButton("Login");
    loginButton.setBounds(50, 150, 100, 30);
    loginButton.addActionListener(this);

    resetButton = new JButton("Reset");
    resetButton.setBounds(200, 150, 100, 30);
    resetButton.addActionListener(this);

    signupButton = new JButton("Sign up");
    signupButton.setBounds(125, 200, 150, 30);
    signupButton.addActionListener(this);

    add(userLabel);
    add(passwordLabel);
    add(userTextField);
    add(passwordField);
    add(loginButton);
    add(resetButton);
    add(signupButton);

    setTitle("Login Page");
    setSize(400, 300);
    setLayout(null);
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}

public void actionPerformed(ActionEvent e) {
    if (e.getSource() == loginButton) {
        String user = userTextField.getText();
        String password = new String(passwordField.getPassword());

        if (user.equals("admin") && password.equals("admin123")) {
            JOptionPane.showMessageDialog(this, "Login successful!");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.");
        }
    } else if (e.getSource() == resetButton) {
        userTextField.setText("");
        passwordField.setText("");
    } else if (e.getSource() == signupButton) {
        String username = JOptionPane.showInputDialog(this, "Enter a new username:");
        String password = JOptionPane.showInputDialog(this, "Enter a new password:");

        // Add code here to store the new user details in the database
        try {
            Connection con = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = con.prepareStatement("INSERT INTO users(username, password) VALUES (?, ?)");
            ps.setString(1, username);
            ps.setString(2, password);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "New user created successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to create new user.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
>>>>>>> Stashed changes
    }
}

public static void main(String[] args) {
    new ExpenseTrackerLogin();
}

<<<<<<< Updated upstream

    
=======
@Override
public void ancestorAdded(AncestorEvent event) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'ancestorAdded'");
}

@Override
public void ancestorRemoved(AncestorEvent event) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'ancestorRemoved'");
}

@Override
public void ancestorMoved(AncestorEvent event) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'ancestorMoved'");
}
>>>>>>> Stashed changes
