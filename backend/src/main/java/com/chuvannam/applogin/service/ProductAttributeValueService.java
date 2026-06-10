package com.chuvannam.applogin.service;

import com.chuvannam.applogin.entity.ProductAttributeValue;

import java.util.List;
import java.util.UUID;

public interface ProductAttributeValueService {

    List<ProductAttributeValue> findAll();

    ProductAttributeValue findById(UUID id);

    List<ProductAttributeValue> findByProductAttributeId(UUID productAttributeId);

    List<ProductAttributeValue> findByAttributeValueId(UUID attributeValueId);

    ProductAttributeValue save(ProductAttributeValue productAttributeValue);

    ProductAttributeValue update(
            UUID id,
            ProductAttributeValue productAttributeValue
    );

    void delete(UUID id);
}