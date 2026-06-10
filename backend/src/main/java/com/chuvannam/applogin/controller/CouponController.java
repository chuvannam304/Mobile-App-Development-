package com.chuvannam.applogin.controller;

import com.chuvannam.applogin.entity.Coupon;
import com.chuvannam.applogin.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CouponController {

    private final CouponService couponService;

    @GetMapping
    public ResponseEntity<List<Coupon>> getAll() {
        return ResponseEntity.ok(
                couponService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Coupon> getById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                couponService.findById(id)
        );
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<Coupon> getByCode(
            @PathVariable String code
    ) {
        return ResponseEntity.ok(
                couponService.findByCode(code)
        );
    }

    @PostMapping
    public ResponseEntity<Coupon> create(
            @RequestBody Coupon coupon
    ) {
        return ResponseEntity.ok(
                couponService.save(coupon)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Coupon> update(
            @PathVariable UUID id,
            @RequestBody Coupon coupon
    ) {
        return ResponseEntity.ok(
                couponService.update(id, coupon)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable UUID id
    ) {
        couponService.delete(id);

        return ResponseEntity.ok(
                "Coupon deleted successfully"
        );
    }
}