package Vendor;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class AcceptOrder extends JFrame {

    private DefaultTableModel dineInTableModel;
    private DefaultTableModel takeAwayTableModel;
    private DefaultTableModel deliveryTableModel;

    private JTable dineInOrderTable;
    private JTable takeAwayOrderTable;
    private JTable deliveryOrderTable;

    private JButton acceptButton;
    private JButton cancelButton;
    private JButton viewDetailsButton;
    private JButton backButton;
    
    public AcceptOrder() {
        setTitle("Vendor Order Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        dineInTableModel = new DefaultTableModel(new Object[]{"Order ID", "Username", "Phone Number", "Time", "Status"}, 0);
        takeAwayTableModel = new DefaultTableModel(new Object[]{"Order ID", "Username", "Phone Number", "Time", "Status"}, 0);
        deliveryTableModel = new DefaultTableModel(new Object[]{"Order ID", "Username", "Phone Number", "Time", "Address", "Status"}, 0);

        dineInOrderTable = new JTable(dineInTableModel);
        takeAwayOrderTable = new JTable(takeAwayTableModel);
        deliveryOrderTable = new JTable(deliveryTableModel);
        centerAlignTable.centerAlignTable(dineInOrderTable);
        centerAlignTable.centerAlignTable(takeAwayOrderTable);
        centerAlignTable.centerAlignTable(deliveryOrderTable);        

        JScrollPane dineInScrollPane = new JScrollPane(dineInOrderTable);
        JScrollPane takeAwayScrollPane = new JScrollPane(takeAwayOrderTable);
        JScrollPane deliveryScrollPane = new JScrollPane(deliveryOrderTable);

        tabbedPane.addTab("Dine In", dineInScrollPane);
        tabbedPane.addTab("Take Away", takeAwayScrollPane);
        tabbedPane.addTab("Delivery", deliveryScrollPane);

        acceptButton = new JButton("Accept");
        cancelButton = new JButton("Cancel");
        backButton = new JButton("Back"); // Initialize the new back button
        viewDetailsButton = new JButton("View Details");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(acceptButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(backButton);
        buttonPanel.add(viewDetailsButton);
        
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VendorPageGUI();
                dispose();
            }
        });
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStatus("Accepted");
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStatus("Cancelled");
            }
        });

        viewDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewOrderDetails();
            }
        });

        add(buttonPanel, BorderLayout.SOUTH);
        add(tabbedPane, BorderLayout.CENTER);

        loadOrdersFromFile("CustomerDetailDineInOrder.txt", dineInTableModel);
        loadOrdersFromFile("CustomerDetailTakeAwayOrder.txt", takeAwayTableModel);
        loadDeliveryOrdersFromFile("CustomerDetailDeliveryInOrder.txt", deliveryTableModel);

        // Save all orders to "AllOrders.txt"
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateStatus(String newStatus) {
        JTable selectedTable = getCurrentTable();
        int selectedRow = selectedTable.getSelectedRow();

        if (selectedRow != -1) {
            DefaultTableModel tableModel = (DefaultTableModel) selectedTable.getModel();
            String orderId = tableModel.getValueAt(selectedRow, 0).toString();
            String currentStatus = tableModel.getValueAt(selectedRow, tableModel.getColumnCount() - 1).toString();

            if (!currentStatus.equals("Cancelled") && !currentStatus.equals("Accepted")) {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter("AllOrders.txt", true))) {
                    saveOrderToFile(bw, orderId, newStatus);
                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle the exception (e.g., display an error message)
                }

                tableModel.setValueAt(newStatus, selectedRow, tableModel.getColumnCount() - 1);
                tableModel.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "Status cannot be changed from " + currentStatus + " to " + newStatus, "Status Change Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadOrdersFromFile(String filename, DefaultTableModel tableModel) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String orderId = readLineOrDefault(br).trim();
            String username = readLineOrDefault(br).trim();
            String contact = readLineOrDefault(br).trim();
            String time = readLineOrDefault(br).trim();
            String status = readLineOrDefault(br).trim();  // Read the status from the file

            // Check if all necessary values are present before adding a row
            if (!orderId.isEmpty() && !username.isEmpty() && !contact.isEmpty() && !time.isEmpty()) {
                Object[] rowData = new Object[]{orderId, username, contact, time, status};
                tableModel.addRow(rowData);

                // Find the index of the last column (status) and set the value in the table
                int statusColumnIndex = tableModel.findColumn("Status");
                int lastRowIndex = tableModel.getRowCount() - 1;
                tableModel.setValueAt(status, lastRowIndex, statusColumnIndex);
            }

        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (e.g., display an error message)
        }
    }

    private void loadDeliveryOrdersFromFile(String filename, DefaultTableModel tableModel) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String orderId = readLineOrDefault(br).trim();
            String username = readLineOrDefault(br).trim();
            String contact = readLineOrDefault(br).trim();
            String time = readLineOrDefault(br).trim();
            String address = readLineOrDefault(br).trim();
            String status = readLineOrDefault(br).trim();  // Read the status from the file

            // Check if all necessary values are present before adding a row
            if (!orderId.isEmpty() && !username.isEmpty() && !contact.isEmpty() && !time.isEmpty() && !address.isEmpty()) {
                Object[] rowData = new Object[]{orderId, username, contact, time, address, status};
                tableModel.addRow(rowData);

                // Find the index of the last column (status) and set the value in the table
                int statusColumnIndex = tableModel.findColumn("Status");
                int lastRowIndex = tableModel.getRowCount() - 1;
                tableModel.setValueAt(status, lastRowIndex, statusColumnIndex);
            }

        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (e.g., display an error message)
        }
    }



    private void saveOrderToFile(BufferedWriter bw, String orderId, String status) throws IOException {
        bw.write(orderId + "\t" + status);
        bw.newLine();
    }


    private String readLineOrDefault(BufferedReader br) throws IOException {
        String line = br.readLine();
        return (line != null) ? line : "";
    }

    private JTable getCurrentTable() {
        JTabbedPane tabbedPane = (JTabbedPane) getContentPane().getComponent(1);
        JScrollPane scrollPane = (JScrollPane) tabbedPane.getSelectedComponent();
        return ((JTable) scrollPane.getViewport().getView());
    }
    
    // Inside your AcceptOrder class

    // Add this method to handle the "View Details" button click
    private void viewOrderDetails() {
        JTable selectedTable = getCurrentTable();
        int selectedRow = selectedTable.getSelectedRow();

        if (selectedRow != -1) {
            DefaultTableModel tableModel = (DefaultTableModel) selectedTable.getModel();
            String orderId = tableModel.getValueAt(selectedRow, 0).toString();

            // Call a method to display order details
            displayOrderDetails(orderId);
        }
    }

    // Add this method to display order details in a new window or dialog
    private void displayOrderDetails(String orderId) {
        // Read details from the corresponding item detail file
        String itemDetailFileName = getItemDetailFileName(orderId);
        StringBuilder details = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(itemDetailFileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                details.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (e.g., display an error message)
        }

        // Display the details in a new window or dialog
        JTextArea detailsTextArea = new JTextArea(details.toString());
        JScrollPane detailsScrollPane = new JScrollPane(detailsTextArea);
        detailsScrollPane.setPreferredSize(new Dimension(400, 300));

        JOptionPane.showMessageDialog(this, detailsScrollPane, "Order Details", JOptionPane.PLAIN_MESSAGE);
    }

    // Add this method to determine the item detail file based on the order type
    private String getItemDetailFileName(String orderId) {
        JTabbedPane tabbedPane = (JTabbedPane) getContentPane().getComponent(1);
        int selectedIndex = tabbedPane.getSelectedIndex();

        switch (selectedIndex) {
            case 0: // Dine In
                return "CustomerDineInOrderDetail.txt";
            case 1: // Take Away
                return "CustomerTakeAwayOrderDetail.txt";
            case 2: // Delivery
                return "CustomerDeliveryOrderDetail.txt";
            default:
                throw new IllegalArgumentException("Invalid tab index");
        }
    }
}