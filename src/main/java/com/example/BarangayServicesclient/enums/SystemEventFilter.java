package com.example.BarangayServicesclient.enums;

public enum SystemEventFilter {
    OfficialID("SELECT * FROM dbekonek.tbsystemevents " +
            "WHERE officialID = ? ", "Official ID"),
    OfficialName("SELECT * FROM dbekonek.tbsystemevents " +
            "WHERE officialName LIKE ?", "Official Name"),
    Event("SELECT * FROM dbekonek.tbsystemevents " +
            "WHERE event = ?", "Event"),
    Timestamp("SELECT * FROM dbekonek.tbsystemevents " +
            "WHERE timestamp LIKE ?", "Timestamp");

    private String query;
    private String filter;

    SystemEventFilter(String query, String filter) {
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
