package com.example.jpaonetomanymanytoone.repository;

import com.example.jpaonetomanymanytoone.model.Kommune;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface KommuneRepository extends CrudRepository<Kommune, String> {
    List<Kommune> getKommunesByRegionKode(String regionsKode);
}
