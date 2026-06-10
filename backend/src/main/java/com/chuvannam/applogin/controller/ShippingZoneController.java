package com.chuvannam.applogin.controller;

import com.chuvannam.applogin.entity.ShippingZone;
import com.chuvannam.applogin.service.ShippingZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/shipping-zones")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ShippingZoneController {

    private final ShippingZoneService shippingZoneService;

    @GetMapping
    public ResponseEntity<List<ShippingZone>> getAll() {
        return ResponseEntity.ok(
                shippingZoneService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShippingZone> getById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                shippingZoneService.findById(id)
        );
    }

    @GetMapping("/active")
    public ResponseEntity<List<ShippingZone>> getActive() {
        return ResponseEntity.ok(
                shippingZoneService.findActive()
        );
    }

    @GetMapping("/free-shipping")
    public ResponseEntity<List<ShippingZone>> getFreeShipping() {
        return ResponseEntity.ok(
                shippingZoneService.findFreeShipping()
        );
    }

    @GetMapping("/search")
    public ResponseEntity<List<ShippingZone>> search(
            @RequestParam String keyword
    ) {
        return ResponseEntity.ok(
                shippingZoneService.searchByName(keyword)
        );
    }

    @PostMapping
    public ResponseEntity<ShippingZone> create(
            @RequestBody ShippingZone shippingZone
    ) {
        return ResponseEntity.ok(
                shippingZoneService.save(shippingZone)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShippingZone> update(
            @PathVariable UUID id,
            @RequestBody ShippingZone shippingZone
    ) {
        return ResponseEntity.ok(
                shippingZoneService.update(id, shippingZone)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable UUID id
    ) {
        shippingZoneService.delete(id);

        return ResponseEntity.ok(
                "ShippingZone deleted successfully"
        );
    }
}