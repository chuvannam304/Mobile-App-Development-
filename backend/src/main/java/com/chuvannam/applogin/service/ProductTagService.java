package com.chuvannam.applogin.service;

import com.chuvannam.applogin.entity.ProductTag;

import java.util.List;
import java.util.UUID;

public interface ProductTagService {

    List<ProductTag> findAll();

    ProductTag findById(UUID id);

    List<ProductTag> findByProductId(UUID productId);

    List<ProductTag> findByTagId(UUID tagId);

    ProductTag save(ProductTag productTag);

    ProductTag update(UUID id, ProductTag productTag);

    void delete(UUID id);
}