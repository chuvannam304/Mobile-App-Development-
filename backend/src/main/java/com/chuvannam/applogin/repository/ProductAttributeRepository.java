package com.chuvannam.applogin.repository;

import com.chuvannam.applogin.entity.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductAttributeRepository
        extends JpaRepository<ProductAttribute, UUID> {

    List<ProductAttribute> findByProductId(UUID productId);

    List<ProductAttribute> findByAttributeId(UUID attributeId);
}