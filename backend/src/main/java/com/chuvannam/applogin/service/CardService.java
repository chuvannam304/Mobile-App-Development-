package com.chuvannam.applogin.service;

import com.chuvannam.applogin.entity.Card;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CardService {

    List<Card> findAll();

    Card findById(UUID id);

    Card findByCustomerId(UUID customerId);

    Card save(Card card);

    Card update(UUID id, Card card);

    void delete(UUID id);

    Map<String, Object> addToCart(
            String authorization,
            UUID productId,
            String sizeText,
            String colorText
    ) throws Exception;

    List<Map<String, Object>> getMyBag(
            String authorization
    ) throws Exception;
}