package com.example.BarangayServicesclient.enums;

public enum Uri {
    BASE("http://localhost:8080/api/"),
    RESIDENT("/Residents/{userRFID}"),
    RESIDENTS("/Residents"),
    OFFICIAL("/Officials/{userRFID}"),
    OFFICIALS("/Officials"),
    CASES("/Residents/{userRFID}/Cases"),
    CASE("/Residents/{userRFID}/Cases/{caseId}");

    private String uri;

    Uri(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }
}
