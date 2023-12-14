package Vendor;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class ModifyMenu implements ActionListener {
    private static ArrayList<Product> products;
    private JFrame frame;
    private JPanel panelTop, panelCenter, panelBottom;
    private JButton addButton, editButton, deleteButton, quitButton;
    private JLabel nameLabel, priceLabel;
    private JTextField nameTextField, priceTextField;
    private DefaultTableModel tableModel;
    private JTable table;

    public ModifyMenu() {
        
        try {
            // Load product data from a file (modify the file name as needed)
            Scanner scanner = new Scanner(new File("products.txt"));
            products = new ArrayList<Product>();
            while (scanner.hasNext()) {
                String name = scanner.nextLine();
                double price = Double.parseDouble(scanner.nextLine());
                String status = scanner.nextLine();
                scanner.nextLine();
                Product product = new Product(name, price);
                product.setStatus(status);
                products.add(product);
            }
            scanner.close();
        } catch (Exception e) {
            products = new ArrayList<Product>();
        }

        saveProductsToFile();  // Save initial state to file
        
        frame = new JFrame("Vendor Products System");
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        panelTop = new JPanel(new GridLayout(2, 2));
        nameLabel = new JLabel("Product Name:");
        priceLabel = new JLabel("Product Price:");
        nameTextField = new JTextField(20);
        priceTextField = new JTextField(20);

        panelTop.add(nameLabel);
        panelTop.add(priceLabel);
        panelTop.add(nameTextField);
        panelTop.add(priceTextField);

        panelCenter = new JPanel();
        int size = products.size();
        String[][] data = new String[size][3]; // Changed size to 4 for the new "Access" column
        for (int i = 0; i < size; i++) {
            Product product = products.get(i);
            data[i][0] = product.getName();
            data[i][1] = String.valueOf(product.getPrice());
            data[i][2] = product.getStatus();
        }
        String[] columnNames = { "Product Name", "Product Price", "Status", "Access" }; 
        tableModel = new DefaultTableModel(data, columnNames);
        table = new JTable(tableModel);
        centerAlignTable.centerAlignTable(table);
        
        // Customize the column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(150);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(3).setPreferredWidth(200);
        
        JScrollPane scrollPane = new JScrollPane(table);
        // Assuming this code is inside an ActionListener in the NewVersion class
        // Assuming this code is inside the NewVersion constructor or a method
        StatusButtonEditor statusButtonEditor = new StatusButtonEditor(table, products, new JCheckBox(), this, tableModel);
        table.getColumnModel().getColumn(3).setCellEditor(statusButtonEditor);
        table.getColumnModel().getColumn(3).setCellRenderer(new StatusButtonRenderer());
        panelCenter.add(scrollPane);

        panelBottom = new JPanel();
        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        quitButton = new JButton("Quit");

        addButton.addActionListener(this);
        editButton.addActionListener(this);
        deleteButton.addActionListener(this);
        quitButton.addActionListener(this);

        panelBottom.add(addButton);
        panelBottom.add(editButton);
        panelBottom.add(deleteButton);
        panelBottom.add(quitButton);

        frame.add(panelTop, BorderLayout.NORTH);
        frame.add(panelCenter, BorderLayout.CENTER);
        frame.add(panelBottom, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == quitButton) {
            saveProductsToFile(); // Save product data to file before exiting
            new VendorPageGUI();
            frame.dispose();
        } else if (ae.getSource() == addButton) {
            String name = nameTextField.getText();
            double price = Double.parseDouble(priceTextField.getText());
            boolean found = false;

            for (Product product : products) {
                if (name.equals(product.getName())) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                Product newProduct = new Product(name, price);
                newProduct.setStatus("Available"); // Set the status to "Available"
                products.add(newProduct); 
                tableModel.addRow(new Object[] { name, price, newProduct.getStatus() });
                nameTextField.setText("");
                priceTextField.setText("");
            } else {
                JOptionPane.showMessageDialog(frame, "Product already exists!");
            }
        } else if (ae.getSource() == editButton) {
            String name = nameTextField.getText();
            double price = Double.parseDouble(priceTextField.getText());
            boolean found = false;
            int rowToUpdate = -1;

            for (int i = 0; i < products.size(); i++) {
                Product product = products.get(i);
                if (name.equals(product.getName())) {
                    found = true;
                    rowToUpdate = i;
                    break;
                }
            }

            if (found && rowToUpdate != -1) {
                products.get(rowToUpdate).setPrice(price);
                tableModel.setValueAt(price, rowToUpdate, 1);
                nameTextField.setText("");
                priceTextField.setText("");
            } else {
                JOptionPane.showMessageDialog(frame, "Product not found!");
            }
        } else if (ae.getSource() == deleteButton) {
            String name = nameTextField.getText();
            boolean found = false;
            int rowToRemove = -1;

            for (int i = 0; i < products.size(); i++) {
                Product product = products.get(i);
                if (name.equals(product.getName())) {
                    found = true;
                    rowToRemove = i;
                    products.remove(i);
                    break;
                }
            }

            if (found && rowToRemove != -1) {
                tableModel.removeRow(rowToRemove);
                nameTextField.setText("");
                priceTextField.setText("");
            } else {
                JOptionPane.showMessageDialog(frame, "Product not found!");
            }
        }
    }
    public void saveProductsToFile() {
        try {
            PrintWriter writer = new PrintWriter("products.txt");
            for (Product product : products) {
                writer.println(product.getName());
                writer.println(product.getPrice());
                writer.println(product.getStatus()); // Save the correct status
                writer.println();
            }
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}