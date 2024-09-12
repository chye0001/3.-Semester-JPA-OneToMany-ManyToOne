package com.example.jpaonetomanymanytoone.controller;

import com.example.jpaonetomanymanytoone.model.Kommune;
import com.example.jpaonetomanymanytoone.model.Region;
import com.example.jpaonetomanymanytoone.repository.RegionRepository;
import com.example.jpaonetomanymanytoone.service.ApiServiceGetRegioner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class RegionRestController {

    private ApiServiceGetRegioner apiServiceGetRegioner;
    private RegionRepository regionRepository;

    public RegionRestController(ApiServiceGetRegioner apiServiceGetRegioner, RegionRepository regionRepository) {
        this.apiServiceGetRegioner = apiServiceGetRegioner;
        this.regionRepository = regionRepository;
    }

    @GetMapping("/regioner")
    public ResponseEntity<List<Region>> getRegioner() {
        List<Region> regionList = apiServiceGetRegioner.getAllRegions();

        if (regionList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(regionList);
    }

    @PostMapping("/region")
    public ResponseEntity<Region> createRegion(@RequestBody Region region) {
        String kode = region.getKode();

        //if the primary key, "kode" have not been set/field in by the client send bad request, 400.
        if (kode == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Region> optionalRegion = regionRepository.findById(kode);
        if (optionalRegion.isPresent()) {
            return ResponseEntity.badRequest().build();

        } else
            regionRepository.save(region);

        return ResponseEntity.ok(region);
    }

    @PutMapping("/region/{kode}")
    public ResponseEntity<Region> updateRegion(@PathVariable String kode, @RequestBody Region region) {
        Optional<Region> regionOptional = regionRepository.findById(kode);

        if (!regionOptional.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        regionRepository.save(region);
        return ResponseEntity.ok(region);
    }

    @DeleteMapping("/region/{kode}")
    public ResponseEntity<String> deleteRegion(@PathVariable String kode) {
        Optional<Region> regionOptional = regionRepository.findById(kode);

        if (regionOptional.isEmpty()) {
            return ResponseEntity.notFound().build();

        } else {
            Region region = regionOptional.get();
            String regionNavn = region.getNavn();

            regionRepository.delete(region);
            return ResponseEntity.ok(regionNavn + " er blevet slettet");
        }
    }
}
