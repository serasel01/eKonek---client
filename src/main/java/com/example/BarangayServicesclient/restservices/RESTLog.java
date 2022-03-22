package com.example.BarangayServicesclient.restservices;

import com.example.BarangayServicesclient.enums.Uri;
import com.example.BarangayServicesclient.models.Log;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

public class RESTLog extends RESTService{
    private WebClient webClient;

    public RESTLog(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public List<?> getList() {
        return webClient.post()
                .uri(Uri.LOGS.getUri() +
                                "?parameterType=" + getParameterType().name() +
                                "&parameterEntry=" + getParameterEntry(),
                        getBarangay())
                .retrieve()
                .bodyToFlux(Log.class)
                .collectList()
                .block();
    }

    @Override
    public Mono<?> get() {
        return null;
    }

    @Override
    public String add() throws JsonProcessingException {
        return webClient.post()
                .uri(Uri.LOG.getUri(),
                        getBarangay(),
                        getLog())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ObjectMapper()
                        .writeValueAsString(getLog()))
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
