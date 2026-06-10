package com.chuvannam.applogin.service.impl;

import com.chuvannam.applogin.entity.Product;
import com.chuvannam.applogin.entity.ProductCategory;
import com.chuvannam.applogin.repository.ProductCategoryRepository;
import com.chuvannam.applogin.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    @Override
    public List<ProductCategory> findAll() {
        return productCategoryRepository.findAll();
    }

    @Override
    public ProductCategory findById(UUID id) {
        return productCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductCategory not found"));
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        if (productCategory.getId() == null) productCategory.setId(UUID.randomUUID());
        return productCategoryRepository.save(productCategory);
    }

    @Override
    public ProductCategory update(UUID id, ProductCategory productCategory) {
        ProductCategory old = findById(id);
        old.setProduct(productCategory.getProduct());
        old.setCategory(productCategory.getCategory());
        return productCategoryRepository.save(old);
    }

    @Override
    public void delete(UUID id) {
        productCategoryRepository.deleteById(id);
    }

    @Override
    public List<Product> findProductsByCategoryId(UUID categoryId) {
        return productCategoryRepository.findProductsByCategoryId(categoryId);
    }
}
