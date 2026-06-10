package com.chuvannam.applogin.service;

import com.chuvannam.applogin.entity.CardItem;

import java.util.List;
import java.util.UUID;

public interface CardItemService {

    List<CardItem> findAll();

    CardItem findById(UUID id);

    List<CardItem> findByCardId(UUID cardId);

    List<CardItem> findByProductId(UUID productId);

    CardItem save(CardItem cardItem);

    CardItem update(UUID id, CardItem cardItem);

    void delete(UUID id);
}