package com.chuvannam.applogin.service;

import com.chuvannam.applogin.entity.ProductSupplier;

import java.util.List;
import java.util.UUID;

public interface ProductSupplierService {

    List<ProductSupplier> findAll();

    ProductSupplier findById(UUID id);

    List<ProductSupplier> findByProductId(UUID productId);

    List<ProductSupplier> findBySupplierId(UUID supplierId);

    ProductSupplier save(ProductSupplier productSupplier);

    ProductSupplier update(
            UUID id,
            ProductSupplier productSupplier
    );

    void delete(UUID id);
}