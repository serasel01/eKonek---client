package com.example.BarangayServicesclient.models;

public class Transaction {
    private String transactionID;
    private String transactionDate;
    private String transactionType;
    private String residentName;
    private String officialName;
    private String transactionStatus;
    private String residentRFID;

    public Transaction() {
    }

    public Transaction(String transactionID,
                       String transactionDate,
                       String transactionType,
                       String residentName,
                       String officialName,
                       String transactionStatus,
                       String residentRFID) {

        this.transactionID = transactionID;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.residentName = residentName;
        this.officialName = officialName;
        this.transactionStatus = transactionStatus;
        this.residentRFID = residentRFID;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getResidentName() {
        return residentName;
    }

    public void setResidentName(String residentName) {
        this.residentName = residentName;
    }

    public String getOfficialName() {
        return officialName;
    }

    public void setOfficialName(String officialName) {
        this.officialName = officialName;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getResidentRFID() {
        return residentRFID;
    }

    public void setResidentRFID(String residentRFID) {
        this.residentRFID = residentRFID;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionID='" + transactionID + '\'' +
                ", transactionDate='" + transactionDate + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", residentName='" + residentName + '\'' +
                ", officialName='" + officialName + '\'' +
                ", transactionStatus='" + transactionStatus + '\'' +
                ", residentRFID='" + residentRFID + '\'' +
                '}';
    }
}
