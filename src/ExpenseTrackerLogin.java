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
    }
}



    
