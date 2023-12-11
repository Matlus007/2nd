/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import Runner.DeliveryOrder;
import Runner.DeliveryRunner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author theli
 */
public class AddLine extends javax.swing.JFrame {
    private ArrayList<DeliveryOrder> orderList = DeliveryOrder.loadorders();

    /**
     * Creates new form AddLine
     */
    public AddLine() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Task History");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        add = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        add.setText("add");
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(147, 147, 147)
                .addComponent(add)
                .addContainerGap(181, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(142, Short.MAX_VALUE)
                .addComponent(add)
                .addGap(135, 135, 135))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        String filePath = "src\\Database\\DeliveryOrder.txt"; // Replace with your file path
        String address = "d";
        String contact = "0";
        String username = "Jon";
        String date = "10/12/2023 06:59:56 am";
        SimpleDateFormat originalDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");

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
   
    }//GEN-LAST:event_addActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add;
    // End of variables declaration//GEN-END:variables
}