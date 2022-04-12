package com.example.barangayservicesui.enums;

public enum Pane {
    SystemLogs("views/system-logs-view.fxml"),
    Officials("views/manage-officials-view.fxml"),
    Transactions("views/manage-transactions-view.fxml"),
    Profile("views/resident-profile-view.fxml"),
    NewProfile("views/resident-profile-view.fxml"),
    Residents("views/manage-residents-view.fxml");

    private String filePath;

    Pane(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
