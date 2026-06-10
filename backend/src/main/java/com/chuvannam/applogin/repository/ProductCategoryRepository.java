package com.chuvannam.applogin.repository;

import com.chuvannam.applogin.entity.ProductCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProductCategoryRepository
        extends JpaRepository<ProductCategory, UUID> {

    @Query("""
            SELECT pc.product
            FROM ProductCategory pc
            WHERE pc.category.id = :categoryId
            """)
    List<com.chuvannam.applogin.entity.Product> findProductsByCategoryId(
            @Param("categoryId") UUID categoryId
    );
}