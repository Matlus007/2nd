package Vendor;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AdminReceiptGUI extends JFrame {
    private JTextField userIDField;
    private JTextField userNameField;
    private JTextField totalAmountField;
    private String userID;
    private double topUpAmount;

    public AdminReceiptGUI() {
        setTitle("Transaction Receipt Generator");
        setSize(400, 200);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        JLabel userIDLabel = new JLabel("UserID:");
        userIDField = new JTextField(userID);
        userIDField.setEditable(false);
        JLabel userNameLabel = new JLabel("Customer Name:");
        userNameField = new JTextField();
        userNameField.setEditable(false);
        JLabel totalAmountLabel = new JLabel("Total Amount (RM):");
        totalAmountField = new JTextField(String.valueOf(topUpAmount));
        totalAmountField.setEditable(false);

        inputPanel.add(userIDLabel);
        inputPanel.add(userIDField);
        inputPanel.add(userNameLabel);
        inputPanel.add(userNameField);
        inputPanel.add(totalAmountLabel);
        inputPanel.add(totalAmountField);

        JPanel buttonPanel = new JPanel();
        JButton generateButton = new JButton("Generate Receipt");
        buttonPanel.add(generateButton);

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        generateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generateReceipt();
            }
        });

        add(mainPanel);
    }

    AdminReceiptGUI(String enteredCustomerID, double topUpAmount) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void generateReceipt() {
        String customerName = userNameField.getText();
        String totalAmountText = totalAmountField.getText();
        double totalAmount = 0.0;

        try {
            totalAmount = Double.parseDouble(totalAmountText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid total amount. Please enter a valid number.");
            return;
        }

        // Create a new frame to display the receipt details
        JFrame receiptFrame = new JFrame("Receipt");
        receiptFrame.setSize(300, 600); // Set the frame size to (250, 600)
        receiptFrame.setLayout(new BorderLayout());

        // Create a JTextArea with center alignment
        JTextArea receiptTextArea = new JTextArea();
        receiptTextArea.setEditable(false);
        receiptTextArea.setFont(new Font("Times New Roman", Font.PLAIN, 14)); // Set font and size
        receiptTextArea.setForeground(Color.BLACK); // Set text color

        // Center-align text in the JTextArea
        receiptTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        receiptTextArea.setAlignmentY(Component.CENTER_ALIGNMENT);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String transactionDate = dateFormat.format(new Date());

        StringBuilder receiptText = new StringBuilder();
        receiptText.append("University Food Ordering System\n");
        receiptText.append("Jalan Teknologi 5, Taman Teknologi Malaysia,\n");
        receiptText.append("57000 Kuala Lumpur,\n");
        receiptText.append("Wilayah Persekutuan Kuala Lumpur\n");
        receiptText.append("Tel:03-8996 1000\n");
        receiptText.append("www.apu.com\n\n\n");
        receiptText.append("Transaction Receipt\n");
        receiptText.append("-----------------------------------------\n");
        receiptText.append("Date: ").append(transactionDate).append("\n");
        receiptText.append("Customer: ").append(customerName).append("\n");
        receiptText.append("Total Reload Amount: RM").append(String.format("%.2f", totalAmount)).append("\n");
        receiptText.append("Balance: RM\n");
        receiptText.append("Thank you!");

        receiptTextArea.setText(receiptText.toString());

        receiptFrame.add(receiptTextArea, BorderLayout.CENTER);
        receiptFrame.setLocationRelativeTo(this); // Center receiptFrame relative to AdminReceiptGeneratorFrame
        receiptFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new AdminReceiptGUI();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
