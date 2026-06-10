package com.chuvannam.applogin.service.impl;

import com.chuvannam.applogin.entity.ProductAttribute;
import com.chuvannam.applogin.repository.ProductAttributeRepository;
import com.chuvannam.applogin.service.ProductAttributeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductAttributeServiceImpl implements ProductAttributeService {

    private final ProductAttributeRepository productAttributeRepository;

    @Override
    public List<ProductAttribute> findAll() {
        return productAttributeRepository.findAll();
    }

    @Override
    public ProductAttribute findById(UUID id) {
        return productAttributeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("ProductAttribute not found: " + id));
    }

    @Override
    public List<ProductAttribute> findByProductId(UUID productId) {
        return productAttributeRepository.findByProductId(productId);
    }

    @Override
    public List<ProductAttribute> findByAttributeId(UUID attributeId) {
        return productAttributeRepository.findByAttributeId(attributeId);
    }

    @Override
    public ProductAttribute save(ProductAttribute productAttribute) {
        return productAttributeRepository.save(productAttribute);
    }

    @Override
    public ProductAttribute update(UUID id, ProductAttribute productAttribute) {

        ProductAttribute existing = findById(id);

        existing.setProduct(productAttribute.getProduct());
        existing.setAttribute(productAttribute.getAttribute());

        return productAttributeRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        productAttributeRepository.deleteById(id);
    }
}