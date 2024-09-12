package com.example.jpaonetomanymanytoone.service;

import com.example.jpaonetomanymanytoone.model.Region;

import java.util.List;

public interface ApiServiceGetRegioner {
    void initRegioner();
    List<Region> getAllRegions();
}
