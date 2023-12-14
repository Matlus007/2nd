package Vendor;
 
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
 
public class VendorPageGUI extends JFrame {
    
    public VendorPageGUI() {
        setTitle("Vendor Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        // Set the background color of the content pane to white
        getContentPane().setBackground(Color.WHITE);
        // Add the slogan label
        JLabel sloganLabel = new JLabel("Welcome to Vendor Page~");
        sloganLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        sloganLabel.setForeground(Color.decode("#4169E1"));
        sloganLabel.setHorizontalAlignment(JLabel.CENTER);
        add(sloganLabel, BorderLayout.NORTH);
        // Add the picture
        ImageIcon imageIcon = new ImageIcon("Vendor.jpg"); // Replace with your image path
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setPreferredSize(new Dimension(200, 221));
        add(imageLabel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        
        JButton modifyBtn = createButton("Modify");
        JButton acceptCancelOrderBtn = createButton("Accept/Cancel Order");
        JButton updateOrderStatusBtn = createButton("Update Order Status");
        JButton checkOrderHistoryBtn = createButton("Check Order History");
        JButton readCustomerReviewBtn = createButton("Read Customer Review");
        JButton revenueDashboardBtn = createButton("Revenue Dashboard");
        
        // Set button background color
        modifyBtn.setBackground(Color.decode("#ADD8E6"));
        acceptCancelOrderBtn.setBackground(Color.decode("#ADD8E6"));
        updateOrderStatusBtn.setBackground(Color.decode("#ADD8E6"));
        checkOrderHistoryBtn.setBackground(Color.decode("#ADD8E6"));
        readCustomerReviewBtn.setBackground(Color.decode("#ADD8E6"));
        revenueDashboardBtn.setBackground(Color.decode("#ADD8E6"));
        
        
        // Add action listeners to the buttons
        modifyBtn.addActionListener((ActionEvent e) -> {
            new ModifyMenu();
            dispose();
        });
        
        acceptCancelOrderBtn.addActionListener((ActionEvent e) -> {
            new AcceptOrder();
            dispose();
        });
        
        updateOrderStatusBtn.addActionListener((ActionEvent e) -> {
            new UpdateOrderStatusGUI();
            dispose();
        });
        
        checkOrderHistoryBtn.addActionListener((ActionEvent e) -> {
            new OrderHistoryGUI();
            dispose();
        });
        
//        acceptCancelOrderBtn.addActionListener(new ButtonClickListener("Accept/Cancel Order"));
//        updateOrderStatusBtn.addActionListener(new ButtonClickListener("Update Order Status"));
//        checkOrderHistoryBtn.addActionListener(new ButtonClickListener("Check Order History"));
//        readCustomerReviewBtn.addActionListener(new ButtonClickListener("Read Customer Review"));
//        revenueDashboardBtn.addActionListener(new ButtonClickListener("Revenue Dashboard"));
//        
        
        // Create constraints for GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add spacing between buttons
        // Add buttons to the panel with constraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(modifyBtn, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        buttonPanel.add(acceptCancelOrderBtn, gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        buttonPanel.add(updateOrderStatusBtn, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        buttonPanel.add(checkOrderHistoryBtn, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        buttonPanel.add(readCustomerReviewBtn, gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        buttonPanel.add(revenueDashboardBtn, gbc);
        // Add the button panel to the frame
        add(buttonPanel, BorderLayout.SOUTH);
        // Set frame properties
        pack(); // Automatically size the frame
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private JButton createButton(String label) {
        JButton button = new JButton(label);
        button.setPreferredSize(new Dimension(200, 40)); // Set the preferred size for all buttons
        return button;
    }
    
    // Action listener class for button clicks
//    private class ButtonClickListener implements ActionListener {
//        private String operation;
//        
//        public ButtonClickListener(String operation) {
//            this.operation = operation;
//        }
//                
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            if (operation.equals("Modify")) {
//            }
//        }
//    }
     

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VendorPageGUI());
    }
}