package com.example.BarangayServicesclient;

import com.example.BarangayServicesclient.models.Admin;
import com.example.BarangayServicesclient.models.Log;
import com.example.BarangayServicesclient.models.Resident;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

public class BarangayRESTClient {
    private static WebClient webClient;
    private static BarangayRESTClient single_instance = null;
    private static final String BASE_URI = "http://localhost:8080/api/";
    private static final String RESIDENT_URI = "Barangays/{barangay}/Residents/{userRFID}";
    private static final String RESIDENTS_URI = "Barangays/{barangay}/Residents";
    private static final String RFID_URI = "RFIDs/{userRFID}";
    private static final String LOG_URI = "Barangays/{barangay}/Logs/{timestamp}";
    private static final String LOGS_URI = "Barangays/{barangay}/Logs";

    public BarangayRESTClient(WebClient webClient) {
        BarangayRESTClient.webClient = webClient;
    }

    public static BarangayRESTClient getInstance(){
        if (single_instance == null){
            single_instance = new BarangayRESTClient( WebClient.create(BASE_URI));
        }
        return single_instance;
    }

    public List<Resident> getAllResidents(String barangay){
        return webClient.get().uri(RESIDENTS_URI, barangay)
                .retrieve()
                .bodyToFlux(Resident.class)
                .collectList()
                .block();
    }

    public List<Resident> getSearchedResidents(String barangay, String param){
        return webClient.post()
                .uri(RESIDENTS_URI + "?param=" + param, barangay)
                .retrieve()
                .bodyToFlux(Resident.class)
                .collectList()
                .block();
    }

    public List<Log> getAllLogs(String barangay){
        return webClient.get().uri(LOGS_URI, barangay)
                .retrieve()
                .bodyToFlux(Log.class)
                .collectList()
                .block();
    }

    public Resident getResident(String barangay, String userRFID){
        return webClient.get().uri(RESIDENT_URI, barangay, userRFID)
                .retrieve()
                .bodyToMono(Resident.class)
                .block();
    }

    public Admin getLoginCreds(String userRFID){
        return webClient.get().uri(RFID_URI, userRFID)
                .retrieve()
                .bodyToMono(Admin.class)
                .block();
    }

    public String addResident(String barangay, String userRFID, Resident resident) throws JsonProcessingException {
        String message =  webClient.post().uri(RESIDENT_URI, barangay, userRFID, resident)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ObjectMapper().writeValueAsString(resident))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        Logging.printInfoLog(message);
        return message;
    }

    public String addLog(String barangay, Log log) throws JsonProcessingException {
        String message =  webClient.post().uri(LOG_URI, barangay, log)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ObjectMapper().writeValueAsString(log))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        Logging.printInfoLog(message);
        return message;
    }

    public String addLoginCreds(String userRFID, Admin admin) throws JsonProcessingException {
        String message =  webClient.post().uri(RFID_URI, userRFID, admin)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ObjectMapper().writeValueAsString(admin))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        Logging.printInfoLog(message);
        return message;
    }

    public String updateResident(String barangay, String userRFID, Resident resident) throws JsonProcessingException {
        String message =  webClient.put().uri(RESIDENT_URI, barangay, userRFID, resident)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ObjectMapper().writeValueAsString(resident))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        Logging.printInfoLog(message);
        return message;
    }

    public String updateLoginCreds(String userRFID, Admin admin) throws JsonProcessingException {
        String message =  webClient.put().uri(RFID_URI, userRFID, admin)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ObjectMapper().writeValueAsString(admin))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        Logging.printInfoLog(message);
        return message;
    }

    public String deleteResident(String barangay, String userRFID) throws JsonProcessingException {
        String message =  webClient.delete().uri(RESIDENT_URI, barangay, userRFID)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        Logging.printInfoLog(message);
        return message;
    }

    public String deleteLoginCreds(String userRFID) throws JsonProcessingException {
        String message =  webClient.delete().uri(RFID_URI, userRFID)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        Logging.printInfoLog(message);
        return message;
    }


}
