package com.example.BarangayServicesclient.models;

public class Case {
    private String caseTitle;
    private String caseNumber;
    private String dateIssued;
    private String complaintName;
    private String address;
    private String description;
    private String dateHearing;
    private String timeHearing;
    private String actionsTaken;

    public Case() {
    }

    public Case(String caseTitle,
                String caseNumber,
                String dateIssued,
                String complaintName,
                String address,
                String description,
                String dateHearing,
                String timeHearing,
                String actionsTaken) {

        this.caseTitle = caseTitle;
        this.caseNumber = caseNumber;
        this.dateIssued = dateIssued;
        this.complaintName = complaintName;
        this.address = address;
        this.description = description;
        this.dateHearing = dateHearing;
        this.timeHearing = timeHearing;
        this.actionsTaken = actionsTaken;
    }

    public String getCaseTitle() {
        return caseTitle;
    }

    public void setCaseTitle(String caseTitle) {
        this.caseTitle = caseTitle;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(String dateIssued) {
        this.dateIssued = dateIssued;
    }

    public String getComplaintName() {
        return complaintName;
    }

    public void setComplaintName(String complaintName) {
        this.complaintName = complaintName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateHearing() {
        return dateHearing;
    }

    public void setDateHearing(String dateHearing) {
        this.dateHearing = dateHearing;
    }

    public String getTimeHearing() {
        return timeHearing;
    }

    public void setTimeHearing(String timeHearing) {
        this.timeHearing = timeHearing;
    }

    public String getActionsTaken() {
        return actionsTaken;
    }

    public void setActionsTaken(String actionsTaken) {
        this.actionsTaken = actionsTaken;
    }

    @Override
    public String toString() {
        return "Case{" +
                "caseTitle='" + caseTitle + '\'' +
                ", caseNumber='" + caseNumber + '\'' +
                ", dateIssued='" + dateIssued + '\'' +
                ", complaintName='" + complaintName + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", dateHearing='" + dateHearing + '\'' +
                ", timeHearing='" + timeHearing + '\'' +
                ", actionsTaken='" + actionsTaken + '\'' +
                '}';
    }
}
