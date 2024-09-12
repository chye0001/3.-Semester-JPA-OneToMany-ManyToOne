package com.example.jpaonetomanymanytoone.config;

import com.example.jpaonetomanymanytoone.service.ApiServiceGetKommuner;
import com.example.jpaonetomanymanytoone.service.ApiServiceGetRegioner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitData implements CommandLineRunner {

    @Autowired
    private ApiServiceGetRegioner apiServiceGetRegioner;

    @Autowired
    private ApiServiceGetKommuner apiServiceGetKommuner;

    @Override
    public void run(String... args) throws Exception {
        apiServiceGetRegioner.initRegioner();
        apiServiceGetKommuner.initKommuner();
    }
}
