package com.chuvannam.applogin.controller;

import com.chuvannam.applogin.entity.ShippingRate;
import com.chuvannam.applogin.service.ShippingRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/shipping-rates")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ShippingRateController {

    private final ShippingRateService shippingRateService;

    @GetMapping
    public ResponseEntity<List<ShippingRate>> getAll() {
        return ResponseEntity.ok(
                shippingRateService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShippingRate> getById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                shippingRateService.findById(id)
        );
    }

    @GetMapping("/shipping-zone/{shippingZoneId}")
    public ResponseEntity<List<ShippingRate>> getByShippingZoneId(
            @PathVariable UUID shippingZoneId
    ) {
        return ResponseEntity.ok(
                shippingRateService.findByShippingZoneId(shippingZoneId)
        );
    }

    @GetMapping("/weight-unit/{weightUnit}")
    public ResponseEntity<List<ShippingRate>> getByWeightUnit(
            @PathVariable String weightUnit
    ) {
        return ResponseEntity.ok(
                shippingRateService.findByWeightUnit(weightUnit)
        );
    }

    @PostMapping
    public ResponseEntity<ShippingRate> create(
            @RequestBody ShippingRate shippingRate
    ) {
        return ResponseEntity.ok(
                shippingRateService.save(shippingRate)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShippingRate> update(
            @PathVariable UUID id,
            @RequestBody ShippingRate shippingRate
    ) {
        return ResponseEntity.ok(
                shippingRateService.update(id, shippingRate)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable UUID id
    ) {
        shippingRateService.delete(id);

        return ResponseEntity.ok(
                "ShippingRate deleted successfully"
        );
    }
}