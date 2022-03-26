package com.example.barangayservicesui.utils;

import com.example.BarangayServicesclient.RESTFacade;
import com.example.BarangayServicesclient.enums.ParameterType;
import com.example.BarangayServicesclient.models.Case;
import com.example.BarangayServicesclient.models.Log;
import com.example.BarangayServicesclient.models.Resident;
import com.example.barangayservicesui.enums.Barangay;
import com.example.barangayservicesui.enums.LogEvent;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Admin implements AdminAccess {
    private static Admin adminInstance = null;
    private Resident admin;
    private Barangay barangay;
    private final Map<String, Resident> residentMap =
            new HashMap<>();

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
        }

        residentMap.put(admin.getUserRFID(), admin);
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

    @Override
    public void addResident(Resident resident)
            throws JsonProcessingException{

        RESTFacade.getInstance()
                .addResident(
                        Admin.getInstance()
                                .getAdmin()
                                .getBarangay(),
                        resident);
    }

    @Override
    public void editResidentInfo(Resident resident)
            throws JsonProcessingException {

        RESTFacade.getInstance()
                .updateResident(
                        getAdmin().getBarangay(),
                        resident);
    }

    @Override
    public void deleteResident(Resident resident)
            throws JsonProcessingException {

        RESTFacade.getInstance()
                .deleteResident(resident.getBarangay(),
                        resident.getUserRFID());

        Admin.getInstance()
                .addLog(resident,
                        LogEvent.ResidentAccountDeletion.getEvent());
    }

    @Override
    public void addLog(Resident resident, String event)
            throws JsonProcessingException {

        RESTFacade.getInstance()
                .addLog(getAdmin().getBarangay(),
                        new Log(
                                getAdmin().getUserRFID(),
                                resident.getUserRFID(),
                                getAdmin().getFullName(),
                                resident.getFullName(),
                                event,
                                Instant.now().toEpochMilli(),
                                LocalDateTime.now().format(DateTimeFormatter.ofPattern("LLLL dd, yyyy HH:mm"))
                        )
                );
    }

    @Override
    public void addResidentCase(Resident resident, Case aCase)
            throws JsonProcessingException {

        RESTFacade.getInstance()
                .addCase(
                        resident,
                        aCase);
    }

    @Override
    public List<Resident> getResidentList(ParameterType parameterType,
                                          String parameterEntry) {
        List<Resident> resultList;

        //get from cache / previous searched entries (only for RFID)
        if (residentMap.containsKey(parameterEntry)){
            resultList = new ArrayList<>();
            resultList.add(residentMap.get(parameterEntry));

            //get from database
        } else {
            resultList = RESTFacade
                    .getInstance()
                    .getResidents(Admin
                                    .getInstance()
                                    .getBarangay()
                                    .getBarangay(),
                            parameterType,
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
