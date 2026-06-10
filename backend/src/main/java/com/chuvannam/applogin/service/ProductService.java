package com.chuvannam.applogin.service;

import com.chuvannam.applogin.entity.Product;
import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<Product> findAll();
    Product findById(UUID id);
    Product save(Product product);
    Product update(UUID id, Product product);
    void delete(UUID id);
    List<Product> findByTagName(String tagName);
}
