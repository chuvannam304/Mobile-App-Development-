package com.chuvannam.applogin.repository;

import com.chuvannam.applogin.entity.Sell;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SellRepository
        extends JpaRepository<Sell, UUID> {

    Optional<Sell> findByProductId(UUID productId);

    boolean existsByProductId(UUID productId);
}