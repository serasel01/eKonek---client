package com.example.BarangayServicesclient.enums;

public enum TransactionFilter {
    TransactionID("SELECT * FROM dbekonek.tbtransaction " +
            "WHERE transactionID = ? ", "Transaction ID"),
    TransactionDate("SELECT * FROM dbekonek.tbtransaction " +
            "WHERE transactionDate = ?", "Transaction Date"),
    TransactionType("SELECT * FROM dbekonek.tbtransaction " +
            "WHERE transactionType = ?", "Transaction Type"),
    ResidentName("SELECT * FROM dbekonek.tbtransaction " +
            "WHERE residentName LIKE ?", "Resident Name"),
    ResidentRFID("SELECT * FROM dbekonek.tbtransaction " +
            "WHERE residentRFID = ?", "Resident RFID"),
    TransactionStatus("SELECT * FROM dbekonek.tbtransaction " +
            "WHERE transactionStatus = ?", "Transaction Status");

    private String query;
    private String filter;

    TransactionFilter(String query, String filter) {
        this.query = query;
        this.filter = filter;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
}
