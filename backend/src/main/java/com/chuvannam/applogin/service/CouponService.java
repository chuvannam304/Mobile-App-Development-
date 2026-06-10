package com.chuvannam.applogin.service;

import com.chuvannam.applogin.entity.Coupon;

import java.util.List;
import java.util.UUID;

public interface CouponService {

    List<Coupon> findAll();

    Coupon findById(UUID id);

    Coupon findByCode(String code);

    Coupon save(Coupon coupon);

    Coupon update(UUID id, Coupon coupon);

    void delete(UUID id);
}