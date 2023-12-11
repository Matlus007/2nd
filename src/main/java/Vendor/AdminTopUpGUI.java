package Vendor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class AdminTopUpGUI {

    private static final String BALANCE_FILE_PATH = "Balance.txt";
    private JFrame frame;

    private JTextField userIDTextField;
    private JTextField topUpTextField;

    private void initialize() throws IOException {
        frame = new JFrame("Admin Top-Up");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new FlowLayout());
        JLabel userIDLabel = new JLabel("UserID:");
        userIDTextField = new JTextField(10);
        JLabel topUpLabel = new JLabel("Top up amount:");
        topUpTextField = new JTextField(10);

        centerPanel.add(userIDLabel);
        centerPanel.add(userIDTextField);
        centerPanel.add(topUpLabel);
        centerPanel.add(topUpTextField);

        JPanel southPanel = new JPanel();
        JButton backButton = new JButton("Back");
        JButton nextButton = new JButton("Next");

        backButton.setBackground(Color.decode("#ADD8E6"));
        nextButton.setBackground(Color.decode("#ADD8E6"));

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement the Back button logic if needed
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performTopUp();
            }
        });

        southPanel.add(backButton);
        southPanel.add(nextButton);

        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(southPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void performTopUp() {
        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            String enteredCustomerID = userIDTextField.getText().trim();
            double topUpAmount = Double.parseDouble(topUpTextField.getText().trim());

            Map<String, CustomerWalletData> customerDataMap = new HashMap<>();
            reader = new BufferedReader(new FileReader(BALANCE_FILE_PATH));
            String line;
            String currentCustomerID = null;
            String currentCustomerName = null;
            double currentBalance = 0.0;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                if (currentCustomerID == null) {
                    currentCustomerID = line.trim();
                } else if (currentCustomerName == null) {
                    currentCustomerName = line.trim();
                } else {
                    currentBalance = Double.parseDouble(line.trim());
                    customerDataMap.put(currentCustomerID, new CustomerWalletData(currentCustomerName, currentBalance));
                    currentCustomerID = null;
                    currentCustomerName = null;
                }
            }

            if (customerDataMap.containsKey(enteredCustomerID)) {
                CustomerWalletData customerData = customerDataMap.get(enteredCustomerID);
                double newBalance = customerData.getBalance() + topUpAmount;
                customerData.setBalance(newBalance);

                writer = new BufferedWriter(new FileWriter(BALANCE_FILE_PATH));
                for (Map.Entry<String, CustomerWalletData> entry : customerDataMap.entrySet()) {
                    writer.write(entry.getKey() + "\n");
                    writer.write(entry.getValue().getName() + "\n");
                    writer.write(entry.getValue().getBalance() + "\n\n");
                }

                // Close the current AdminTopUpGUI frame
                frame.dispose();
                
                JOptionPane.showMessageDialog(frame, "Top-up successful!","Sucess",JOptionPane.INFORMATION_MESSAGE);

                // Navigate to AdminReceiptGUI
                AdminReceiptGUI adminReceiptGUI = new AdminReceiptGUI(enteredCustomerID, topUpAmount);
                adminReceiptGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                adminReceiptGUI.setLocationRelativeTo(null);
                adminReceiptGUI.setVisible(true);

            } else {
                JOptionPane.showMessageDialog(frame, "CustomerID not found.");
            }

        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new AdminTopUpGUI().initialize();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

