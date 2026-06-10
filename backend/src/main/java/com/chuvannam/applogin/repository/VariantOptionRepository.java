package com.chuvannam.applogin.repository;

import com.chuvannam.applogin.entity.VariantOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VariantOptionRepository
        extends JpaRepository<VariantOption, UUID> {

    List<VariantOption> findByProductId(UUID productId);

    List<VariantOption> findByProductIdAndActiveTrue(UUID productId);
}