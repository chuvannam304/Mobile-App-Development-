package com.chuvannam.applogin.controller;

import com.chuvannam.applogin.entity.ProductAttribute;
import com.chuvannam.applogin.service.ProductAttributeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/product-attributes")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProductAttributeController {

    private final ProductAttributeService productAttributeService;

    @GetMapping
    public ResponseEntity<List<ProductAttribute>> getAll() {
        return ResponseEntity.ok(productAttributeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductAttribute> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(productAttributeService.findById(id));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductAttribute>> getByProductId(
            @PathVariable UUID productId
    ) {
        return ResponseEntity.ok(productAttributeService.findByProductId(productId));
    }

    @GetMapping("/attribute/{attributeId}")
    public ResponseEntity<List<ProductAttribute>> getByAttributeId(
            @PathVariable UUID attributeId
    ) {
        return ResponseEntity.ok(productAttributeService.findByAttributeId(attributeId));
    }

    @PostMapping
    public ResponseEntity<ProductAttribute> create(
            @RequestBody ProductAttribute productAttribute
    ) {
        return ResponseEntity.ok(productAttributeService.save(productAttribute));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductAttribute> update(
            @PathVariable UUID id,
            @RequestBody ProductAttribute productAttribute
    ) {
        return ResponseEntity.ok(productAttributeService.update(id, productAttribute));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        productAttributeService.delete(id);
        return ResponseEntity.ok("ProductAttribute deleted successfully");
    }
}