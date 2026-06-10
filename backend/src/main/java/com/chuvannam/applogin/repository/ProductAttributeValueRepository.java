package com.chuvannam.applogin.repository;

import com.chuvannam.applogin.entity.ProductAttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductAttributeValueRepository
        extends JpaRepository<ProductAttributeValue, UUID> {

    List<ProductAttributeValue> findByProductAttributeId(UUID productAttributeId);

    List<ProductAttributeValue> findByAttributeValueId(UUID attributeValueId);
}