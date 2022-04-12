package com.example.BarangayServicesclient.models;

public class SystemEvent {
    private String event;
    private String timestamp;
    private String officialName;
    private String officialID;

    public SystemEvent() {
    }

    public SystemEvent(String event,
                       String timestamp,
                       String officialName,
                       String officialID) {

        this.event = event;
        this.timestamp = timestamp;
        this.officialName = officialName;
        this.officialID = officialID;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getOfficialName() {
        return officialName;
    }

    public void setOfficialName(String officialName) {
        this.officialName = officialName;
    }

    public String getOfficialID() {
        return officialID;
    }

    public void setOfficialID(String officialID) {
        this.officialID = officialID;
    }

    @Override
    public String toString() {
        return "SystemEvent{" +
                "event='" + event + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", officialName='" + officialName + '\'' +
                ", officialID='" + officialID + '\'' +
                '}';
    }
}
