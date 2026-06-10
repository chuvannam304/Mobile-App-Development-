package com.chuvannam.applogin.controller;

import com.chuvannam.applogin.entity.Supplier;
import com.chuvannam.applogin.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
@CrossOrigin("*")
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping
    public ResponseEntity<List<Supplier>> getAll() {
        return ResponseEntity.ok(
                supplierService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supplier> getById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                supplierService.findById(id)
        );
    }

    @GetMapping("/country/{countryId}")
    public ResponseEntity<List<Supplier>> getByCountryId(
            @PathVariable Integer countryId
    ) {
        return ResponseEntity.ok(
                supplierService.findByCountryId(countryId)
        );
    }

    @GetMapping("/search")
    public ResponseEntity<List<Supplier>> search(
            @RequestParam String keyword
    ) {
        return ResponseEntity.ok(
                supplierService.searchByName(keyword)
        );
    }

    @PostMapping
    public ResponseEntity<Supplier> create(
            @RequestBody Supplier supplier
    ) {
        return ResponseEntity.ok(
                supplierService.save(supplier)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Supplier> update(
            @PathVariable UUID id,
            @RequestBody Supplier supplier
    ) {
        return ResponseEntity.ok(
                supplierService.update(id, supplier)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable UUID id
    ) {
        supplierService.delete(id);

        return ResponseEntity.ok(
                "Supplier deleted successfully"
        );
    }
}