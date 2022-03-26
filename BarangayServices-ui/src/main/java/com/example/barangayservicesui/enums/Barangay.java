package com.example.barangayservicesui.enums;

import java.util.ArrayList;

public enum Barangay {
    TUMAGA("Tumaga", "src/main/resources/images/Tumaga.png"),
    GUIWAN("Guiwan", "src/main/resources/images/Guiwan.png"),
    STAMARIA("StaMaria", "src/main/resources/images/StaMaria.png"),
    TETUAN("Tetuan", "src/main/resources/images/Tetuan.png");

    private String barangay;
    private String fileReference;
    private ArrayList<String> certificateTypes;

    Barangay(String barangay, String fileReference) {
        this.barangay = barangay;
        this.fileReference = fileReference;
        this.certificateTypes = new ArrayList<>();
        setCertificateTypes();
    }

    private void setCertificateTypes() {
        if (this.barangay.equals("StaMaria")){
            this.certificateTypes.add("Certification");
            this.certificateTypes.add("Senior Citizen");
            this.certificateTypes.add("Good Moral");
            this.certificateTypes.add("Residency");
            this.certificateTypes.add("Indigency");

        } else {
            this.certificateTypes.add("Certification");
            this.certificateTypes.add("Clearance");
            this.certificateTypes.add("Residency");
            this.certificateTypes.add("Indigency");
        }
    }

    public ArrayList<String> getCertificateTypes() {
        return certificateTypes;
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
