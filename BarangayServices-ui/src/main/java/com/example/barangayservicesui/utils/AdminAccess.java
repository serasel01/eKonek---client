package com.example.barangayservicesui.utils;

import com.example.BarangayServicesclient.enums.ParameterType;
import com.example.BarangayServicesclient.models.Resident;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface AdminAccess {
    void addResident();
    void editResidentInfo();
    void deleteResident();
    void addLog(Resident resident, String event) throws JsonProcessingException;
    List<Resident> getResidentList(ParameterType parameterType,
                                   String parameterEntry);
    void logout();
}
