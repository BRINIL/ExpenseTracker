import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ExpenseTrackerHomePage extends JFrame implements ActionListener {
    private JLabel titleLabel;
    private JButton newExpenseButton, viewExpensesButton;

    public ExpenseTrackerHomePage() {
        super("Expense Tracker");

        // set up form
        JPanel panel = new JPanel(new GridLayout(0, 1));
        titleLabel = new JLabel("Welcome to Expense Tracker!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel);
        newExpenseButton = new JButton("New Expense");
        newExpenseButton.addActionListener(this);
        panel.add(newExpenseButton);
        viewExpensesButton = new JButton("View Expenses");
        viewExpensesButton.addActionListener(this);
        panel.add(viewExpensesButton);

        add(panel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newExpenseButton) {
            // open new expense form
            // ExpenseTrackerNewExpenseForm newExpenseForm = new ExpenseTrackerNewExpenseForm();
        } else if (e.getSource() == viewExpensesButton) {
            // open view expenses form
            // ExpenseTrackerViewExpensesForm viewExpensesForm = new ExpenseTrackerViewExpensesForm();
        }
    }

    public static void main(String[] args) {
        new ExpenseTrackerHomePage();
    }
}
