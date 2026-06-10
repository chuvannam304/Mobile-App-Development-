package com.chuvannam.applogin.service.impl;

import com.chuvannam.applogin.entity.ProductSupplier;
import com.chuvannam.applogin.repository.ProductSupplierRepository;
import com.chuvannam.applogin.service.ProductSupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductSupplierServiceImpl implements ProductSupplierService {

    private final ProductSupplierRepository productSupplierRepository;

    @Override
    public List<ProductSupplier> findAll() {
        return productSupplierRepository.findAll();
    }

    @Override
    public ProductSupplier findById(UUID id) {
        return productSupplierRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("ProductSupplier not found: " + id));
    }

    @Override
    public List<ProductSupplier> findByProductId(UUID productId) {
        return productSupplierRepository.findByProductId(productId);
    }

    @Override
    public List<ProductSupplier> findBySupplierId(UUID supplierId) {
        return productSupplierRepository.findBySupplierId(supplierId);
    }

    @Override
    public ProductSupplier save(ProductSupplier productSupplier) {
        return productSupplierRepository.save(productSupplier);
    }

    @Override
    public ProductSupplier update(
            UUID id,
            ProductSupplier productSupplier
    ) {

        ProductSupplier existing = findById(id);

        existing.setProduct(
                productSupplier.getProduct()
        );

        existing.setSupplier(
                productSupplier.getSupplier()
        );

        return productSupplierRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        productSupplierRepository.deleteById(id);
    }
}