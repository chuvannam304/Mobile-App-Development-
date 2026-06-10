package com.chuvannam.applogin.service.impl;

import com.chuvannam.applogin.entity.Coupon;
import com.chuvannam.applogin.repository.CouponRepository;
import com.chuvannam.applogin.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;

    @Override
    public List<Coupon> findAll() {
        return couponRepository.findAll();
    }

    @Override
    public Coupon findById(UUID id) {
        return couponRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Coupon not found: " + id));
    }

    @Override
    public Coupon findByCode(String code) {
        return couponRepository.findByCode(code)
                .orElseThrow(() ->
                        new RuntimeException("Coupon not found: " + code));
    }

    @Override
    public Coupon save(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Override
    public Coupon update(UUID id, Coupon coupon) {

        Coupon existing = findById(id);

        existing.setCode(coupon.getCode());
        existing.setDiscountValue(coupon.getDiscountValue());
        existing.setDiscountType(coupon.getDiscountType());

        existing.setTimesUsed(coupon.getTimesUsed());
        existing.setMaxUsage(coupon.getMaxUsage());

        existing.setOrderAmountLimit(
                coupon.getOrderAmountLimit()
        );

        existing.setCouponStartDate(
                coupon.getCouponStartDate()
        );

        existing.setCouponEndDate(
                coupon.getCouponEndDate()
        );

        return couponRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        couponRepository.deleteById(id);
    }
}