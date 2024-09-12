package com.example.jpaonetomanymanytoone.service;

import com.example.jpaonetomanymanytoone.model.Kommune;

import java.util.List;

public interface ApiServiceGetKommuner {
    void initKommuner();
    List<Kommune> getAllKommuner();
}
