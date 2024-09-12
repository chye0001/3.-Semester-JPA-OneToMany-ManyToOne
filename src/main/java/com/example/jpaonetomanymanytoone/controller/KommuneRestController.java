package com.example.jpaonetomanymanytoone.controller;

import com.example.jpaonetomanymanytoone.model.Kommune;
import com.example.jpaonetomanymanytoone.repository.KommuneRepository;
import com.example.jpaonetomanymanytoone.repository.RegionRepository;
import com.example.jpaonetomanymanytoone.service.ApiServiceGetKommuner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class KommuneRestController {

    private RegionRepository regionRepository;

    private ApiServiceGetKommuner apiServiceGetKommuner;
    private KommuneRepository kommuneRepository;

    public KommuneRestController(RegionRepository regionRepository, ApiServiceGetKommuner apiServiceGetKommuner, KommuneRepository kommuneRepository) {
        this.regionRepository = regionRepository;
        this.apiServiceGetKommuner = apiServiceGetKommuner;
        this.kommuneRepository = kommuneRepository;
    }

    @GetMapping("/kommuner")
    public ResponseEntity<List<Kommune>> getKommuner() {
        List<Kommune> kommuneList = apiServiceGetKommuner.getAllKommuner();

        if (kommuneList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(kommuneList);
    }

    @GetMapping("/kommuner/{regionKode}")
    public ResponseEntity<List<Kommune>> getAllKommunerByRegionCode(@PathVariable String regionKode) {

        if (!regionRepository.existsById(regionKode)) {
            return ResponseEntity.notFound().build();

        } else {
            List<Kommune> kommuneList = kommuneRepository.getKommunesByRegionKode(regionKode);
            return ResponseEntity.ok(kommuneList);
        }
    }

    @PostMapping("/kommune")
    public ResponseEntity<Kommune> createKommune(@RequestBody Kommune kommune) {
        String kode = kommune.getKode();

        //if the primary key, "kode" have not been set/field in by the client send bad request, 400.
        if (kode == null) {
            return ResponseEntity.badRequest().build();
        }

        if (kommuneRepository.existsById(kode)) {
            return ResponseEntity.badRequest().build();

        } else {
            Kommune createdKommune = kommuneRepository.save(kommune);
            return ResponseEntity.ok(createdKommune);
        }
    }

    @PutMapping("/kommune/{kode}")
    public ResponseEntity<Kommune> updateKommune(@PathVariable String kode, @RequestBody Kommune kommune) {
        //original code
//        Optional<Kommune> kommuneOptional = kommuneRepository.findById(kode);
//
//        if (kommuneOptional.isEmpty()) {
//            return ResponseEntity.badRequest().build();
//        }
//
//        kommuneRepository.save(kommune);
//        return ResponseEntity.ok(kommune);
//


        //improved code

        //if kode does not match the primary key in the kommune object, respond with 409 badRequest.
        if (!kode.equals(kommune.getKode())) {
            return ResponseEntity.badRequest().build();
        }

        //check and see if the kommune exists, if it does not, server should respond with 404 not found, since put is used for update and not creation.
        //this insures we follow the RESTfull practices.
        if (!kommuneRepository.existsById(kode)) {
            return ResponseEntity.notFound().build();
        }

        //we return the updated kommune after is has been saved instead of directly returning it based on the @RequestBody.
        //this ensures it contains the latest changes matching the data in the database.
        Kommune updatedKommune = kommuneRepository.save(kommune);
        return ResponseEntity.ok(updatedKommune);
    }

    @DeleteMapping("/kommune/{kode}")
    public ResponseEntity<String> deleteKommune(@PathVariable String kode) {

        if (!kommuneRepository.existsById(kode)) {
            return ResponseEntity.notFound().build();

        } else {
            Optional<Kommune> kommuneOptional = kommuneRepository.findById(kode);
            String kommuneNavn = kommuneOptional.get().getNavn();
            kommuneRepository.deleteById(kode);
            return ResponseEntity.ok(kommuneNavn + " er blevet slettet");
        }
    }
}
