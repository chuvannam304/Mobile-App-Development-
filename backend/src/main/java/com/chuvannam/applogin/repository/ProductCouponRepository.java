package com.chuvannam.applogin.repository;

import com.chuvannam.applogin.entity.ProductCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductCouponRepository
        extends JpaRepository<ProductCoupon, UUID> {

    List<ProductCoupon> findByProductId(UUID productId);

    List<ProductCoupon> findByCouponId(UUID couponId);
}