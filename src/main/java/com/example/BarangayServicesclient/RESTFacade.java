package com.example.BarangayServicesclient;

import com.example.BarangayServicesclient.enums.ParameterType;
import com.example.BarangayServicesclient.enums.Uri;
import com.example.BarangayServicesclient.models.Admin;
import com.example.BarangayServicesclient.models.Case;
import com.example.BarangayServicesclient.models.Log;
import com.example.BarangayServicesclient.models.Resident;
import com.example.BarangayServicesclient.restservices.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

public class RESTFacade {
    private static WebClient webClient;
    private static RESTFacade single_instance = null;
    private static RESTService restResident;
    private static RESTService restLoginCreds;
    private static RESTService restLog;
    private static RESTService restCase;

    public RESTFacade(WebClient webClient) {
        RESTFacade.webClient = webClient;
        restResident = new RESTResident(webClient);
        restLoginCreds = new RESTLoginCreds(webClient);
        restLog = new RESTLog(webClient);
        restCase = new RESTCase(webClient);
    }

    public static RESTFacade getInstance(){
        if (single_instance == null){
            single_instance = new RESTFacade(
                    WebClient.create(Uri.BASE.getUri())
            );
        }
        return single_instance;
    }

    public List<Resident> getResidents(String barangay,
                                       ParameterType parameterType,
                                       String parameterEntry){
        return (List<Resident>) restResident.setBarangay(barangay)
                .setParameterType(parameterType)
                .setParameterEntry(parameterEntry)
                .getList();
    }

    public Resident getResident(String barangay,
                                String userRFID){

        return (Resident) restResident.setBarangay(barangay)
                .setUserRFID(userRFID)
                .get()
                .block();
    }

    public String addResident(String barangay,
                              String userRFID,
                              Resident resident)
            throws JsonProcessingException {

        return restResident.setBarangay(barangay)
                .setUserRFID(userRFID)
                .setResident(resident)
                .add();
    }

    public String updateResident(String barangay,
                                 String userRFID,
                                 Resident resident)
            throws JsonProcessingException {

        return restResident.setBarangay(barangay)
                .setUserRFID(userRFID)
                .setResident(resident)
                .update();
    }

    public String deleteResident(String barangay,
                                 String userRFID)
            throws JsonProcessingException {

        return restResident.setBarangay(barangay)
                .setUserRFID(userRFID)
                .delete();
    }

    public boolean authenticateLogin(String userRFID,
                                     String password,
                                     String barangay){
        Admin admin = getLoginCreds(userRFID);
        return admin.getPassword().equals(password)
                && admin.getBarangay().equals(barangay);
    }

    public Admin getLoginCreds(String userRFID){
        return (Admin) restLoginCreds.setUserRFID(userRFID)
                .get()
                .block();
    }

    public String addLoginCreds(String userRFID,
                                Admin admin)
            throws JsonProcessingException {

        return restLoginCreds.setUserRFID(userRFID)
                .setAdmin(admin)
                .add();
    }

    public String updateLoginCreds(String userRFID,
                                   Admin admin)
            throws JsonProcessingException {

        return restLoginCreds.setUserRFID(userRFID)
                .setAdmin(admin)
                .update();
    }

    public String deleteLoginCreds(String userRFID)
            throws JsonProcessingException {

        return restLoginCreds.setUserRFID(userRFID)
                .delete();
    }

    public List<Log> getAllLogs(String barangay,
                                ParameterType parameterType,
                                String parameterEntry){
        return (List<Log>) restLog.setBarangay(barangay)
                .setParameterType(parameterType)
                .setParameterEntry(parameterEntry)
                .getList();
    }

    public String addLog(String barangay, Log log) throws JsonProcessingException {
        return restLog.setBarangay(barangay)
                .setLog(log)
                .add();
    }

    public List<Case> getCases(String barangay,
                               String userRFID){
        return (List<Case>) restCase.setBarangay(barangay)
                .setUserRFID(userRFID)
                .getList();
    }

    public String addCase(String barangay,
                          String userRFID,
                          Case aCase)
            throws JsonProcessingException {

        return restCase.setaCase(aCase)
                .setBarangay(barangay)
                .setUserRFID(userRFID)
                .add();
    }

}
