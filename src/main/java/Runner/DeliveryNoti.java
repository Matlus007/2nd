package Runner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

public class DeliveryNoti {; 
    private String runnerID;
    private String notification;
    private String time;
    private static ArrayList<DeliveryNoti> notiList;
    private static DefaultTableModel tableModel;
    
    public DeliveryNoti(String runnerID, String notification, String time){
        this.runnerID = runnerID;
        this.notification = notification;
        this.time = time;
    }

    public String getRunnerID() {
        return runnerID;
    }

    public void setRunnerID(String runnerID) {
        this.runnerID = runnerID;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public static ArrayList<DeliveryNoti> getNotiList() {
        return notiList;
    }

    public static void setNotiList(ArrayList<DeliveryNoti> notiList) {
        DeliveryNoti.notiList = notiList;
    }

    public static DefaultTableModel getTableModel() {
        return tableModel;
    }

    public static void setTableModel(DefaultTableModel tableModel) {
        DeliveryNoti.tableModel = tableModel;
    }
    
    public static ArrayList loadnotifications(){
        notiList = new ArrayList<>();
        String notiTxt = "C:\\Users\\User\\Documents\\NetBeansProjects\\JavaAssignmentFinal\\src\\main\\java\\Database\\DeliveryOrder.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(notiTxt))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] orderData = line.split(", ");
                String loadRunnerID = orderData[0];
                String loadNoti = orderData[1];
                String loadTime = orderData[2];
                DeliveryNoti noti = new DeliveryNoti(loadRunnerID,loadNoti, loadTime);
                notiList.add(noti);
        }

        }catch (IOException e) {
            e.printStackTrace();
        }
        
        return(notiList);
     }
    
    public static void saveToFile(ArrayList<DeliveryNoti> notiList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src\\Database\\DeliveryNoti.txt"))) {
            for (DeliveryNoti notiLoad : notiList) {
                String line = notiLoad.getRunnerID() + ", " + notiLoad.getNotification() + ", " + notiLoad.getTime();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void sendRunnerNoti(String runnerID){
        String filePath = "src\\Database\\DelieryNoti.txt";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        Date date = new Date();
        String formattedDate = dateFormat.format(date);
        try {
            // Create a FileWriter in append mode to add a line to the file
            FileWriter fileWriter = new FileWriter(filePath, true); // 'true' for append mode
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            String lineToAdd = runnerID + ", You have new order!" + ", " + formattedDate;

            // Write the line to the file
            bufferedWriter.write(lineToAdd);
            bufferedWriter.newLine(); // Add a new line after the entered text

            // Close resources
            bufferedWriter.close();
            fileWriter.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static DefaultTableModel startNotiTable(ArrayList<DeliveryNoti> notiList, String operator) {
        // Create the table model with column headers
        String[] columnHeaders = {"Notifications", "Time"};
        tableModel = new DefaultTableModel(columnHeaders, 0);

        // Populate the table model with item data
        for (DeliveryNoti notiLoad : notiList) {
            if(notiLoad.getRunnerID().equals(operator)){
                Object[] rowData = {notiLoad.getNotification(), notiLoad.getTime()};
                tableModel.addRow(rowData);
            }
        }
        return tableModel;

    }
}
