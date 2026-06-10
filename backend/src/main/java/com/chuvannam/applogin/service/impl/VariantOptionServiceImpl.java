package com.chuvannam.applogin.service.impl;

import com.chuvannam.applogin.entity.VariantOption;
import com.chuvannam.applogin.repository.VariantOptionRepository;
import com.chuvannam.applogin.service.VariantOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VariantOptionServiceImpl implements VariantOptionService {

    private final VariantOptionRepository variantOptionRepository;

    @Override
    public List<VariantOption> findAll() {
        return variantOptionRepository.findAll();
    }

    @Override
    public VariantOption findById(UUID id) {
        return variantOptionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("VariantOption not found: " + id));
    }

    @Override
    public List<VariantOption> findByProductId(UUID productId) {
        return variantOptionRepository.findByProductId(productId);
    }

    @Override
    public List<VariantOption> findActiveByProductId(UUID productId) {
        return variantOptionRepository.findByProductIdAndActiveTrue(productId);
    }

    @Override
    public VariantOption save(VariantOption variantOption) {
        return variantOptionRepository.save(variantOption);
    }

    @Override
    public VariantOption update(UUID id, VariantOption variantOption) {

        VariantOption existing = findById(id);

        existing.setTitle(variantOption.getTitle());
        existing.setImage(variantOption.getImage());
        existing.setProduct(variantOption.getProduct());

        existing.setSalePrice(variantOption.getSalePrice());
        existing.setComparePrice(variantOption.getComparePrice());
        existing.setBuyingPrice(variantOption.getBuyingPrice());

        existing.setQuantity(variantOption.getQuantity());
        existing.setSku(variantOption.getSku());
        existing.setActive(variantOption.getActive());

        return variantOptionRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        variantOptionRepository.deleteById(id);
    }
}