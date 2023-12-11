package Vendor;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import javax.swing.*;

public class LoginPageUser extends JFrame {
    private JTextField userIDField;
    private JPasswordField passwordField;
    
    public LoginPageUser() {
        // Set up the JFrame
        setTitle("Main Login Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 700);
        setResizable(false);
        setLocationRelativeTo(null);

        // Create the main panel with GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        mainPanel.setBackground(Color.decode("#E6E6FA"));

        // Create slogan labels
        JLabel sloganLabel1 = new JLabel("Welcome to", SwingConstants.CENTER);
        sloganLabel1.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        // Change the color of sloganLabel1 to purple
        sloganLabel1.setForeground(Color.decode("#4169E1"));
        
        JLabel sloganLabel2 = new JLabel("University Food Ordering System", SwingConstants.CENTER);
        sloganLabel2.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        // Change the color of sloganLabel2 to purple
        sloganLabel2.setForeground(Color.decode("#000080"));
        
        // Create a space label
        JLabel spaceLabel1 = new JLabel(" ");
        JLabel spaceLabel2 = new JLabel(" ");

        // Create a picture label
        ImageIcon imageIcon = new ImageIcon("Figure.jpg"); // Replace with your image path
        JLabel pictureLabel = new JLabel(imageIcon);
        pictureLabel.setPreferredSize(new Dimension(300, 300)); // Adjust the size as needed
        
        // Create labels for User ID and Password
        JLabel userIDLabel = new JLabel("User ID:");
        userIDLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        userIDField = new JTextField(20); // Adjust the size as needed
        userIDField.setFont(new Font("Times New Roman", Font.PLAIN, 15));

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        passwordField = new JPasswordField(20); // Adjust the size as needed
        passwordField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        
        // Create Login and Exit buttons
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(Color.decode("#ffffff"));
        loginButton.setForeground(Color.decode("#8FBC8F"));
        loginButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));

        JButton exitButton = new JButton("Exit");
        exitButton.setBackground(Color.decode("#ffffff"));
        exitButton.setForeground(Color.decode("#FF7FAC"));
        exitButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));

        // Add action listener for the Login button
        loginButton.addActionListener(e -> {
            String userID = userIDField.getText();
            String password = new String(passwordField.getPassword());

            if (userID.equals("Ad001") && password.equals("1234")) {
                // Redirect to the admin page
                navigateToAdminPage(userID);
            } else if (checkLogin(userID, password)) {
                // Redirect to the appropriate page based on user type
                navigateToUserPage(userID);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid user ID or password. Please try again.");
            }
        });

        // Add action listener for the Exit button
        exitButton.addActionListener(e -> System.exit(0));

        // Add components to the main panel with proper positioning
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(sloganLabel1, gbc);
        mainPanel.add(sloganLabel2, gbc);
        mainPanel.add(spaceLabel1, gbc);
        mainPanel.add(pictureLabel, gbc);
        mainPanel.add(spaceLabel2, gbc);
        mainPanel.add(userIDLabel, gbc);
        mainPanel.add(userIDField, gbc);
        mainPanel.add(passwordLabel, gbc);
        mainPanel.add(passwordField, gbc);

        // Create a button panel for Login and Exit buttons with spacing
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.decode("#E6E6FA"));
        buttonPanel.add(loginButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(20, 0))); // Add spacing
        buttonPanel.add(exitButton);
        
        // Add the main panel and button panel to the JFrame
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Set JFrame visibility
        setVisible(true);
    }

    // Add a field to store the user type
private String userType;

// Modify the checkLogin method
private boolean checkLogin(String userID, String password) {
    try (Scanner scanner = new Scanner(new File("User.txt"))) {
        while (scanner.hasNext()) {
            String timeAndDate = scanner.nextLine();
            userType = scanner.nextLine(); // Store the user type
            String storedUserID = scanner.nextLine();
            String username = scanner.nextLine();
            String storedPassword = scanner.nextLine();
            String contactNumber = scanner.nextLine();

            // Skip the space line
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            if (storedUserID.equals(userID) && storedPassword.equals(password)) {
                return true;
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "An error occurred while reading user data.");
    }

    return false;
}

    private void navigateToAdminPage(String userID) {
        // Implement navigation to the admin's page
        // You can open a new window or perform any other actions here.
        JOptionPane.showMessageDialog(null, "Welcome, Admin!");
            // Open the AdminPageGUI when the administrator logs in
        dispose();
        
        SwingUtilities.invokeLater(() -> new AdminPageGUI());
    }
    
    private void navigateToUserPage(String userID) {
        // Implement navigation to the user's page based on the user ID
        // You can open a new window or perform any other actions here.
        JOptionPane.showMessageDialog(null, "Welcome, User " + userID + "!");
    }
}