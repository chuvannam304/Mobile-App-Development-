package com.chuvannam.applogin.service.impl;

import com.chuvannam.applogin.entity.Product;
import com.chuvannam.applogin.repository.ProductRepository;
import com.chuvannam.applogin.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public Product save(Product product) {
        if (product.getId() == null) product.setId(UUID.randomUUID());
        if (product.getCreatedAt() == null) product.setCreatedAt(OffsetDateTime.now());
        return productRepository.save(product);
    }

    @Override
    public Product update(UUID id, Product product) {
        Product old = findById(id);
        old.setSlug(product.getSlug());
        old.setProductName(product.getProductName());
        old.setSalePrice(product.getSalePrice());
        old.setComparePrice(product.getComparePrice());
        old.setQuantity(product.getQuantity());
        old.setShortDescription(product.getShortDescription());
        old.setProductDescription(product.getProductDescription());
        old.setPublished(product.getPublished());
        old.setImageUrl(product.getImageUrl());
        old.setSku(product.getSku());
        old.setCategory(product.getCategory());
        return productRepository.save(old);
    }

    @Override
    public void delete(UUID id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> findByTagName(String tagName) {
        return productRepository.findByTagName(tagName);
    }
}
