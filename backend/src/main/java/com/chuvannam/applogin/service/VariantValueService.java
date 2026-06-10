package com.chuvannam.applogin.service;

import com.chuvannam.applogin.entity.VariantValue;

import java.util.List;
import java.util.UUID;

public interface VariantValueService {

    List<VariantValue> findAll();

    VariantValue findById(UUID id);

    List<VariantValue> findByVariantId(UUID variantId);

    List<VariantValue> findByProductAttributeValueId(
            UUID productAttributeValueId
    );

    VariantValue save(VariantValue variantValue);

    VariantValue update(
            UUID id,
            VariantValue variantValue
    );

    void delete(UUID id);
}