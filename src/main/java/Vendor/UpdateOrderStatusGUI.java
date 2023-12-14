package Vendor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class UpdateOrderStatusGUI extends JFrame {
    private JTable orderTable;
    private JButton updateButton;
    private JButton backButton;
    private boolean initialized = false; // Add this variable to track initialization

    public UpdateOrderStatusGUI() {
        setTitle("Update Order Status");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        initializeComponents();
        if (!initialized) {
            loadOrderData();
            initialized = true; // Mark as initialized
        }
        
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Save data when the window is closing
                saveOrderData();
            }
        });
    }

    private void initializeComponents() {
        // Create the table with two columns: OrderId and Status
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("OrderId");
        model.addColumn("Status");
        orderTable = new JTable(model);
        centerAlignTable.centerAlignTable(orderTable);

        // Create a button for updating the status
        updateButton = new JButton("Update Status");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStatus();
            }
        });

        // Create a back button
        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VendorPageGUI();
                saveOrderData(); // Save data before going back
                dispose();
            }
        });

        // Set up the layout
        setLayout(new BorderLayout());
        add(new JScrollPane(orderTable), BorderLayout.CENTER);

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(updateButton);
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadOrderData() {
        // Read data from the UpdatedOrders.txt file and populate the table
        try (BufferedReader reader = new BufferedReader(new FileReader("UpdatedOrders.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line based on a tab
                String[] parts = line.trim().split("\t");

                // Use the first part as orderId
                String orderId = parts[0];
                String status = parts[1];

                // Add data to the table
                DefaultTableModel model = (DefaultTableModel) orderTable.getModel();
                model.addRow(new Object[]{orderId, status});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateStatus() {
        // Get the selected row and column
        int selectedRow = orderTable.getSelectedRow();
        int statusColumn = 1; // assuming the "Status" column is the second column

        if (selectedRow != -1) {
            // Check if the current status is "preparing"
            String currentStatus = orderTable.getValueAt(selectedRow, statusColumn).toString();
            if (currentStatus.equals("preparing")) {
                // Update the status to "done"
                orderTable.setValueAt("done", selectedRow, statusColumn);
            } else {
                JOptionPane.showMessageDialog(this, "Order status is already 'done'.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to update.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveOrderData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("UpdatedOrders.txt"))) {
            DefaultTableModel model = (DefaultTableModel) orderTable.getModel();
            int rowCount = model.getRowCount();

            for (int i = 0; i < rowCount; i++) {
                String orderId = model.getValueAt(i, 0).toString();
                String status = model.getValueAt(i, 1).toString();
                String line = orderId + "\t" + status;
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving order data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

