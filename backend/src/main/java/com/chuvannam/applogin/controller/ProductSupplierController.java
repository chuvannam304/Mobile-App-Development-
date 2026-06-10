package com.chuvannam.applogin.controller;

import com.chuvannam.applogin.entity.ProductSupplier;
import com.chuvannam.applogin.service.ProductSupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/product-suppliers")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProductSupplierController {

    private final ProductSupplierService productSupplierService;

    @GetMapping
    public ResponseEntity<List<ProductSupplier>> getAll() {
        return ResponseEntity.ok(
                productSupplierService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductSupplier> getById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                productSupplierService.findById(id)
        );
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductSupplier>> getByProductId(
            @PathVariable UUID productId
    ) {
        return ResponseEntity.ok(
                productSupplierService.findByProductId(productId)
        );
    }

    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<List<ProductSupplier>> getBySupplierId(
            @PathVariable UUID supplierId
    ) {
        return ResponseEntity.ok(
                productSupplierService.findBySupplierId(supplierId)
        );
    }

    @PostMapping
    public ResponseEntity<ProductSupplier> create(
            @RequestBody ProductSupplier productSupplier
    ) {
        return ResponseEntity.ok(
                productSupplierService.save(productSupplier)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductSupplier> update(
            @PathVariable UUID id,
            @RequestBody ProductSupplier productSupplier
    ) {
        return ResponseEntity.ok(
                productSupplierService.update(id, productSupplier)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable UUID id
    ) {
        productSupplierService.delete(id);

        return ResponseEntity.ok(
                "ProductSupplier deleted successfully"
        );
    }
}