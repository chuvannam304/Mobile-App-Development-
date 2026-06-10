package com.chuvannam.applogin.service.impl;

import com.chuvannam.applogin.entity.ProductTag;
import com.chuvannam.applogin.repository.ProductTagRepository;
import com.chuvannam.applogin.service.ProductTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductTagServiceImpl implements ProductTagService {

    private final ProductTagRepository productTagRepository;

    @Override
    public List<ProductTag> findAll() {
        return productTagRepository.findAll();
    }

    @Override
    public ProductTag findById(UUID id) {
        return productTagRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("ProductTag not found: " + id));
    }

    @Override
    public List<ProductTag> findByProductId(UUID productId) {
        return productTagRepository.findByProductId(productId);
    }

    @Override
    public List<ProductTag> findByTagId(UUID tagId) {
        return productTagRepository.findByTagId(tagId);
    }

    @Override
    public ProductTag save(ProductTag productTag) {
        return productTagRepository.save(productTag);
    }

    @Override
    public ProductTag update(UUID id, ProductTag productTag) {

        ProductTag existing = findById(id);

        existing.setProduct(
                productTag.getProduct()
        );

        existing.setTag(
                productTag.getTag()
        );

        return productTagRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        productTagRepository.deleteById(id);
    }
}