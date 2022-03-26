package com.example.barangayservicesui.enums;

public enum LogEvent {
    AdminAccountDeletion("Admin Account Deletion"),
    AdminAccountCreation("Admin Account Creation"),
    CertificateIssuance("Certificate Issuance"),
    ResidentAccountDeletion("Resident Account Deletion"),
    ResidentAccountCreation("Resident Account Creation"),
    UpdateResidentInfo("Update Resident Info");

    private String event;

    public String getEvent() {
        return event;
    }

    LogEvent(String event) {
        this.event = event;
    }


}
