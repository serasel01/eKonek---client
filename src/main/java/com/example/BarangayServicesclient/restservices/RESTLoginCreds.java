package com.example.BarangayServicesclient.restservices;

import com.example.BarangayServicesclient.enums.Uri;
import com.example.BarangayServicesclient.models.Admin;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

public class RESTLoginCreds extends RESTService{
    private WebClient webClient;

    public RESTLoginCreds(WebClient webClient){
        this.webClient = webClient;
    }

    @Override
    public List<?> getList() {
        return null;
    }

    @Override
    public Mono<?> get() {
        return webClient.get().uri(Uri.RFID.getUri(), getUserRFID())
                .retrieve()
                .bodyToMono(Admin.class);
    }

    @Override
    public String add() throws JsonProcessingException {
        return webClient.post()
                .uri(Uri.RFID.getUri(), getUserRFID(), getAdmin())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ObjectMapper()
                        .writeValueAsString(getAdmin()))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @Override
    public String update() throws JsonProcessingException {
        return webClient.put()
                .uri(Uri.RFID.getUri(),
                        getUserRFID(),
                        getAdmin())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ObjectMapper()
                        .writeValueAsString(getAdmin()))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @Override
    public String delete() {
        return webClient.delete().uri(Uri.RFID.getUri(),
                        getUserRFID())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
