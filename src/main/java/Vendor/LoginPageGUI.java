package Vendor;

import javax.swing.SwingUtilities;

public class LoginPageGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginPageUser());
    }
}