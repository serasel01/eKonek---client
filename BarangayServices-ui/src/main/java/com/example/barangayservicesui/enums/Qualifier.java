package com.example.barangayservicesui.enums;

public enum Qualifier {
    Jr("Jr"),
    JrII("Jr II"),
    Sr("Sr"),
    I("I"),
    II("II"),
    III("III"),
    IV("IV"),
    V("V"),
    VI("VI");

    private String qualifier;

    Qualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }
}
