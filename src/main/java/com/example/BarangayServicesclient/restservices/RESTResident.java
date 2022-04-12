package com.example.BarangayServicesclient.restservices;

import com.example.BarangayServicesclient.enums.Uri;
import com.example.BarangayServicesclient.models.Resident;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

public class RESTResident extends RESTService{
    private WebClient webClient;

    public RESTResident(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public List<?> getList() {
        return webClient.get()
                .uri(Uri.RESIDENTS.getUri() +
                        "?parameterType=" + getResidentFilterParameter().name() +
                        "&parameterEntry=" + getParameterEntry() +
                        "&barangay=" + getBarangay())
                .retrieve()
                .bodyToFlux(Resident.class)
                .collectList()
                .block();
    }

    @Override
    public Mono<?> get() {
        return webClient.get()
                .uri(Uri.RESIDENT.getUri(),
                        getUserRFID())
                .retrieve()
                .bodyToMono(Resident.class);
    }

    @Override
    public String add() throws JsonProcessingException {
        return webClient.post()
                .uri(Uri.RESIDENT.getUri(),
                        getUserRFID(),
                        getResident())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ObjectMapper()
                        .writeValueAsString(getResident()))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @Override
    public String update() throws JsonProcessingException {
        return webClient.put()
                .uri(Uri.RESIDENT.getUri(),
                        getUserRFID(),
                        getResident())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ObjectMapper()
                        .writeValueAsString(getResident()))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @Override
    public String delete() {
        return webClient.delete()
                .uri(Uri.RESIDENT.getUri(),
                        getUserRFID())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

}
