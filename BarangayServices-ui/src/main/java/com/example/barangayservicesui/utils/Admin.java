package com.example.barangayservicesui.utils;

import com.example.BarangayServicesclient.DatabaseFacade;
import com.example.BarangayServicesclient.enums.ResidentFilterParameter;
import com.example.BarangayServicesclient.models.Case;
import com.example.BarangayServicesclient.models.Official;
import com.example.BarangayServicesclient.models.Resident;
import com.example.BarangayServicesclient.models.SystemEvent;
import com.example.barangayservicesui.enums.Barangay;
import com.example.barangayservicesui.enums.LogEvent;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Admin implements AdminAccess {
    private static Admin adminInstance = null;
    private Resident admin;
    private Official official;
    private Barangay barangay;
    private final Map<String, Resident> residentMap =
            new HashMap<>();
    private String userType;

    public Admin() {
    }

    public static Admin getInstance(){
        if (adminInstance == null){
            adminInstance = new Admin();
        }
        return adminInstance;
    }

    public Resident getAdmin() {
        return admin;
    }

    public void setAdmin(Resident admin) {
        this.admin = admin;

        switch (admin.getBarangay()){
            case "Tumaga":
                setBarangay(Barangay.TUMAGA);
                break;
            case "Guiwan":
                setBarangay(Barangay.GUIWAN);
                break;
            case "StaMaria":
                setBarangay(Barangay.STAMARIA);
                break;
            case "Tetuan":
                setBarangay(Barangay.TETUAN);
                break;
        }

        residentMap.put(admin.getUserRFID(), admin);
    }

    public Official getOfficial() {
        return official;
    }

    public void setOfficial(Official official) {
        this.official = official;
    }

    public Barangay getBarangay() {
        return barangay;
    }

    public void setBarangay(Barangay barangay) {
        this.barangay = barangay;
    }

    public Map<String, Resident> getResidentMap() {
        return residentMap;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public void addResident(Resident resident)
            throws JsonProcessingException{

        DatabaseFacade.getInstance()
                .addResident(resident);
    }

    @Override
    public void editResidentInfo(Resident resident)
            throws JsonProcessingException {

        DatabaseFacade.getInstance()
                .updateResident(resident);
    }

    @Override
    public void deleteResident(Resident resident)
            throws JsonProcessingException {

        DatabaseFacade.getInstance()
                .deleteResident(resident.getUserRFID());
    }

    @Override
    public void addLog(String event)
            throws JsonProcessingException {

        DatabaseFacade.getInstance()
                .addSystemEvent(new SystemEvent(
                        event,
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("LLLL dd, yyyy HH:mm:ss")),
                        Admin.getInstance().getAdmin().getFullName(),
                        Admin.getInstance().getAdmin().getUserRFID()
                ));
    }

    @Override
    public void addResidentCase(Resident resident, Case aCase)
            throws JsonProcessingException {

        DatabaseFacade.getInstance()
                .addCase(resident, aCase);
    }

    @Override
    public List<Resident> getResidentList(ResidentFilterParameter filterParameter,
                                          String parameterEntry) {
        List<Resident> resultList = null;

        //get from cache / previous searched entries (only for RFID)
        if (residentMap.containsKey(parameterEntry)){
            resultList = new ArrayList<>();
            resultList.add(residentMap.get(parameterEntry));

            //get from database
        } else {
            resultList = DatabaseFacade
                    .getInstance()
                    .getResidents(
                            admin.getBarangay(),
                            filterParameter,
                            parameterEntry);

            for(Resident resident : resultList){
                residentMap.put(resident.getUserRFID(), resident);
            }
        }

        return resultList;
    }

    @Override
    public void logout() {
        admin = null;
        adminInstance = null;
        barangay = null;
    }
}
