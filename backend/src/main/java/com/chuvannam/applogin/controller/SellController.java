package com.chuvannam.applogin.controller;

import com.chuvannam.applogin.entity.Sell;
import com.chuvannam.applogin.service.SellService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/sells")
@RequiredArgsConstructor
@CrossOrigin("*")
public class SellController {

    private final SellService sellService;

    @GetMapping
    public ResponseEntity<List<Sell>> getAll() {
        return ResponseEntity.ok(
                sellService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sell> getById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                sellService.findById(id)
        );
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Sell> getByProductId(
            @PathVariable UUID productId
    ) {
        return ResponseEntity.ok(
                sellService.findByProductId(productId)
        );
    }

    @PostMapping
    public ResponseEntity<Sell> create(
            @RequestBody Sell sell
    ) {
        return ResponseEntity.ok(
                sellService.save(sell)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sell> update(
            @PathVariable UUID id,
            @RequestBody Sell sell
    ) {
        return ResponseEntity.ok(
                sellService.update(id, sell)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable UUID id
    ) {
        sellService.delete(id);

        return ResponseEntity.ok(
                "Sell deleted successfully"
        );
    }
}