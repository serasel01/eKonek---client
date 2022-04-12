package com.example.BarangayServicesclient;

import com.example.BarangayServicesclient.database.SQLConnect;
import com.example.BarangayServicesclient.database.SQLSystemEvent;
import com.example.BarangayServicesclient.database.SQLTransaction;
import com.example.BarangayServicesclient.enums.*;
import com.example.BarangayServicesclient.models.*;
import com.example.BarangayServicesclient.restservices.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.reactive.function.client.WebClient;

import java.sql.SQLException;
import java.util.List;

public class DatabaseFacade {
    private static WebClient webClient;
    private static DatabaseFacade single_instance = null;

    private static RESTService restResident;
    private static RESTService restCase;
    private static RESTService restOfficial;

    private static SQLConnect sqlTransaction;
    private static SQLConnect sqlSystemEvent;

    public DatabaseFacade(WebClient webClient) {
        DatabaseFacade.webClient = webClient;
        restResident = new RESTResident(webClient);
        restCase = new RESTCase(webClient);
        restOfficial = new RESTOfficial(webClient);

        sqlTransaction = new SQLTransaction();
        sqlSystemEvent = new SQLSystemEvent();
    }

    public static DatabaseFacade getInstance(){
        if (single_instance == null){
            single_instance = new DatabaseFacade(
                    WebClient.create(Uri.BASE.getUri())
            );
        }
        return single_instance;
    }

    public List<Resident> getResidents(String barangay,
                                       ResidentFilterParameter filterParameter,
                                       String parameterEntry){
        return (List<Resident>) restResident
                .setBarangay(barangay)
                .setResidentFilterParameter(filterParameter)
                .setParameterEntry(parameterEntry)
                .getList();
    }

    public Resident getResident(String userRFID){

        return (Resident) restResident
                .setUserRFID(userRFID)
                .get()
                .block();
    }

    public String addResident(Resident resident)
            throws JsonProcessingException {

        return restResident
                .setUserRFID(resident.getUserRFID())
                .setResident(resident)
                .add();
    }

    public String updateResident(Resident resident)
            throws JsonProcessingException {

        return restResident
                .setUserRFID(resident.getUserRFID())
                .setResident(resident)
                .update();
    }

    public String deleteResident(String userRFID) {

        return restResident
                .setUserRFID(userRFID)
                .delete();
    }

    public List<Official> getOfficials(String barangay,
                                       OfficialFilterParameter filterParameter,
                                       String parameterEntry){

       return (List<Official>) restOfficial
               .setBarangay(barangay)
               .setOfficialFilterParameter(filterParameter)
               .setParameterEntry(parameterEntry)
               .getList();
    }

    public Official getOfficial(String userRFID,
                                String barangay){

        return (Official) restOfficial
                .setUserRFID(userRFID)
                .setBarangay(barangay)
                .get()
                .block();
    }

    public String authenticateLogin(String userRFID,
                                     String password,
                                     String barangay){

        Official official = getOfficial(userRFID, barangay);

        if (official == null){
            return "Login Failed: User not found.";

        } else if(official.getPassword().equals(password)
                && official.getBarangay().equals(barangay)) {
            return "Login Success!";

        } else {
            return "Login Failed: Incorrect barangay or password.";
        }
    }

    public String addOfficial(Official official)
            throws JsonProcessingException {

        return restOfficial
                .setUserRFID(official.getUserRFID())
                .setOfficial(official)
                .add();
    }

    public String updateOfficial(Official official)
            throws JsonProcessingException {

        return restOfficial
                .setUserRFID(official.getUserRFID())
                .setOfficial(official)
                .update();
    }

    public String deleteOfficial(String userRFID) {

        return restOfficial
                .setUserRFID(userRFID)
                .delete();
    }

    public List<Case> getCases(String userRFID){
        return (List<Case>) restCase
                .setUserRFID(userRFID)
                .getList();
    }

    public String addCase(Resident resident,
                          Case aCase)
            throws JsonProcessingException {

        return restCase
                .setaCase(aCase)
                .setUserRFID(resident.getUserRFID())
                .setCaseNumber(aCase.getCaseNumber())
                .add();
    }

    public List<Transaction> getTransactions(String entry,
                                             TransactionFilter filter)
            throws SQLException {

        return (List<Transaction>) sqlTransaction
                .setTransactionFilter(filter)
                .setEntry(entry)
                .getList();
    }

    public int getTransactionsCount(String entry)
            throws SQLException {

        return sqlTransaction
                .setEntry(entry)
                .getTotalCount();
    }

    public void addTransaction(Transaction transaction)
            throws JsonProcessingException {

        sqlTransaction
                .setTransaction(transaction)
                .add();
    }

    public void updateTransaction(Transaction transaction)
            throws JsonProcessingException {

        sqlTransaction
                .setTransaction(transaction)
                .update();
    }

    public List<SystemEvent> getSystemEvents(String entry,
                                             SystemEventFilter filter)
            throws SQLException {

        return (List<SystemEvent>) sqlSystemEvent
                .setSystemEventFilter(filter)
                .setEntry(entry)
                .getList();
    }

    public void addSystemEvent(SystemEvent systemEvent)
            throws JsonProcessingException {

        sqlSystemEvent.setSystemEvent(systemEvent).add();
    }

    public void updateSystemEvent(SystemEvent systemEvent)
            throws JsonProcessingException {

        sqlSystemEvent
                .setSystemEvent(systemEvent)
                .update();
    }

}
