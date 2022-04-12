package com.example.BarangayServicesclient.restservices;

import com.example.BarangayServicesclient.enums.Uri;
import com.example.BarangayServicesclient.models.Official;
import com.example.BarangayServicesclient.models.Resident;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

public class RESTOfficial extends RESTService{
    private WebClient webClient;

    public RESTOfficial(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public List<?> getList() {
        return webClient.get()
                .uri(Uri.OFFICIALS.getUri() +
                        "?parameterType=" + getOfficialFilterParameter().name() +
                        "&parameterEntry=" + getParameterEntry() +
                        "&barangay=" + getBarangay())
                .retrieve()
                .bodyToFlux(Official.class)
                .collectList()
                .block();
    }

    @Override
    public Mono<?> get() {
        return webClient.get()
                .uri(Uri.OFFICIAL.getUri() +
                                "?barangay=" + getBarangay(),
                        getUserRFID())
                .retrieve()
                .bodyToMono(Official.class);
    }

    @Override
    public String add() throws JsonProcessingException {
        return webClient.post()
                .uri(Uri.OFFICIAL.getUri(),
                        getUserRFID(),
                        getOfficial())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ObjectMapper()
                        .writeValueAsString(getOfficial()))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @Override
    public String update() throws JsonProcessingException {
        return webClient.put()
                .uri(Uri.OFFICIAL.getUri(),
                        getUserRFID(),
                        getOfficial())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ObjectMapper()
                        .writeValueAsString(getOfficial()))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @Override
    public String delete() {
        return webClient.delete()
                .uri(Uri.OFFICIAL.getUri(),
                        getUserRFID())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
