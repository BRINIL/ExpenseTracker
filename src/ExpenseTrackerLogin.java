import javax.swing.*;
import javax.swing.table.TableModel;

import java.awt.*;
import java.awt.event.*;

public class ExpenseTrackerLogin extends JFrame implements ActionListener {
    JLabel userLabel, passwordLabel;
    JTextField userTextField;
    JPasswordField passwordField;
    JButton loginButton, resetButton, signupButton;

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

            JOptionPane.showMessageDialog(this, "New user created successfully!");
        }
    }
    
    public static void main(String[] args) {
        ExpenseTrackerLogin expenseTracker = new ExpenseTrackerLogin();
        expenseTracker.show();
    }
}


