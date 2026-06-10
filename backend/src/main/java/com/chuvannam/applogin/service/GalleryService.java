package com.chuvannam.applogin.service;

import com.chuvannam.applogin.entity.Gallery;

import java.util.List;
import java.util.UUID;

public interface GalleryService {

    List<Gallery> findAll();

    Gallery findById(UUID id);

    List<Gallery> findByProductId(UUID productId);

    Gallery save(Gallery gallery);

    Gallery update(UUID id, Gallery gallery);

    void delete(UUID id);
}