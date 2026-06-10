package com.chuvannam.applogin.repository;

import com.chuvannam.applogin.entity.Variant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VariantRepository extends JpaRepository<Variant, UUID> {

    List<Variant> findByProductId(UUID productId);

    List<Variant> findByVariantOptionEntityId(UUID variantOptionId);
}