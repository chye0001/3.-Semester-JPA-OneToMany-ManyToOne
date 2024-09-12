package com.example.jpaonetomanymanytoone.service;

import com.example.jpaonetomanymanytoone.model.Kommune;
import com.example.jpaonetomanymanytoone.model.Region;
import com.example.jpaonetomanymanytoone.repository.KommuneRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApiServiceGetKommunerImpl implements ApiServiceGetKommuner{

    private final RestTemplate restTemplate;
    private final String kommuneUrl = "https://api.dataforsyningen.dk/kommuner";

    private final KommuneRepository kommuneRepository;

    public ApiServiceGetKommunerImpl(RestTemplate restTemplate, KommuneRepository kommuneRepository) {
        this.restTemplate = restTemplate;
        this.kommuneRepository = kommuneRepository;
    }

    @Override
    public void initKommuner() {
        ResponseEntity<List<Kommune>> kommuneResponse =
                restTemplate.exchange(
                        kommuneUrl,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Kommune>>() {
                        });

        List<Kommune> kommuneList = kommuneResponse.getBody();
        saveKommuner(kommuneList);
    }

    private void saveKommuner(List<Kommune> kommuneList) {
        kommuneRepository.saveAll(kommuneList);
    }

    @Override
    public List<Kommune> getAllKommuner(){
        return (List<Kommune>) kommuneRepository.findAll();
    }
}
