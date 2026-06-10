package com.chuvannam.applogin.controller;

import com.chuvannam.applogin.entity.Variant;
import com.chuvannam.applogin.service.VariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/variants")
@RequiredArgsConstructor
@CrossOrigin("*")
public class VariantController {

    private final VariantService variantService;

    @GetMapping
    public ResponseEntity<List<Variant>> getAll() {
        return ResponseEntity.ok(
                variantService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Variant> getById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                variantService.findById(id)
        );
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Variant>> getByProductId(
            @PathVariable UUID productId
    ) {
        return ResponseEntity.ok(
                variantService.findByProductId(productId)
        );
    }

    @GetMapping("/variant-option/{variantOptionId}")
    public ResponseEntity<List<Variant>> getByVariantOptionId(
            @PathVariable UUID variantOptionId
    ) {
        return ResponseEntity.ok(
                variantService.findByVariantOptionId(variantOptionId)
        );
    }

    @PostMapping
    public ResponseEntity<Variant> create(
            @RequestBody Variant variant
    ) {
        return ResponseEntity.ok(
                variantService.save(variant)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Variant> update(
            @PathVariable UUID id,
            @RequestBody Variant variant
    ) {
        return ResponseEntity.ok(
                variantService.update(id, variant)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable UUID id
    ) {
        variantService.delete(id);

        return ResponseEntity.ok(
                "Variant deleted successfully"
        );
    }
}