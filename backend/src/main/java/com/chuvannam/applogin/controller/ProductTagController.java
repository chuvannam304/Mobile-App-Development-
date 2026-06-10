package com.chuvannam.applogin.controller;

import com.chuvannam.applogin.entity.ProductTag;
import com.chuvannam.applogin.service.ProductTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/product-tags")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProductTagController {

    private final ProductTagService productTagService;

    @GetMapping
    public ResponseEntity<List<ProductTag>> getAll() {
        return ResponseEntity.ok(productTagService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductTag> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(productTagService.findById(id));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductTag>> getByProductId(
            @PathVariable UUID productId
    ) {
        return ResponseEntity.ok(productTagService.findByProductId(productId));
    }

    @GetMapping("/tag/{tagId}")
    public ResponseEntity<List<ProductTag>> getByTagId(
            @PathVariable UUID tagId
    ) {
        return ResponseEntity.ok(productTagService.findByTagId(tagId));
    }

    @PostMapping
    public ResponseEntity<ProductTag> create(
            @RequestBody ProductTag productTag
    ) {
        return ResponseEntity.ok(productTagService.save(productTag));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductTag> update(
            @PathVariable UUID id,
            @RequestBody ProductTag productTag
    ) {
        return ResponseEntity.ok(productTagService.update(id, productTag));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        productTagService.delete(id);
        return ResponseEntity.ok("ProductTag deleted successfully");
    }
}