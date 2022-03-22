package com.example.BarangayServicesclient.enums;

public enum Uri {
    BASE("http://localhost:8080/api/"),
    RESIDENT("Barangays/{barangay}/Residents/{userRFID}"),
    RESIDENTS("Barangays/{barangay}/Residents"),
    RFID("RFIDs/{userRFID}"),
    LOG("Barangays/{barangay}/Logs/{timestamp}"),
    LOGS("Barangays/{barangay}/Logs"),
    CASES("Barangays/{barangay}/Residents/{userRFID}/Cases"),
    CASE("Barangays/{barangay}/Residents/{userRFID}/Cases/{caseId}");

    private String uri;

    Uri(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }
}
