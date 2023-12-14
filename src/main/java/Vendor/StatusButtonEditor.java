package Vendor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class StatusButtonEditor extends DefaultCellEditor {
    private JPanel panel;
    private JButton button;
    private String status;
    private boolean isButtonColumnEditor;
    private JTable table;
    private ArrayList<Product> products;
    private ModifyMenu newVersion;
    private DefaultTableModel tableModel;

    public StatusButtonEditor(JTable table, ArrayList<Product> products, JCheckBox checkBox, ModifyMenu newVersion, DefaultTableModel tableModel) {
        super(checkBox);
        this.table = table;
        this.products = products;
        this.newVersion = newVersion;
        this.tableModel = tableModel; 

        panel = new JPanel(new BorderLayout());
        button = new JButton("Change Status");
        button.setPreferredSize(new Dimension(80, 20));
        panel.add(button, BorderLayout.CENTER); // Place the button in the center
        panel.setOpaque(true);

        button.addActionListener(e -> {
            int row = table.convertRowIndexToModel(table.getEditingRow());
            Product product = products.get(row);

            if ("Available".equals(product.getStatus())) {
                product.setStatus("Sold Out");
            } else if ("Sold Out".equals(product.getStatus())) {
                product.setStatus("Available");
            }

            // Update the status value in the model, assuming the status column is at index 2
            tableModel.setValueAt(product.getStatus(), row, 2);

            // Save changes to file
            newVersion.saveProductsToFile();

            // Stop editing
            fireEditingStopped();
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value != null) {
            status = value.toString();
        } else {
            status = ""; // Provide a default value or handle it as needed
        }
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return status;
    }
}
