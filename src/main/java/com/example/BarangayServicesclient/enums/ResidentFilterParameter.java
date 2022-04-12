package com.example.BarangayServicesclient.enums;

public enum ResidentFilterParameter {
    ResidentRFID("RFID"),
    FirstName("First Name"),
    LastName("Last Name"),
    Address("Address"),
    MobileNumber("Mobile Number");

    private String parameter;

    ResidentFilterParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
}
