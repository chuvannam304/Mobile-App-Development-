package com.chuvannam.applogin.service.impl;

import com.chuvannam.applogin.entity.Slideshow;
import com.chuvannam.applogin.repository.SlideshowRepository;
import com.chuvannam.applogin.service.SlideshowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SlideshowServiceImpl implements SlideshowService {

    private final SlideshowRepository slideshowRepository;

    @Override
    public List<Slideshow> findAll() {
        return slideshowRepository.findAll();
    }

    @Override
    public Slideshow findById(UUID id) {
        return slideshowRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Slideshow not found: " + id));
    }

    @Override
    public List<Slideshow> findPublished() {
        return slideshowRepository.findByPublishedTrueOrderByDisplayOrderAsc();
    }

    @Override
    public List<Slideshow> searchByTitle(String keyword) {
        return slideshowRepository.findByTitleContainingIgnoreCase(keyword);
    }

    @Override
    public Slideshow save(Slideshow slideshow) {
        return slideshowRepository.save(slideshow);
    }

    @Override
    public Slideshow update(UUID id, Slideshow slideshow) {

        Slideshow existing = findById(id);

        existing.setTitle(slideshow.getTitle());
        existing.setDestinationUrl(slideshow.getDestinationUrl());

        existing.setImage(slideshow.getImage());
        existing.setPlaceholder(slideshow.getPlaceholder());

        existing.setDescription(slideshow.getDescription());
        existing.setBtnLabel(slideshow.getBtnLabel());

        existing.setDisplayOrder(slideshow.getDisplayOrder());

        existing.setPublished(slideshow.getPublished());
        existing.setClicks(slideshow.getClicks());

        existing.setStyles(slideshow.getStyles());

        existing.setCreatedBy(slideshow.getCreatedBy());
        existing.setUpdatedBy(slideshow.getUpdatedBy());

        return slideshowRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        slideshowRepository.deleteById(id);
    }
}