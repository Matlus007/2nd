package Vendor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class StatusButtonRenderer extends JPanel implements TableCellRenderer {
    private JLabel label;
    private JButton button;

    public StatusButtonRenderer() {
        setLayout(new BorderLayout());
        label = new JLabel();
        button = new JButton("Change Status");
        button.setPreferredSize(new Dimension(60, 20)); // Set preferred size for the button
        add(label, BorderLayout.CENTER);
        add(button, BorderLayout.CENTER);

        // Add ActionListener to the button
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Toggle the status when the button is clicked
                if (label.getText().equals("Available")) {
                    label.setText("Sold Out");
                } else {
                    label.setText("Available");
                }
            }
        });
    }

    @Override

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // Set the label's text based on the value (assumed to be a String)
        if (value instanceof String) {
            label.setText((String) value);
        } else {
            label.setText("");
        }
        return this;
    }
}
