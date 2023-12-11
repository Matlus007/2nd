package assignment.Customer;

import Runner.DeliveryOrder;
import Runner.DeliveryRunner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFrame;

public class CustomerOrderSucess {
    private Map<String, Integer> itemQuantityMap;
    private Map<String, Double> itemPriceMap;
    private Map<String, String> orderIdMap;
    private int orderCounter;
    private JFrame successFrame;
    private String username, userID, address, contact;
    private ArrayList<DeliveryOrder> orderList = DeliveryOrder.loadorders();
    
    public CustomerOrderSucess(Map<String, Integer> itemQuantityMap, Map<String, Double> itemPriceMap, String serviceType) {
        this.itemQuantityMap = itemQuantityMap;
        this.itemPriceMap = itemPriceMap;
        this.orderIdMap = new TreeMap<>();
        this.loadOrderIdMap(serviceType);
        this.orderCounter = orderIdMap.size() + 1;
    }

    public Map<String, Integer> getItemQuantityMap() {
        return itemQuantityMap;
    }

    public Map<String, Double> getItemPriceMap() {
        return itemPriceMap;
    }

    public void saveOrderToFile(String username, String userID, String contact, String orderId, String address, String serviceType) {
        String fileName = getOrderFileName(serviceType);

        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(orderId + "\n");
            writer.write(username + "\n");
            writer.write(contact + "\n");

            String date = updateTimeDate();
            writer.write(date + "\n");

            if (address != null) {
                writer.write(address + "\n");
            }

            double totalPrice = calculateTotalPrice();
            writer.write(totalPrice + "\n");

            for (Map.Entry<String, Integer> entry : itemQuantityMap.entrySet()) {
                String itemName = entry.getKey();
                int quantity = entry.getValue();
                double price = itemPriceMap.get(itemName);

                writer.write(itemName + "\n");
                writer.write(quantity + "\n");
                writer.write(price + "\n");
            }

            writer.write("\n");

            orderIdMap.put(orderId, fileName); // Update the orderIdMap
            CustomerFileToRunner(address, username, contact);
        } catch (IOException e) {
            // Handle exceptions appropriately
        }
    }

    private String getOrderFileName(String serviceType) {
        switch (serviceType) {
            case "Dine In" -> {
                return "CustomerDineInOrder.txt";
            }
            case "Take Away" -> {
                return "CustomerTakeAwayOrder.txt";
            }
            case "Delivery" -> {
                return "CustomerDeliveryOrder.txt";
            }
            default -> throw new IllegalArgumentException("Invalid serviceType");
        }
    }

        private double calculateTotalPrice() {
            double total = 0;
            for (Map.Entry<String, Integer> entry : itemQuantityMap.entrySet()) {
                String itemName = entry.getKey();
                int quantity = entry.getValue();
                double price = itemPriceMap.get(itemName);
                total += price * quantity;
            }
            return total;
        }
    
    /**
     *
     * @return
     */
    public String generateOrderId(String serviceType) {
        int maxOrderId = 0;

        // Determine the prefix based on serviceType
        String prefix;
        switch (serviceType) {
            case "Dine In" -> prefix = "D";
            case "Take Away" -> prefix = "T";
            case "Delivery" -> prefix = "J";
            default -> throw new IllegalArgumentException("Invalid serviceType");
        }

        for (String orderId : orderIdMap.keySet()) {
            // Extract the order number from the orderId
            if (orderId.startsWith(prefix)) {
                int orderIdNumber = Integer.parseInt(orderId.substring(1));
                maxOrderId = Math.max(maxOrderId, orderIdNumber);
            }
        }

        // Generate the new orderId with the appropriate prefix and format
        return prefix + String.format("%02d", maxOrderId + 1);
    }
    
    private String updateTimeDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        Date date = new Date();
        String formattedDate = dateFormat.format(date);
        
        return formattedDate;
    }
    
    private void loadOrderIdMap(String serviceType) {
        try (BufferedReader reader = new BufferedReader(new FileReader(getOrderFileName(serviceType)))) {
            String orderId = "";
            String line;
            Pattern orderIdPattern = Pattern.compile("([DTJ]\\d+)");
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                Matcher matcher = orderIdPattern.matcher(line);
                if (matcher.matches()) {
                    orderId = matcher.group(1);
                    String fileName = getOrderFileName(serviceType);
                    orderIdMap.put(orderId, fileName);
                }
            }
        } catch (IOException e) {
            // Handle exceptions appropriately
        }
    }
    
    private void CustomerFileToRunner(String address, String username, String contact) {  
        String date = updateTimeDate();
        String filePath = "C:\\Users\\PC\\Downloads\\Java-Final-master\\src\\main\\java\\Database\\DeliveryOrder.txt"; 
        SimpleDateFormat originalDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        System.out.println("a");

        SimpleDateFormat desiredDateFormat = new SimpleDateFormat("dd/MMM/yyyy");
        try {
            // Create a FileWriter in append mode to add a line to the file
            FileWriter fileWriter = new FileWriter(filePath, true); // 'true' for append mode
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            Date originalDate = originalDateFormat.parse(date);
            String newDate = desiredDateFormat.format(originalDate);
            String lineToAdd = DeliveryOrder.getTotalOrders() + ", " + address + ", " + username + ", " + contact + ", pending, " + DeliveryRunner.getNextRunner(orderList) + ", " + newDate + ", 5.0";

            // Write the line to the file
            bufferedWriter.write(lineToAdd);
            bufferedWriter.newLine(); // Add a new line after the entered text

            // Close resources
            bufferedWriter.close();
            fileWriter.close();


        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
   
    }                        
}