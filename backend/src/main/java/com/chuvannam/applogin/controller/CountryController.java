package com.chuvannam.applogin.controller;

import com.chuvannam.applogin.entity.Country;
import com.chuvannam.applogin.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CountryController {

    private final CountryService countryService;

    @GetMapping
    public ResponseEntity<List<Country>> getAll() {
        return ResponseEntity.ok(
                countryService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Country> getById(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(
                countryService.findById(id)
        );
    }

    @GetMapping("/iso/{iso}")
    public ResponseEntity<Country> getByIso(
            @PathVariable String iso
    ) {
        return ResponseEntity.ok(
                countryService.findByIso(iso)
        );
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Country> getByName(
            @PathVariable String name
    ) {
        return ResponseEntity.ok(
                countryService.findByName(name)
        );
    }

    @PostMapping
    public ResponseEntity<Country> create(
            @RequestBody Country country
    ) {
        return ResponseEntity.ok(
                countryService.save(country)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Country> update(
            @PathVariable Integer id,
            @RequestBody Country country
    ) {
        return ResponseEntity.ok(
                countryService.update(id, country)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Integer id
    ) {
        countryService.delete(id);

        return ResponseEntity.ok(
                "Country deleted successfully"
        );
    }
}