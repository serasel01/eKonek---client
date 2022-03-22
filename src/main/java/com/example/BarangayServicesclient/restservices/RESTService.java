package com.example.BarangayServicesclient.restservices;

import com.example.BarangayServicesclient.enums.ParameterType;
import com.example.BarangayServicesclient.models.Admin;
import com.example.BarangayServicesclient.models.Case;
import com.example.BarangayServicesclient.models.Log;
import com.example.BarangayServicesclient.models.Resident;
import com.fasterxml.jackson.core.JsonProcessingException;
import reactor.core.publisher.Mono;

import java.util.List;

public abstract class RESTService {
    private String barangay;
    private String parameterEntry;
    private String userRFID;
    private ParameterType parameterType;
    private Resident resident;
    private Admin admin;
    private Log log;
    private Case aCase;

    public String getBarangay() {
        return barangay;
    }

    public RESTService setBarangay(String barangay) {
        this.barangay = barangay;
        return this;
    }

    public ParameterType getParameterType() {
        return parameterType;
    }

    public RESTService setParameterType(ParameterType parameterType) {
        this.parameterType = parameterType;
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

    public Admin getAdmin() {
        return admin;
    }

    public RESTService setAdmin(Admin admin) {
        this.admin = admin;
        return this;
    }

    public Log getLog() {
        return log;
    }

    public RESTService setLog(Log log) {
        this.log = log;
        return this;
    }

    public Case getaCase() {
        return aCase;
    }

    public RESTService setaCase(Case aCase) {
        this.aCase = aCase;
        return this;
    }

    public abstract List<?> getList();
    public abstract Mono<?> get();
    public abstract String add() throws JsonProcessingException;
    public abstract String update() throws JsonProcessingException;
    public abstract String delete();

}
