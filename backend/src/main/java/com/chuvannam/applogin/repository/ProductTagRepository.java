package com.chuvannam.applogin.repository;

import com.chuvannam.applogin.entity.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductTagRepository
        extends JpaRepository<ProductTag, UUID> {

    List<ProductTag> findByProductId(UUID productId);

    List<ProductTag> findByTagId(UUID tagId);
}