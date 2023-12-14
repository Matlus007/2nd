package assignment.Admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class AdminPageGUI extends JFrame {

    public AdminPageGUI() {
        setTitle("Admin Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Add the slogan label
        JLabel sloganLabel = new JLabel("Welcome to Admin Page~");
        sloganLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        sloganLabel.setForeground(Color.decode("#4169E1"));
        sloganLabel.setHorizontalAlignment(JLabel.CENTER);
        add(sloganLabel, BorderLayout.NORTH);

        // Add the picture
        ImageIcon imageIcon = new ImageIcon("Admin.png"); // Replace with your image path
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setPreferredSize(new Dimension(200, 200));
        add(imageLabel, BorderLayout.CENTER);

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Create buttons
        JButton modifyBtn = new JButton("Modify");
        JButton topUpBtn = new JButton("Top-Up");
        JButton logoutBtn = new JButton("Log-Out");

        // Add action listeners to the buttons
        modifyBtn.addActionListener(new ButtonClickListener("Modify"));
        topUpBtn.addActionListener(new ButtonClickListener("Top-Up"));
        
        // Add action listener for the "Log-Out" button
        logoutBtn.addActionListener(e -> {
        // Close the current AdminPageGUI window
        dispose();
        // Open a new instance of LoginPageUser
        SwingUtilities.invokeLater(() -> new LoginPageUser());
        });

        // Set button background color
        modifyBtn.setBackground(Color.decode("#ADD8E6"));
        topUpBtn.setBackground(Color.decode("#ADD8E6"));
        logoutBtn.setBackground(Color.decode("#ADD8E6"));

        // Add buttons to the panel
        buttonPanel.add(modifyBtn);
        buttonPanel.add(Box.createRigidArea(new Dimension(20, 0))); // Add spacing
        buttonPanel.add(topUpBtn);
        buttonPanel.add(Box.createRigidArea(new Dimension(20, 0))); // Add spacing
        buttonPanel.add(logoutBtn);

        // Add the button panel to the frame
        add(buttonPanel, BorderLayout.SOUTH);

        // Set frame properties
        pack(); // Automatically size the frame
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private static class AdminTopUpGUI {

        public AdminTopUpGUI() {
        }
    }

    // Action listener class for button clicks
    private class ButtonClickListener implements ActionListener {
        private String operation;

        public ButtonClickListener(String operation) {
            this.operation = operation;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (operation) {
                case "Modify":
                    dispose();
                    // Open the AdminManageGUI when "Modify" button is clicked
                    new AdminManageGUI();
                    break;
                case "Top-Up":
                     new assignment.Admin.AdminTopUpGUI();
                    System.out.print("yes");
                    dispose();
                    break;
                case "Log-Out":
                    // Exit the program when "Log-Out" button is clicked
                    new LoginPageUser();
                default:
                    JOptionPane.showMessageDialog(null, "Executing " + operation + " operation...");
                    break;
            }
        }
    }

    private boolean userExists(String userId) {
        try {
            Scanner scanner = new Scanner(new File("Balance.txt"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
 
                // Debugging statement to print each line and its parts
                System.out.println("Line: " + line);
                System.out.println("Parts: " + Arrays.toString(parts));
 
                // Check if parts array has at least 3 elements and the third element matches the userId
                if (parts[0].trim().equals(userId.trim())) {
                    scanner.close();
                    return true;
                }
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminPageGUI());
    }
}