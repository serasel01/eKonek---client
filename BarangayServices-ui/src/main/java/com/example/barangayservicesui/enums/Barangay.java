package com.example.barangayservicesui.enums;

public enum Barangay {
    TUMAGA("Tumaga", "src/main/resources/images/Tumaga.png"),
    GUIWAN("Guiwan", "src/main/resources/images/Guiwan.png"),
    STAMARIA("StaMaria", "src/main/resources/images/StaMaria.png");

    private String barangay;
    private String fileReference;

    Barangay(String barangay, String fileReference) {
        this.barangay = barangay;
        this.fileReference = fileReference;
    }

    public String getFileReference() {
        return fileReference;
    }

    public void setFileReference(String fileReference) {
        this.fileReference = fileReference;
    }

    public String getBarangay() {
        return barangay;
    }

    public void setBarangay(String barangay) {
        this.barangay = barangay;
    }
}
