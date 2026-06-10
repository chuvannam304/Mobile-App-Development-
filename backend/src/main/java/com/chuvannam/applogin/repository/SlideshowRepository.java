package com.chuvannam.applogin.repository;

import com.chuvannam.applogin.entity.Slideshow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SlideshowRepository
        extends JpaRepository<Slideshow, UUID> {

    List<Slideshow> findByPublishedTrue();

    List<Slideshow> findByPublishedTrueOrderByDisplayOrderAsc();

    List<Slideshow> findByTitleContainingIgnoreCase(String keyword);
}