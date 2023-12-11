package Runner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DeliveryOrder{
    private int orderID; 
    private String address;
    private String name;
    private String phoneNum;
    private String status;
    private String runnerID;
    private String date;
    private float earnings;
    private static ArrayList<DeliveryOrder> orderList;
    private static DefaultTableModel tableModel;

    public DeliveryOrder(int orderID, String address, String name, String phoneNum, String status, String runnerID, String date, float earnings) {
        this.orderID = orderID;
        this.address = address;
        this.name = name;
        this.phoneNum = phoneNum;
        this.status = status;
        this.runnerID = runnerID;
        this.date = date;
        this.earnings = earnings;
    }

    public float getEarnings() {
        return earnings;
    }

    public void setEarnings(float earnings) {
        this.earnings = earnings;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRunnerID() {
        return runnerID;
    }

    public void setRunnerID(String runnerID) {
        this.runnerID = runnerID;
    }

    public int getOrderID() {
        return orderID;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getStatus() {
        return status;
    }

    public static ArrayList<DeliveryOrder> getOrderList() {
        return orderList;
    }

    public static DefaultTableModel getTableModel() {
        return tableModel;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static void setOrderList(ArrayList<DeliveryOrder> orderList) {
        DeliveryOrder.orderList = orderList;
    }

    public static void setTableModel(DefaultTableModel tableModel) {
        DeliveryOrder.tableModel = tableModel;
    }
    
    //array
     public static ArrayList loadorders(){
        orderList = new ArrayList<>();
        String ordersTxt = "C:\\Users\\PC\\Downloads\\Java-Final-master\\src\\main\\java\\Database\\DeliveryOrder.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(ordersTxt))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] orderData = line.split(", ");
                int loadOrderID = Integer.parseInt(orderData[0]);
                String loadAddress = orderData[1];
                String loadName = orderData[2];
                String loadPhoneNum = orderData[3];
                String loadStatus = orderData[4];
                String loadRunnerID = orderData[5];
                String loadDate = orderData[6];
                float loadEarnings = Float.parseFloat(orderData[7]);
                DeliveryOrder orderLoad = new DeliveryOrder(loadOrderID,loadAddress, loadName, loadPhoneNum, loadStatus, loadRunnerID, loadDate, loadEarnings);
                orderList.add(orderLoad);
        }

        }catch (IOException e) {
            e.printStackTrace();
        }
        
        return(orderList);
     }
     
     public static void saveToFile(ArrayList<DeliveryOrder> orderList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\PC\\Downloads\\Java-Final-master\\src\\main\\java\\Database\\DeliveryOrder.txt"))) {
            for (DeliveryOrder orderLoad : orderList) {
                String line = orderLoad.getOrderID() + ", " + orderLoad.getAddress() + ", " + orderLoad.getName() + ", " + orderLoad.getPhoneNum() + ", " + orderLoad.getStatus() + ", " + orderLoad.getRunnerID() + ", " + orderLoad.getDate() + ", " + orderLoad.getEarnings();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     
     public static DefaultTableModel initializeTable(ArrayList<DeliveryOrder> orderList) {
        // Create the table model with column headers
        String[] columnHeaders = {"Order ID", "Address", "Customer Name", "Phone number", "Status"};
        tableModel = new DefaultTableModel(columnHeaders, 0);

        // Populate the table model with item data
        for (DeliveryOrder orderLoad : orderList) {
            Object[] rowData = {orderLoad.getOrderID(), orderLoad.getAddress(), orderLoad.getName(), orderLoad.getPhoneNum(), orderLoad.getStatus()};
            tableModel.addRow(rowData);
        }
        return tableModel;

    }
     
    public static DefaultTableModel startAcceptTable(ArrayList<DeliveryOrder> orderList, String operator) {
        String[] columnHeaders = {"Order ID", "Address", "Customer Name", "Phone number"};
        tableModel = new DefaultTableModel(columnHeaders, 0);

        // Populate the table model with item data
        for (DeliveryOrder orderLoad : orderList) {
            if(orderLoad.getRunnerID().equals(operator) && orderLoad.getStatus().equals("pending")){
                Object[] rowData = {orderLoad.getOrderID(), orderLoad.getAddress(), orderLoad.getName(), orderLoad.getPhoneNum()};
                tableModel.addRow(rowData);
            }
        }
        return tableModel;

    }
    
    public static DefaultTableModel startViewTable(ArrayList<DeliveryOrder> orderList, String operator) {
        String[] columnHeaders = {"Order ID", "Address", "Customer Name", "Phone number", "Status"};
        tableModel = new DefaultTableModel(columnHeaders, 0);

        // Populate the table model with item data
        for (DeliveryOrder orderLoad : orderList) {
            if(orderLoad.getRunnerID().equals(operator)){
                if (orderLoad.getStatus().equals("waiting_restaurant") || orderLoad.getStatus().equals("out_for_delivery")){
                    Object[] rowData = {orderLoad.getOrderID(), orderLoad.getAddress(), orderLoad.getName(), orderLoad.getPhoneNum(), orderLoad.getStatus()};
                    tableModel.addRow(rowData);
                }
            }
        }
        return tableModel;

    }
    
    public static DefaultTableModel startHistoryTable(ArrayList<DeliveryOrder> orderList, String operator) {
        String[] columnHeaders = {"Order ID", "Address", "Customer Name", "Phone number", "Date"};
        tableModel = new DefaultTableModel(columnHeaders, 0);

        // Populate the table model with item data
        for (DeliveryOrder orderLoad : orderList) {
            if(orderLoad.getRunnerID().equals(operator) && orderLoad.getStatus().equals("delivered")){
                Object[] rowData = {orderLoad.getOrderID(), orderLoad.getAddress(), orderLoad.getName(), orderLoad.getPhoneNum(), orderLoad.getDate()};
                tableModel.addRow(rowData);
            }
        }
        return tableModel;

    }
        
    public static DefaultTableModel startRevenueTable(ArrayList<DeliveryOrder> orderList, String curRunner, String type) {
        
        String[] columnHeaders = {"Date", "Earnings"};
        tableModel = new DefaultTableModel(columnHeaders, 0);
        if(type.equals("Daily")){
            Map<String, Float> dailyEarningsMap = new TreeMap<>();

            for (DeliveryOrder orderLoad : orderList) {
                String currentDate = orderLoad.getDate();

                // Skip if the date is "-"
                if (currentDate.equals("-")) {
                    continue;
                }
                if (!orderLoad.getRunnerID().equals(curRunner)) {
                    continue;
                }

                // Calculate daily earnings
                float dailyEarnings = dailyEarningsMap.getOrDefault(currentDate, 0.0f);
                dailyEarnings += orderLoad.getEarnings();
                dailyEarningsMap.put(currentDate, dailyEarnings);
            }

            // Iterate through the daily earnings map and add rows to the table model
            for (Map.Entry<String, Float> entry : dailyEarningsMap.entrySet()) {
                String date = entry.getKey();
                float totalEarnings = entry.getValue();

                // Add each day's total earnings as a row in the table model
                Object[] rowData = { date, totalEarnings };
                tableModel.addRow(rowData);
            }      
        }else if(type.equals("Monthly")){
        // TreeMap to store monthly earnings
        Map<String, Float> monthlyEarningsMap = new TreeMap<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("d-MMM-yyyy", Locale.US);

        for (DeliveryOrder order : orderList) {
            String currentDate = order.getDate();

            // Skip if the date is "-"
            if (currentDate.equals("-")) {
                continue;
            }
            
            if (!order.getRunnerID().equals(curRunner)) {
                continue;
            }
            try {
                Date date = dateFormat.parse(currentDate);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.set(Calendar.DAY_OF_MONTH, 1); // Set to the beginning of the month

                String monthYear = dateFormat.format(calendar.getTime());
                float monthlyEarnings = monthlyEarningsMap.getOrDefault(monthYear, 0.0f);
                monthlyEarnings += order.getEarnings();
                monthlyEarningsMap.put(monthYear, monthlyEarnings);

            } catch (ParseException e) {
                e.printStackTrace();
                // Handle parsing exceptions if needed
            }
        }

        // Add monthly earnings data to the table model
        for (Map.Entry<String, Float> entry : monthlyEarningsMap.entrySet()) {
            String monthYear = entry.getKey();
            float totalMonthlyEarnings = entry.getValue();

            // Format the month-year and total earnings
            String formattedMonthYear = monthYear.substring(2);

            // Add formatted data to the table model
            Object[] rowData = { formattedMonthYear, totalMonthlyEarnings};
            tableModel.addRow(rowData);
        }
    } else{
                    // TreeMap to store yearly earnings
        Map<String, Float> yearlyEarningsMap = new TreeMap<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("d-MMM-yyyy", Locale.US);

        for (DeliveryOrder order : orderList) {
            String currentDate = order.getDate();

            // Skip if the date is "-"
            if (currentDate.equals("-")) {
                continue;
            }
            if (!order.getRunnerID().equals(curRunner)) {
                continue;
            }
            try {
                Date date = dateFormat.parse(currentDate);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);

                // Set to the beginning of the year
                calendar.set(Calendar.MONTH, Calendar.JANUARY);
                calendar.set(Calendar.DAY_OF_MONTH, 1);

                String year = String.valueOf(calendar.get(Calendar.YEAR));
                float yearlyEarnings = yearlyEarningsMap.getOrDefault(year, 0.0f);
                yearlyEarnings += order.getEarnings();
                yearlyEarningsMap.put(year, yearlyEarnings);

            } catch (ParseException e) {
                e.printStackTrace();
                // Handle parsing exceptions if needed
            }
        }

        // Add yearly earnings data to the table model
        for (Map.Entry<String, Float> entry : yearlyEarningsMap.entrySet()) {
            String year = entry.getKey();
            float totalYearlyEarnings = entry.getValue();

            // Format the year and total earnings
            String formattedYear = year;

            // Add formatted data to the table model
            Object[] rowData = { formattedYear, totalYearlyEarnings };
            tableModel.addRow(rowData);
        }
        }
    return tableModel;

    }
        
    public static int getTotalOrders(){
        int count = 0;
        String ordersTxt = "C:\\Users\\PC\\Downloads\\Java-Final-master\\src\\main\\java\\Database\\DeliveryOrder.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(ordersTxt))) {
            String line;
            while ((line = reader.readLine()) != null) {
                count++;
        }

        }catch (IOException e) {
            e.printStackTrace();
        }
        count++;
        return count;
    }
     
    public static void saveTableData(JTable jTable1, ArrayList<DeliveryOrder> orderList) {
    int numRows = jTable1.getRowCount();

    for (int row = 0; row < numRows; row++) {
        String orderID = (String) jTable1.getValueAt(row, 0);
        String address = (String) jTable1.getValueAt(row, 1);
        String name = (String) jTable1.getValueAt(row, 2);
        String phoneNum = (String) jTable1.getValueAt (row, 3);
        String status = (String) jTable1.getValueAt (row, 4);
        String runnerID = (String) jTable1.getValueAt (row, 5);
        String date = (String) jTable1.getValueAt (row, 6);
        String earnings = (String) jTable1.getValueAt (row, 7);

        // Find the corresponding Item object in the itemList based on itemCode
        for (DeliveryOrder orderLoad : orderList) {
            if (orderLoad.getOrderID() == Integer.parseInt(orderID)) {
                // Update the Item object with the edited values
                orderLoad.setAddress(address);
                orderLoad.setName(name);
                orderLoad.setPhoneNum(phoneNum);
                orderLoad.setStatus(status);
                orderLoad.setRunnerID(runnerID);
                orderLoad.setDate(date);
                orderLoad.setEarnings(Float.parseFloat(earnings));
                break;
            }
        }
    }
    DeliveryOrder.saveToFile(orderList);
    }
    
    public static DeliveryOrder getOrderByID(String orderID, ArrayList<DeliveryOrder> orderList) {
        for (DeliveryOrder orderLoad : orderList) {
            if (orderLoad.getOrderID() == Integer.parseInt(orderID)) {
                // Update the Item object with the edited values
                return orderLoad;
            }
        }
        return null;
    }

    
    @Override
     public String toString() {
        return "order{" +
                "order ID='" + orderID + '\'' +
                ", Address='" + address + '\'' +
                ", Customer Name=" + name +
                ", Phone Number=" + phoneNum +
                ", Status=" + status +
                '}';
    }
    
     
     
}