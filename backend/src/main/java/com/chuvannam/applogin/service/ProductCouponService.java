package com.chuvannam.applogin.service;

import com.chuvannam.applogin.entity.ProductCoupon;

import java.util.List;
import java.util.UUID;

public interface ProductCouponService {

    List<ProductCoupon> findAll();

    ProductCoupon findById(UUID id);

    List<ProductCoupon> findByProductId(UUID productId);

    List<ProductCoupon> findByCouponId(UUID couponId);

    ProductCoupon save(ProductCoupon productCoupon);

    ProductCoupon update(
            UUID id,
            ProductCoupon productCoupon
    );

    void delete(UUID id);
}