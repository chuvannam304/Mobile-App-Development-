package com.chuvannam.applogin.controller;

import com.chuvannam.applogin.entity.VariantOption;
import com.chuvannam.applogin.service.VariantOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/variant-options")
@RequiredArgsConstructor
@CrossOrigin("*")
public class VariantOptionController {

    private final VariantOptionService variantOptionService;

    @GetMapping
    public ResponseEntity<List<VariantOption>> getAll() {
        return ResponseEntity.ok(
                variantOptionService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<VariantOption> getById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                variantOptionService.findById(id)
        );
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<VariantOption>> getByProductId(
            @PathVariable UUID productId
    ) {
        return ResponseEntity.ok(
                variantOptionService.findByProductId(productId)
        );
    }

    @GetMapping("/product/{productId}/active")
    public ResponseEntity<List<VariantOption>> getActiveByProductId(
            @PathVariable UUID productId
    ) {
        return ResponseEntity.ok(
                variantOptionService.findActiveByProductId(productId)
        );
    }

    @PostMapping
    public ResponseEntity<VariantOption> create(
            @RequestBody VariantOption variantOption
    ) {
        return ResponseEntity.ok(
                variantOptionService.save(variantOption)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<VariantOption> update(
            @PathVariable UUID id,
            @RequestBody VariantOption variantOption
    ) {
        return ResponseEntity.ok(
                variantOptionService.update(id, variantOption)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable UUID id
    ) {
        variantOptionService.delete(id);

        return ResponseEntity.ok(
                "VariantOption deleted successfully"
        );
    }
}