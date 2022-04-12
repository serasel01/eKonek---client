package com.example.BarangayServicesclient;

import com.example.BarangayServicesclient.enums.*;
import com.example.BarangayServicesclient.models.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseFacadeTest {
    final private static Logger logger = Logger.getLogger(DatabaseFacadeTest.class.getName());

    @Test
    void getResidents() {
        List<Resident> residents = DatabaseFacade.getInstance()
                .getResidents(
                        "Tumaga",
                        ResidentFilterParameter.MobileNumber,
                        "09123456789");

        for (Resident resident : residents){
            logger.info(resident.toString());
        }
    }

    @Test
    void getResident() {
        Resident resident = DatabaseFacade.getInstance().getResident("123987456");
        logger.info(resident.toString());
    }

    @Test
    void addResident() throws JsonProcessingException {
        Resident.ResidentBuilder builder = new Resident.ResidentBuilder(
                "firstName",
                "middleName",
                "lastName",
                "Female",
                "Single",
                "Filipino",
                "Zamboanga City",
                "June 18, 2004",
                false,
                "Lot 3, 3rd Street",
                "Tumaga",
                "fatherFirstName",
                "fatherMiddleName",
                "fatherLastName",
                "Zamboanga City",
                "motherFirstName",
                "motherMiddleName",
                "motherLastName",
                "Zamboanga City",
                "2306521785"
        );

        DatabaseFacade.getInstance().addResident(builder.build());
    }

    @Test
    void updateResident() throws JsonProcessingException {
        Resident.ResidentBuilder builder = new Resident.ResidentBuilder(
                "firstName",
                "middleName",
                "lastName",
                "Female",
                "Single",
                "Filipino",
                "Zamboanga City",
                "June 18, 2004",
                false,
                "Lot 3, 3rd Street",
                "Tumaga",
                "fatherFirstName",
                "fatherMiddleName",
                "fatherLastName",
                "Zamboanga City",
                "motherFirstName",
                "motherMiddleName",
                "motherLastName",
                "Zamboanga City",
                "123987456"
        );
        builder.setEmailAddress("firstLast@email.com");
        builder.setMobileNumber("09987654321");

        DatabaseFacade.getInstance().updateResident(builder.build());
    }

    @Test
    void deleteResident() throws JsonProcessingException {
//        Resident.ResidentBuilder builder = new Resident.ResidentBuilder(
//                "firstName",
//                "middleName",
//                "lastName",
//                "Female",
//                "Single",
//                "Filipino",
//                "Zamboanga City",
//                "June 18, 2004",
//                false,
//                "Lot 3, 3rd Street",
//                "Tumaga",
//                "fatherFirstName",
//                "fatherMiddleName",
//                "fatherLastName",
//                "Zamboanga City",
//                "motherFirstName",
//                "motherMiddleName",
//                "motherLastName",
//                "Zamboanga City",
//                "741258963"
//        );
//
//        DatabaseFacade.getInstance().addResident(builder.build());

        DatabaseFacade.getInstance().deleteResident("741258963");
    }

    @Test
    void getOfficials() {
        List <Official> officials = DatabaseFacade.getInstance()
                .getOfficials(
                        "Tumaga",
                        OfficialFilterParameter.LastName,
                        "last");

        for (Official official : officials){
            logger.info(official.toString());
        }
    }

//    @Test
//    void authenticateLogin() {
//        boolean condition = DatabaseFacade.getInstance().authenticateLogin(
//                "123456789",
//                "8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918",
//                "Tumaga"
//        );
//        logger.info(String.valueOf(condition));
//    }

    @Test
    void getOfficial() {
        Official official = DatabaseFacade
                .getInstance()
                .getOfficial(
                        "12345679",
                        "Tumaga"
                );
        logger.info(official.toString());
    }

    @Test
    void addOfficial() throws JsonProcessingException {
        DatabaseFacade.getInstance().addOfficial(
                new Official(
                        "2306521785",
                        "8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918",
                        "Administrator",
                        "Tumaga",
                        "firstName",
                        "lastName"
                )
        );
    }

    @Test
    void updateOfficial() throws JsonProcessingException {
        DatabaseFacade.getInstance().updateOfficial(
                new Official(
                        "963258741",
                        "8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918",
                        "Official",
                        "Tumaga",
                        "firstName",
                        "lastName"
                )
        );
    }

    @Test
    void deleteOfficial() throws JsonProcessingException {
//        DatabaseFacade.getInstance().addOfficial(
//                new Official(
//                        "963258951",
//                        "8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918",
//                        "Official",
//                        "Tumaga",
//                        "firstName",
//                        "lastName"
//                )
//        );
        DatabaseFacade.getInstance().deleteOfficial("963258951");
    }

    @Test
    void getCases() {
        List<Case> cases = DatabaseFacade
                .getInstance()
                .getCases("123987456");

        for (Case aCase : cases){
            logger.info(aCase.toString());
        }

    }

    @Test
    void addCase() throws JsonProcessingException {
        Resident.ResidentBuilder builder = new Resident.ResidentBuilder(
                "firstName",
                "middleName",
                "lastName",
                "Female",
                "Single",
                "Filipino",
                "Zamboanga City",
                "June 18, 2004",
                false,
                "Lot 3, 3rd Street",
                "Tumaga",
                "fatherFirstName",
                "fatherMiddleName",
                "fatherLastName",
                "Zamboanga City",
                "motherFirstName",
                "motherMiddleName",
                "motherLastName",
                "Zamboanga City",
                "123987456"
        );

        DatabaseFacade.getInstance().addCase(
                builder.build(),
                new Case(
                        "Title",
                        "N0MB3R",
                        "November 04, 2020",
                        "complaintName",
                        "Tumaga, Zamboanga City",
                        "theft",
                        "November 10, 2020",
                        "1:00 PM",
                        "Imprison Sentence for 3 months"
                ));
    }

    @Test
    void getTransactions() throws SQLException {
        List <Transaction> transactions = DatabaseFacade
                .getInstance()
                .getTransactions(
                        "residentName",
                        TransactionFilter.ResidentName
                );

        for (Transaction transaction : transactions){
            logger.info(transaction.toString());
        }
    }

    @Test
    void addTransaction() throws JsonProcessingException {
        DatabaseFacade
                .getInstance()
                .addTransaction(new Transaction(
                        "9YHKB658HQCSD",
                        "September 08, 2021",
                        "Barangay Clearance",
                        "residentName",
                        "officialName",
                        "PENDING",
                        "794613258"
                ));
    }

    @Test
    void updateTransaction() throws JsonProcessingException {
        DatabaseFacade
                .getInstance()
                .updateTransaction(new Transaction(
                        "9YHKB658HQCSD",
                        "September 08, 2021",
                        "Barangay Clearance",
                        "residentName",
                        "officialName",
                        "COMPLETE",
                        "794613258"
                ));
    }

    @Test
    void getSystemEvents() throws SQLException {
        List <SystemEvent> systemEventList = DatabaseFacade
                .getInstance()
                .getSystemEvents(
                        "Login",
                        SystemEventFilter.Event
                );

        for (SystemEvent transaction : systemEventList){
            logger.info(transaction.toString());
        }
    }

    @Test
    void addSystemEvent() throws JsonProcessingException {
        DatabaseFacade
                .getInstance()
                .addSystemEvent(new SystemEvent(
                        "Login",
                        "April 01, 2022 14:30:00",
                        "officialName",
                        "9765413855"
                ));
    }

    @Test
    void updateSystemEvent() {
    }
}