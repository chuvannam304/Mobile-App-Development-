package com.chuvannam.applogin.service.impl;

import com.chuvannam.applogin.entity.Variant;
import com.chuvannam.applogin.repository.VariantRepository;
import com.chuvannam.applogin.service.VariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VariantServiceImpl implements VariantService {

    private final VariantRepository variantRepository;

    @Override
    public List<Variant> findAll() {
        return variantRepository.findAll();
    }

    @Override
    public Variant findById(UUID id) {
        return variantRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Variant not found: " + id));
    }

    @Override
    public List<Variant> findByProductId(UUID productId) {
        return variantRepository.findByProductId(productId);
    }

    @Override
    public List<Variant> findByVariantOptionId(UUID variantOptionId) {
        return variantRepository.findByVariantOptionEntityId(variantOptionId);
    }

    @Override
    public Variant save(Variant variant) {
        return variantRepository.save(variant);
    }

    @Override
    public Variant update(UUID id, Variant variant) {

        Variant existing = findById(id);

        existing.setVariantOption(variant.getVariantOption());
        existing.setProduct(variant.getProduct());
        existing.setVariantOptionEntity(
                variant.getVariantOptionEntity()
        );

        return variantRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        variantRepository.deleteById(id);
    }
}