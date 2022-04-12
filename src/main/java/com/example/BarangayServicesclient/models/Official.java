package com.example.BarangayServicesclient.models;

public class Official {
    private String userRFID;
    private String password;
    private String userType;
    private String barangay;
    private String firstName;
    private String lastName;

    public Official() {
    }

    public Official(String userRFID, String password, String userType, String barangay, String firstName, String lastName) {
        this.userRFID = userRFID;
        this.password = password;
        this.userType = userType;
        this.barangay = barangay;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFullName(){
        return getFirstName() + " " + getLastName();
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserRFID() {
        return userRFID;
    }

    public void setUserRFID(String userRFID) {
        this.userRFID = userRFID;
    }

    public String getBarangay() {
        return barangay;
    }

    public void setBarangay(String barangay) {
        this.barangay = barangay;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Official{" +
                "userRFID='" + userRFID + '\'' +
                ", password='" + password + '\'' +
                ", userType='" + userType + '\'' +
                ", barangay='" + barangay + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
