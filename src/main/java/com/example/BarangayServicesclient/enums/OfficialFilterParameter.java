package com.example.BarangayServicesclient.enums;

public enum OfficialFilterParameter {
    OfficialRFID("RFID"),
    FirstName("First Name"),
    LastName("Last Name");

    private String parameter;

    OfficialFilterParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
}
