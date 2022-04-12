package com.example.barangayservicesui.enums;

public enum LogEvent {
    OfficialAccountDeletion("Official Account Deletion"),
    OfficialAccountCreation("Official Account Creation"),
    ResidentAccountDeletion("Resident Account Deletion"),
    ResidentAccountCreation("Resident Account Creation"),
    UpdateResidentInfo("Update Resident Info"),
    TransactionCreation("Transaction Creation"),
    TransactionCompletion("Transaction Completion"),
    TransactionCancellation("Transaction Cancellation"),
    Login("Login"),
    Logout("Logout");

    private String event;

    public String getEvent() {
        return event;
    }

    LogEvent(String event) {
        this.event = event;
    }


}
