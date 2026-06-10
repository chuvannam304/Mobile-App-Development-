package com.chuvannam.applogin.service.impl;

import com.chuvannam.applogin.entity.Gallery;
import com.chuvannam.applogin.repository.GalleryRepository;
import com.chuvannam.applogin.service.GalleryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GalleryServiceImpl implements GalleryService {

    private final GalleryRepository galleryRepository;

    @Override
    public List<Gallery> findAll() {
        return galleryRepository.findAll();
    }

    @Override
    public Gallery findById(UUID id) {
        return galleryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Gallery not found: " + id));
    }

    @Override
    public List<Gallery> findByProductId(UUID productId) {
        return galleryRepository.findByProductId(productId);
    }

    @Override
    public Gallery save(Gallery gallery) {
        return galleryRepository.save(gallery);
    }

    @Override
    public Gallery update(UUID id, Gallery gallery) {

        Gallery existing = findById(id);

        existing.setProduct(gallery.getProduct());
        existing.setImage(gallery.getImage());
        existing.setPlaceholder(gallery.getPlaceholder());
        existing.setIsThumbnail(gallery.getIsThumbnail());

        return galleryRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        galleryRepository.deleteById(id);
    }
}