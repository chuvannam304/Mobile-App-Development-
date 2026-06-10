package com.chuvannam.applogin.service;

import com.chuvannam.applogin.entity.Sell;

import java.util.List;
import java.util.UUID;

public interface SellService {

    List<Sell> findAll();

    Sell findById(UUID id);

    Sell findByProductId(UUID productId);

    Sell save(Sell sell);

    Sell update(UUID id, Sell sell);

    void delete(UUID id);
}