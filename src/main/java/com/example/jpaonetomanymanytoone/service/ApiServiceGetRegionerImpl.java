package com.example.jpaonetomanymanytoone.service;

import com.example.jpaonetomanymanytoone.model.Region;
import com.example.jpaonetomanymanytoone.repository.RegionRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApiServiceGetRegionerImpl implements ApiServiceGetRegioner {

    private RegionRepository regionRepository;
    private final RestTemplate restTemplate;
    private final String regionsUrl = "https://api.dataforsyningen.dk/regioner";

    public ApiServiceGetRegionerImpl(RegionRepository regionRepository, RestTemplate restTemplate) {
        this.regionRepository = regionRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public void initRegioner() {
        List<Region> regionListe;

        ResponseEntity<List<Region>> regionResponse =
                restTemplate.exchange(
                        regionsUrl,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Region>>() {
                });

        regionListe = regionResponse.getBody();
        saveRegioner(regionListe);
    }

    private void saveRegioner(List<Region> regioner) {
        regioner.forEach(region -> regionRepository.save(region));
    }

    @Override
    public List<Region> getAllRegions(){
        return regionRepository.findAll();
    }
}
