package assignment.Admin;

public class Admin_ManageUserInfo {
    private String timeDate;
    private String userType;
    private String userId; // User ID field
    private String username; // Username field
    private String password;
    private String contactNumber;
    private double Balance;

    public double getBalance() {
        return Balance;
    }
    
    public void setBalance(double Balance) {
        this.Balance = Balance;
    }
 
    public Admin_ManageUserInfo(double Balance) {
        this.Balance = 0;
    }
 
    public Admin_ManageUserInfo(String timeDate, String userType, String userId, String username, String password, String contactNumber) {
        this.timeDate = timeDate;
        this.userType = userType;
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.contactNumber = contactNumber;
    }

    Admin_ManageUserInfo(String timeDate, String userType, String username, String password, String contactNumber) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // Getter and setter methods for all fields

    public String getTimeDate() {
        return timeDate;
    }

    public void setTimeDate(String timeDate) {
        this.timeDate = timeDate;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
