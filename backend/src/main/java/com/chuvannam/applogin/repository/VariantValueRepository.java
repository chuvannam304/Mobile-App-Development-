package com.chuvannam.applogin.repository;

import com.chuvannam.applogin.entity.VariantValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VariantValueRepository
        extends JpaRepository<VariantValue, UUID> {

    List<VariantValue> findByVariantId(UUID variantId);

    List<VariantValue> findByProductAttributeValueId(UUID productAttributeValueId);
}