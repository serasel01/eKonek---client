package com.example.BarangayServicesclient.restservices;

import com.example.BarangayServicesclient.enums.OfficialFilterParameter;
import com.example.BarangayServicesclient.enums.ResidentFilterParameter;
import com.example.BarangayServicesclient.models.Case;
import com.example.BarangayServicesclient.models.Official;
import com.example.BarangayServicesclient.models.Resident;
import com.fasterxml.jackson.core.JsonProcessingException;
import reactor.core.publisher.Mono;

import java.util.List;

public abstract class RESTService {
    private String barangay;
    private String userRFID;
    private String caseNumber;

    private String parameterEntry;
    private ResidentFilterParameter residentFilterParameter;
    private OfficialFilterParameter officialFilterParameter;

    private Resident resident;
    private Official official;
    private Case aCase;

    public String getBarangay() {
        return barangay;
    }

    public RESTService setBarangay(String barangay) {
        this.barangay = barangay;
        return this;
    }

    public String getParameterEntry() {
        return parameterEntry;
    }

    public RESTService setParameterEntry(String parameterEntry) {
        this.parameterEntry = parameterEntry;
        return this;
    }

    public String getUserRFID() {
        return userRFID;
    }

    public RESTService setUserRFID(String userRFID) {
        this.userRFID = userRFID;
        return this;
    }

    public Resident getResident() {
        return resident;
    }

    public RESTService setResident(Resident resident) {
        this.resident = resident;
        return this;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public RESTService setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
        return this;
    }

    public Official getOfficial() {
        return official;
    }

    public RESTService setOfficial(Official official) {
        this.official = official;
        return this;
    }

    public Case getaCase() {
        return aCase;
    }

    public RESTService setaCase(Case aCase) {
        this.aCase = aCase;
        return this;
    }

    public ResidentFilterParameter getResidentFilterParameter() {
        return residentFilterParameter;
    }

    public RESTService setResidentFilterParameter(ResidentFilterParameter residentFilterParameter) {
        this.residentFilterParameter = residentFilterParameter;
        return this;
    }

    public OfficialFilterParameter getOfficialFilterParameter() {
        return officialFilterParameter;
    }

    public RESTService setOfficialFilterParameter(OfficialFilterParameter officialFilterParameter) {
        this.officialFilterParameter = officialFilterParameter;
        return this;
    }

    public abstract List<?> getList();
    public abstract Mono<?> get();
    public abstract String add() throws JsonProcessingException;
    public abstract String update() throws JsonProcessingException;
    public abstract String delete();

}
