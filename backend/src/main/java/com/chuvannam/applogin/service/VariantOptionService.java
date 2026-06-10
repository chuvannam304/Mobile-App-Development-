package com.chuvannam.applogin.service;

import com.chuvannam.applogin.entity.VariantOption;

import java.util.List;
import java.util.UUID;

public interface VariantOptionService {

    List<VariantOption> findAll();

    VariantOption findById(UUID id);

    List<VariantOption> findByProductId(UUID productId);

    List<VariantOption> findActiveByProductId(UUID productId);

    VariantOption save(VariantOption variantOption);

    VariantOption update(UUID id, VariantOption variantOption);

    void delete(UUID id);
}