package com.chuvannam.applogin.service.impl;

import com.chuvannam.applogin.entity.ProductCoupon;
import com.chuvannam.applogin.repository.ProductCouponRepository;
import com.chuvannam.applogin.service.ProductCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductCouponServiceImpl implements ProductCouponService {

    private final ProductCouponRepository productCouponRepository;

    @Override
    public List<ProductCoupon> findAll() {
        return productCouponRepository.findAll();
    }

    @Override
    public ProductCoupon findById(UUID id) {
        return productCouponRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("ProductCoupon not found: " + id));
    }

    @Override
    public List<ProductCoupon> findByProductId(UUID productId) {
        return productCouponRepository.findByProductId(productId);
    }

    @Override
    public List<ProductCoupon> findByCouponId(UUID couponId) {
        return productCouponRepository.findByCouponId(couponId);
    }

    @Override
    public ProductCoupon save(ProductCoupon productCoupon) {
        return productCouponRepository.save(productCoupon);
    }

    @Override
    public ProductCoupon update(
            UUID id,
            ProductCoupon productCoupon
    ) {

        ProductCoupon existing = findById(id);

        existing.setProduct(
                productCoupon.getProduct()
        );

        existing.setCoupon(
                productCoupon.getCoupon()
        );

        return productCouponRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        productCouponRepository.deleteById(id);
    }
}