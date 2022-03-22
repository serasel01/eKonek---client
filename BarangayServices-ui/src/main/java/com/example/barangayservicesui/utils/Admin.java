package com.example.barangayservicesui.utils;

import com.example.BarangayServicesclient.RESTFacade;
import com.example.BarangayServicesclient.enums.ParameterType;
import com.example.BarangayServicesclient.models.Log;
import com.example.BarangayServicesclient.models.Resident;
import com.example.barangayservicesui.enums.Barangay;
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
    private final Map<String, List<Resident>> residentMap =
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

        List<Resident> adminList = new ArrayList<>();
        adminList.add(admin);
        residentMap.put(admin.getUserRFID(), adminList);
    }

    public Barangay getBarangay() {
        return barangay;
    }

    public void setBarangay(Barangay barangay) {
        this.barangay = barangay;
    }

    @Override
    public void addResident() {

    }

    @Override
    public void editResidentInfo() {

    }

    @Override
    public void deleteResident() {

    }

    @Override
    public void addLog(Resident resident, String event) throws JsonProcessingException {
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
    public List<Resident> getResidentList(ParameterType parameterType,
                                          String parameterEntry) {
        List<Resident> resultList;

        //get from cache / previous searched entries
        if (residentMap.containsKey(parameterEntry)){
            resultList = new ArrayList<>(residentMap.get(parameterEntry));

            //get from database
        } else {
            resultList = RESTFacade.getInstance()
                    .getResidents(Admin.getInstance()
                                    .getBarangay().getBarangay(),
                            parameterType,
                            parameterEntry);

            residentMap.put(parameterEntry, resultList);
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
