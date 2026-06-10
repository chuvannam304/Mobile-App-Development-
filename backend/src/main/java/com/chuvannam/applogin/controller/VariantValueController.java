package com.chuvannam.applogin.controller;

import com.chuvannam.applogin.entity.VariantValue;
import com.chuvannam.applogin.service.VariantValueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/variant-values")
@RequiredArgsConstructor
@CrossOrigin("*")
public class VariantValueController {

    private final VariantValueService variantValueService;

    @GetMapping
    public ResponseEntity<List<VariantValue>> getAll() {
        return ResponseEntity.ok(variantValueService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VariantValue> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(variantValueService.findById(id));
    }

    @GetMapping("/variant/{variantId}")
    public ResponseEntity<List<VariantValue>> getByVariantId(
            @PathVariable UUID variantId
    ) {
        return ResponseEntity.ok(
                variantValueService.findByVariantId(variantId)
        );
    }

    @GetMapping("/product-attribute-value/{productAttributeValueId}")
    public ResponseEntity<List<VariantValue>> getByProductAttributeValueId(
            @PathVariable UUID productAttributeValueId
    ) {
        return ResponseEntity.ok(
                variantValueService.findByProductAttributeValueId(
                        productAttributeValueId
                )
        );
    }

    @PostMapping
    public ResponseEntity<VariantValue> create(
            @RequestBody VariantValue variantValue
    ) {
        return ResponseEntity.ok(
                variantValueService.save(variantValue)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<VariantValue> update(
            @PathVariable UUID id,
            @RequestBody VariantValue variantValue
    ) {
        return ResponseEntity.ok(
                variantValueService.update(id, variantValue)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        variantValueService.delete(id);
        return ResponseEntity.ok("VariantValue deleted successfully");
    }
}