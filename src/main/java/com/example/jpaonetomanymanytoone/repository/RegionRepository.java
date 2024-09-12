package com.example.jpaonetomanymanytoone.repository;

import com.example.jpaonetomanymanytoone.model.Kommune;
import com.example.jpaonetomanymanytoone.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region, String> {
}
