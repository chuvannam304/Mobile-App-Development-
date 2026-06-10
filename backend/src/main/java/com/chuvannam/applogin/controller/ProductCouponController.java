package com.chuvannam.applogin.controller;

import com.chuvannam.applogin.entity.ProductCoupon;
import com.chuvannam.applogin.service.ProductCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/product-coupons")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProductCouponController {

    private final ProductCouponService productCouponService;

    @GetMapping
    public ResponseEntity<List<ProductCoupon>> getAll() {
        return ResponseEntity.ok(
                productCouponService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductCoupon> getById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                productCouponService.findById(id)
        );
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductCoupon>> getByProductId(
            @PathVariable UUID productId
    ) {
        return ResponseEntity.ok(
                productCouponService.findByProductId(productId)
        );
    }

    @GetMapping("/coupon/{couponId}")
    public ResponseEntity<List<ProductCoupon>> getByCouponId(
            @PathVariable UUID couponId
    ) {
        return ResponseEntity.ok(
                productCouponService.findByCouponId(couponId)
        );
    }

    @PostMapping
    public ResponseEntity<ProductCoupon> create(
            @RequestBody ProductCoupon productCoupon
    ) {
        return ResponseEntity.ok(
                productCouponService.save(productCoupon)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductCoupon> update(
            @PathVariable UUID id,
            @RequestBody ProductCoupon productCoupon
    ) {
        return ResponseEntity.ok(
                productCouponService.update(id, productCoupon)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable UUID id
    ) {
        productCouponService.delete(id);

        return ResponseEntity.ok(
                "ProductCoupon deleted successfully"
        );
    }
}