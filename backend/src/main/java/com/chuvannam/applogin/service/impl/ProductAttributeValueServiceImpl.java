package com.chuvannam.applogin.service.impl;

import com.chuvannam.applogin.entity.ProductAttributeValue;
import com.chuvannam.applogin.repository.ProductAttributeValueRepository;
import com.chuvannam.applogin.service.ProductAttributeValueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductAttributeValueServiceImpl
        implements ProductAttributeValueService {

    private final ProductAttributeValueRepository productAttributeValueRepository;

    @Override
    public List<ProductAttributeValue> findAll() {
        return productAttributeValueRepository.findAll();
    }

    @Override
    public ProductAttributeValue findById(UUID id) {
        return productAttributeValueRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("ProductAttributeValue not found: " + id));
    }

    @Override
    public List<ProductAttributeValue> findByProductAttributeId(UUID productAttributeId) {
        return productAttributeValueRepository.findByProductAttributeId(productAttributeId);
    }

    @Override
    public List<ProductAttributeValue> findByAttributeValueId(UUID attributeValueId) {
        return productAttributeValueRepository.findByAttributeValueId(attributeValueId);
    }

    @Override
    public ProductAttributeValue save(ProductAttributeValue productAttributeValue) {
        return productAttributeValueRepository.save(productAttributeValue);
    }

    @Override
    public ProductAttributeValue update(
            UUID id,
            ProductAttributeValue productAttributeValue
    ) {
        ProductAttributeValue existing = findById(id);

        existing.setProductAttribute(productAttributeValue.getProductAttribute());
        existing.setAttributeValue(productAttributeValue.getAttributeValue());

        return productAttributeValueRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        productAttributeValueRepository.deleteById(id);
    }
}