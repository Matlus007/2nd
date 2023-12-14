package Vendor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OrderHistoryGUI extends JFrame {
    private JTextArea dineInTextArea, takeAwayTextArea, deliveryTextArea;

    public OrderHistoryGUI() {
        super("Order History");

        JTabbedPane tabbedPane = new JTabbedPane();
        setSize(500,300);

        dineInTextArea = createOrderTextArea();
        JScrollPane dineInScrollPane = new JScrollPane(dineInTextArea);
        tabbedPane.addTab("Dine-In", dineInScrollPane);

        takeAwayTextArea = createOrderTextArea();
        JScrollPane takeAwayScrollPane = new JScrollPane(takeAwayTextArea);
        tabbedPane.addTab("Takeaway", takeAwayScrollPane);

        deliveryTextArea = createOrderTextArea();
        JScrollPane deliveryScrollPane = new JScrollPane(deliveryTextArea);
        tabbedPane.addTab("Delivery", deliveryScrollPane);

        JButton loadButton = new JButton("Load Order History");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadOrderHistory("CustomerDineInOrder.txt", dineInTextArea);
                loadOrderHistory("CustomerTakeAwayOrder.txt", takeAwayTextArea);
                loadOrderHistory("CustomerDeliveryOrder.txt", deliveryTextArea);
            }
        });

        JPanel panel = new JPanel();
        panel.add(loadButton);

        add(panel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Save data when the window is closing
                new VendorPageGUI();
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JTextArea createOrderTextArea() {
        JTextArea textArea = new JTextArea(20, 50);
        textArea.setEditable(false);
        return textArea;
    }

    private void loadOrderHistory(String filePath, JTextArea textArea) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder orderHistory = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                String[] orderData = line.split("\n");

                if (orderData.length > 1) {
                    String orderId = getOrderValue(orderData[0]);
                    String customerName = getOrderValue(orderData[1]);
                    String contact = getOrderValue(orderData[2]);
                    String date = getOrderValue(orderData[3]);
                    String totalPrice = getOrderValue(orderData[4]);

                    orderHistory.append("OrderID: ").append(orderId).append("\n");
                    orderHistory.append("CustomerName: ").append(customerName).append("\n");
                    orderHistory.append("Contact: ").append(contact).append("\n");
                    orderHistory.append("Date: ").append(date).append("\n");
                    orderHistory.append("Total Price: ").append(totalPrice).append("\n");

                    for (int i = 5; i < orderData.length; i++) {
                        String[] itemData = orderData[i].split("\n");

                        if (itemData.length == 3) {
                            String itemName = getOrderValue(itemData[0]);
                            String quantity = getOrderValue(itemData[1]);
                            String singlePrice = getOrderValue(itemData[2]);

                            orderHistory.append("Item Name: ").append(itemName).append("\n");
                            orderHistory.append("Quantity: ").append(quantity).append("\n");
                            orderHistory.append("Single Price: ").append(singlePrice).append("\n");
                        }
                    }

                    orderHistory.append("\n");
                }
            }

            if (orderHistory.length() == 0) {
                orderHistory.append("No order history available.");
            }

            textArea.setText(orderHistory.toString());
        } catch (IOException e) {
            e.printStackTrace();
            textArea.setText("Error loading order history.");
        }
    }

    private String getOrderValue(String orderLine) {
        return orderLine.trim();
    }
}
