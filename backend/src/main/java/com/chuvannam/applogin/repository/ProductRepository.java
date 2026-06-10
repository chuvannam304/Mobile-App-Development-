package com.chuvannam.applogin.repository;

import com.chuvannam.applogin.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query(value = """
        SELECT p.*
        FROM products p
        JOIN product_tags pt ON pt.product_id = p.id
        JOIN tags t ON t.id = pt.tag_id
        WHERE LOWER(t.tag_name) = LOWER(:tagName)
        AND p.published = true
        ORDER BY p.created_at DESC
        """, nativeQuery = true)
    List<Product> findByTagName(String tagName);
    // List<Product> findByCategoryId(UUID categoryId);
}