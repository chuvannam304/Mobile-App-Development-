package com.chuvannam.applogin.service;

import com.chuvannam.applogin.entity.Product;
import com.chuvannam.applogin.entity.ProductCategory;
import java.util.List;
import java.util.UUID;

public interface ProductCategoryService {
    List<ProductCategory> findAll();
    ProductCategory findById(UUID id);
    ProductCategory save(ProductCategory productCategory);
    ProductCategory update(UUID id, ProductCategory productCategory);
    void delete(UUID id);
    List<Product> findProductsByCategoryId(UUID categoryId);
}
