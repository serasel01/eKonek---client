package com.example.barangayservicesui.utils;

import com.example.BarangayServicesclient.models.Resident;

import java.util.prefs.Preferences;
import java.util.prefs.BackingStoreException;

public class AdminPreferences {
    private static Preferences prefs;
    private static AdminPreferences single_instance = null;
    private static final String USER_RFID = "USER_RFID";
    private static final String USER_NAME = "USER_NAME";
    private static final String USER_BARANGAY = "USER_BARANGAY";
    private static final String USER_SEAL = "USER_SEAL";
    private static final String USER_TYPE = "USER_TYPE";

    public AdminPreferences() {

    }

    public static AdminPreferences getPrefsInstance(){
        if (single_instance == null){
            single_instance = new AdminPreferences();
            prefs = Preferences.userNodeForPackage(AdminPreferences.class);
        }
        return single_instance;
    }

    public String getUserRfid(){
        return prefs.get(USER_RFID, null);
    }

    public String getName(){
        return prefs.get(USER_NAME, null);
    }

    public String getUserBarangay(){
        return prefs.get(USER_BARANGAY, null);
    }

    public String getUserSeal() {return prefs.get(USER_SEAL, null);}

    public String getUserType() {return prefs.get(USER_TYPE, null);}

    public void logout() throws BackingStoreException {
        prefs.clear();
    }

    public void setAdmin(Resident adminDetails, String seal) {
        prefs.put(USER_RFID, adminDetails.getUserRFID());
        prefs.put(USER_NAME, adminDetails.getFirstName() + " " + adminDetails.getLastName());
        prefs.put(USER_BARANGAY, adminDetails.getBarangay());
        prefs.put(USER_SEAL, seal);
        prefs.put(USER_TYPE, adminDetails.getUserType());
    }
}
