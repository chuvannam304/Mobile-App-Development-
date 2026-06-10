package com.chuvannam.applogin.service;

import com.chuvannam.applogin.entity.ProductAttribute;

import java.util.List;
import java.util.UUID;

public interface ProductAttributeService {

    List<ProductAttribute> findAll();

    ProductAttribute findById(UUID id);

    List<ProductAttribute> findByProductId(UUID productId);

    List<ProductAttribute> findByAttributeId(UUID attributeId);

    ProductAttribute save(ProductAttribute productAttribute);

    ProductAttribute update(UUID id, ProductAttribute productAttribute);

    void delete(UUID id);
}