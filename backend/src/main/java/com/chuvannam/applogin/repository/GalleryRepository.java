package com.chuvannam.applogin.repository;

import com.chuvannam.applogin.entity.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GalleryRepository extends JpaRepository<Gallery, UUID> {

    List<Gallery> findByProductId(UUID productId);

    List<Gallery> findByProductIdOrderByIsThumbnailDesc(UUID productId);
}