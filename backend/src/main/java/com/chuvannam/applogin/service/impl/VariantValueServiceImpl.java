package com.chuvannam.applogin.service.impl;

import com.chuvannam.applogin.entity.VariantValue;
import com.chuvannam.applogin.repository.VariantValueRepository;
import com.chuvannam.applogin.service.VariantValueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VariantValueServiceImpl implements VariantValueService {

    private final VariantValueRepository variantValueRepository;

    @Override
    public List<VariantValue> findAll() {
        return variantValueRepository.findAll();
    }

    @Override
    public VariantValue findById(UUID id) {
        return variantValueRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("VariantValue not found: " + id));
    }

    @Override
    public List<VariantValue> findByVariantId(UUID variantId) {
        return variantValueRepository.findByVariantId(variantId);
    }

    @Override
    public List<VariantValue> findByProductAttributeValueId(
            UUID productAttributeValueId
    ) {
        return variantValueRepository.findByProductAttributeValueId(
                productAttributeValueId
        );
    }

    @Override
    public VariantValue save(VariantValue variantValue) {
        return variantValueRepository.save(variantValue);
    }

    @Override
    public VariantValue update(
            UUID id,
            VariantValue variantValue
    ) {
        VariantValue existing = findById(id);

        existing.setVariant(
                variantValue.getVariant()
        );

        existing.setProductAttributeValue(
                variantValue.getProductAttributeValue()
        );

        return variantValueRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        variantValueRepository.deleteById(id);
    }
}