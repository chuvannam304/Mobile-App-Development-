package com.chuvannam.applogin.service;

import com.chuvannam.applogin.entity.Variant;

import java.util.List;
import java.util.UUID;

public interface VariantService {

    List<Variant> findAll();

    Variant findById(UUID id);

    List<Variant> findByProductId(UUID productId);

    List<Variant> findByVariantOptionId(UUID variantOptionId);

    Variant save(Variant variant);

    Variant update(UUID id, Variant variant);

    void delete(UUID id);
}