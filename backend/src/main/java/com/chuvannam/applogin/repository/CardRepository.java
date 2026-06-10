package com.chuvannam.applogin.repository;

import com.chuvannam.applogin.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CardRepository
        extends JpaRepository<Card, UUID> {

    Optional<Card> findByCustomerId(UUID customerId);
}