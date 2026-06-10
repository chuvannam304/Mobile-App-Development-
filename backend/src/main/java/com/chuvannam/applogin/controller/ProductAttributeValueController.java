package com.chuvannam.applogin.controller;

import com.chuvannam.applogin.entity.ProductAttributeValue;
import com.chuvannam.applogin.service.ProductAttributeValueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/product-attribute-values")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProductAttributeValueController {

    private final ProductAttributeValueService productAttributeValueService;

    @GetMapping
    public ResponseEntity<List<ProductAttributeValue>> getAll() {
        return ResponseEntity.ok(
                productAttributeValueService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductAttributeValue> getById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                productAttributeValueService.findById(id)
        );
    }

    @GetMapping("/product-attribute/{productAttributeId}")
    public ResponseEntity<List<ProductAttributeValue>> getByProductAttributeId(
            @PathVariable UUID productAttributeId
    ) {
        return ResponseEntity.ok(
                productAttributeValueService.findByProductAttributeId(productAttributeId)
        );
    }

    @GetMapping("/attribute-value/{attributeValueId}")
    public ResponseEntity<List<ProductAttributeValue>> getByAttributeValueId(
            @PathVariable UUID attributeValueId
    ) {
        return ResponseEntity.ok(
                productAttributeValueService.findByAttributeValueId(attributeValueId)
        );
    }

    @PostMapping
    public ResponseEntity<ProductAttributeValue> create(
            @RequestBody ProductAttributeValue productAttributeValue
    ) {
        return ResponseEntity.ok(
                productAttributeValueService.save(productAttributeValue)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductAttributeValue> update(
            @PathVariable UUID id,
            @RequestBody ProductAttributeValue productAttributeValue
    ) {
        return ResponseEntity.ok(
                productAttributeValueService.update(id, productAttributeValue)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable UUID id
    ) {
        productAttributeValueService.delete(id);

        return ResponseEntity.ok(
                "ProductAttributeValue deleted successfully"
        );
    }
}