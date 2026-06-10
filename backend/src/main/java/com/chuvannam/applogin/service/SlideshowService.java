package com.chuvannam.applogin.service;

import com.chuvannam.applogin.entity.Slideshow;

import java.util.List;
import java.util.UUID;

public interface SlideshowService {

    List<Slideshow> findAll();

    Slideshow findById(UUID id);

    List<Slideshow> findPublished();

    List<Slideshow> searchByTitle(String keyword);

    Slideshow save(Slideshow slideshow);

    Slideshow update(UUID id, Slideshow slideshow);

    void delete(UUID id);
}