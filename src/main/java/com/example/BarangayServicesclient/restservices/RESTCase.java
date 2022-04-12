package com.example.BarangayServicesclient.restservices;

import com.example.BarangayServicesclient.enums.Uri;
import com.example.BarangayServicesclient.models.Case;
import com.example.BarangayServicesclient.models.Resident;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

public class RESTCase extends RESTService{
    private WebClient webClient;

    public RESTCase(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public List<?> getList() {
        return webClient.get()
                .uri(Uri.CASES.getUri(),
                        getUserRFID())
                .retrieve()
                .bodyToFlux(Case.class)
                .collectList()
                .block();
    }

    @Override
    public Mono<?> get() {
        return webClient.get()
                .uri(Uri.CASE.getUri(),
                        getUserRFID(),
                        getCaseNumber())
                .retrieve()
                .bodyToMono(Resident.class);
    }

    @Override
    public String add() throws JsonProcessingException {
        return webClient.post()
                .uri(Uri.CASE.getUri(),
                        getUserRFID(),
                        getCaseNumber(),
                        getaCase())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ObjectMapper()
                        .writeValueAsString(getaCase()))
                .retrieve()
                .bodyToMono(String.class)
                .block();

    }

    @Override
    public String update() throws JsonProcessingException {
        return null;
    }

    @Override
    public String delete() {
        return null;
    }
}
