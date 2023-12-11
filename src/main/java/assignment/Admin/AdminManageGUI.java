package assignment.Admin;
 
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;


public class AdminManageGUI implements ActionListener {
    private static ArrayList<Admin_ManageUserInfo> users;
    private JFrame frame;
    private JPanel mainPanel, inputPanel, tablePanel, buttonPanel;
    private JButton registerButton, modifyButton, deleteButton, exitButton;
    private JLabel timeDateLabel, userTypeLabel, userIdLabel, usernameLabel, passwordLabel, contactNumberLabel;
    private JTextField timeDateTextField, userTypeTextField, userIdTextField, usernameTextField, passwordField, contactNumberTextField;
    private JTable detailsTable;
    private DefaultTableModel tableModel;
    private AdminPageGUI adminPageGUI; // Reference to the AdminPageGUI
 
    public AdminManageGUI() {
        frame = new JFrame("Admin Management System");
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        mainPanel = new JPanel(new BorderLayout());
 
        inputPanel = new JPanel(new GridLayout(6, 2)); // Updated to accommodate the Contact Number field
        tablePanel = new JPanel(new BorderLayout());
        buttonPanel = new JPanel();
 
        // Initialize labels
        timeDateLabel = createLabel("Date & Time:");
        userTypeLabel = createLabel("User Type:");
        userIdLabel = createLabel("User ID:");
        usernameLabel = createLabel("Username:");
        passwordLabel = createLabel("Password:");
        contactNumberLabel = createLabel("Contact Number:");
 
        // Initialize text fields
        timeDateTextField = createTextField(20);
        userTypeTextField = createTextField(20);
        userIdTextField = createTextField(20);
        usernameTextField = createTextField(20);
        passwordField = createTextField(20);
        contactNumberTextField = createTextField(20);
        timeDateTextField.setEditable(false);
        userTypeTextField.setEditable(false);
        
       // Add labels and text fields to input panel
        inputPanel.add(timeDateLabel);
        inputPanel.add(timeDateTextField);
        inputPanel.add(userTypeLabel);
        inputPanel.add(userTypeTextField);
        inputPanel.add(userIdLabel);
        inputPanel.add(userIdTextField);
        inputPanel.add(usernameLabel);
        inputPanel.add(usernameTextField);
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordField);
        inputPanel.add(contactNumberLabel);
        inputPanel.add(contactNumberTextField); // Add the formatted text field to the input panel

        // Add a title label above the table, centered and with Royal Blue color
        JLabel titleLabel = new JLabel("Users' Information");
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center align text
        titleLabel.setForeground(Color.decode("#4169E1")); // Set text color to Royal Blue
        tablePanel.add(titleLabel, BorderLayout.NORTH);

        // Add an empty border to create space before the table
        tablePanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

        // Initialize details table
        String[] columnNames = {"Date & Time", "User Type", "User ID", "Username", "Password", "Contact Number"};
        tableModel = new DefaultTableModel(columnNames, 0);
        detailsTable = new JTable(tableModel);
        detailsTable.getColumnModel().getColumn(0).setPreferredWidth(140);
        detailsTable.getColumnModel().getColumn(3).setPreferredWidth(110);
        JScrollPane scrollPane = new JScrollPane(detailsTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        try {
            MaskFormatter maskFormatter = new MaskFormatter("###-#######");
            maskFormatter.setPlaceholderCharacter('_');

            // Create a formatter that only accepts integers
            NumberFormatter numberFormatter = new NumberFormatter();
            numberFormatter.setValueClass(Integer.class);
            numberFormatter.setMinimum(0);
            numberFormatter.setMaximum(Integer.MAX_VALUE);
            numberFormatter.setAllowsInvalid(false); // Disallows non-integer input

            // Apply the number formatter to the formatted text field for contact number
            JFormattedTextField formattedContactNumberField = new JFormattedTextField(new DefaultFormatterFactory(maskFormatter));
            formattedContactNumberField.setColumns(20);
            formattedContactNumberField.setFormatterFactory(new DefaultFormatterFactory(numberFormatter));
            contactNumberTextField = formattedContactNumberField;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Object maskFormatter = null;
        
        contactNumberTextField = new JFormattedTextField(maskFormatter);
        contactNumberTextField.setColumns(20);
        contactNumberTextField.setEditable(true); // Allow editing of the formatted text
        
 
        // Initialize buttons
        registerButton = new JButton("Register");
        modifyButton = new JButton("Modify");
        deleteButton = new JButton("Delete");
        exitButton = new JButton("Exit");
        
        registerButton.setBackground(Color.decode("#ADD8E6"));
        modifyButton.setBackground(Color.decode("#ADD8E6"));
        deleteButton.setBackground(Color.decode("#ADD8E6"));
        exitButton.setBackground(Color.decode("#ADD8E6"));

        registerButton.setForeground(Color.BLACK);
        modifyButton.setForeground(Color.BLACK);
        deleteButton.setForeground(Color.BLACK);
        exitButton.setForeground(Color.BLACK);
 
        // Add action listeners to buttons
        registerButton.addActionListener(this);
        modifyButton.addActionListener(this);
        deleteButton.addActionListener(this);
        exitButton.addActionListener(this);
 
        // Add buttons to button panel
        buttonPanel.add(registerButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(exitButton);
 
        // Add panels to the main panel
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
 
        // Add the main panel to the frame
        frame.add(mainPanel);
 
        // Make the frame visible
        frame.setVisible(true);
 
        // Initialize the user list and display existing data
        users = loadUsersFromFile();
        displayUsers();
        updateTimeDate(); // Update date and time initially
    }
 
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        return label;
    }
 
    private JTextField createTextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        return textField;
    }
 
    private void updateTimeDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        Date date = new Date();
        String formattedDate = dateFormat.format(date);
        timeDateTextField.setText(formattedDate);
    }
 
    private String detectUserType(String userId) {
        if (userId.startsWith("Ad")) {
            return "Admin";
        } else if (userId.startsWith("Tp")) {
            return "Customer";
        } else if (userId.startsWith("V")) {
            return "Vendor";
        } else if (userId.startsWith("R")) {
            return "Runner";
        }
        return "Unknown";
    }
 
    private ArrayList<Admin_ManageUserInfo> loadUsersFromFile() {
        ArrayList<Admin_ManageUserInfo> loadedUsers = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File("User.txt"));
            while (scanner.hasNextLine()) {
                String timeDate = scanner.nextLine();
                String userType = scanner.nextLine();
                String userId = scanner.nextLine();
                String username = scanner.nextLine();
                String password = scanner.nextLine();
                String contactNumber = scanner.nextLine();
                if (scanner.hasNextLine()) {
                    scanner.nextLine(); // Consume the empty line
                }
                Admin_ManageUserInfo user = new Admin_ManageUserInfo(timeDate, userType, userId, username, password, contactNumber);
                loadedUsers.add(user);
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loadedUsers;
    }
 
    private void displayUsers() {
        for (Admin_ManageUserInfo user : users) {
            tableModel.addRow(new String[]{user.getTimeDate(), user.getUserType(), user.getUserId(), user.getUsername(), user.getPassword(), user.getContactNumber()});
        }
    }
 
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == exitButton) {
            try {
                PrintWriter writer = new PrintWriter("User.txt");
                for (Admin_ManageUserInfo user : users) {
                    writer.println(user.getTimeDate());
                    writer.println(user.getUserType());
                    writer.println(user.getUserId());
                    writer.println(user.getUsername());
                    writer.println(user.getPassword());
                    writer.println(user.getContactNumber());
                    writer.println();
                }
                writer.close();
                System.exit(0);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (ae.getSource() == registerButton) {
            String timeDate = timeDateTextField.getText();
            String userId = userIdTextField.getText();
            String username = usernameTextField.getText();
            String password = passwordField.getText();
            String contactNumber = contactNumberTextField.getText();
            boolean found = false;
 
            for (Admin_ManageUserInfo user : users) {
                if (userId.equals(user.getUserId())) {
                    found = true;
                    break;
                }
            }
 
            if (!found) {
                String userType = detectUserType(userId);
                Admin_ManageUserInfo newUser = new Admin_ManageUserInfo(timeDate, userType, userId, username, password, contactNumber);
                users.add(newUser);
 
                tableModel.addRow(new String[]{timeDate, userType, userId, username, password, contactNumber});
 
                updateTimeDate();
                userTypeTextField.setText(userType);
                userIdTextField.setText("");
                usernameTextField.setText("");
                passwordField.setText("");
                contactNumberTextField.setText("");
            } else {
                JOptionPane.showMessageDialog(frame, "User already exists!");
            }
        } else if (ae.getSource() == modifyButton) {
            String userId = userIdTextField.getText();
            String newPassword = passwordField.getText();
            String newContactNumber = contactNumberTextField.getText();
 
            boolean found = false;
 
            for (int i = 0; i < users.size(); i++) {
                Admin_ManageUserInfo user = users.get(i);
                if (userId.equals(user.getUserId())) {
                    found = true;
                    user.setPassword(newPassword);
                    user.setContactNumber(newContactNumber);
 
                    tableModel.setValueAt(newPassword, i, 4); // Update the password column
                    tableModel.setValueAt(newContactNumber, i, 5); // Update the contact number column
 
                    userIdTextField.setText("");
                    usernameTextField.setText("");
                    passwordField.setText("");
                    contactNumberTextField.setText("");
                    break;
                }
            }
            if (!found) {
                JOptionPane.showMessageDialog(frame, "User not found!");
            }
        } else if (ae.getSource() == deleteButton) {
            String userIdToDelete = userIdTextField.getText();
 
            boolean found = false;
            for (int i = 0; i < users.size(); i++) {
                Admin_ManageUserInfo user = users.get(i);
                if (userIdToDelete.equals(user.getUserId())) {
                    found = true;
                    users.remove(i);
                    tableModel.removeRow(i);
                    updateTimeDate();
                    userTypeTextField.setText("");
                    userIdTextField.setText("");
                    usernameTextField.setText("");
                    passwordField.setText("");
                    contactNumberTextField.setText("");
                    break;
                }
            }
            if (!found) {
                JOptionPane.showMessageDialog(frame, "User not found!");
            }
        }
    }

    public void showAdminManageGUI() {
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        new AdminManageGUI();
    }
}