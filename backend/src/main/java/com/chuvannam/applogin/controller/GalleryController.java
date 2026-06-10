package com.chuvannam.applogin.controller;

import com.chuvannam.applogin.entity.Gallery;
import com.chuvannam.applogin.service.GalleryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/gallery")
@RequiredArgsConstructor
@CrossOrigin("*")
public class GalleryController {

    private final GalleryService galleryService;

    @GetMapping
    public ResponseEntity<List<Gallery>> getAll() {
        return ResponseEntity.ok(
                galleryService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Gallery> getById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                galleryService.findById(id)
        );
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Gallery>> getByProductId(
            @PathVariable UUID productId
    ) {
        return ResponseEntity.ok(
                galleryService.findByProductId(productId)
        );
    }

    @PostMapping
    public ResponseEntity<Gallery> create(
            @RequestBody Gallery gallery
    ) {
        return ResponseEntity.ok(
                galleryService.save(gallery)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Gallery> update(
            @PathVariable UUID id,
            @RequestBody Gallery gallery
    ) {
        return ResponseEntity.ok(
                galleryService.update(id, gallery)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable UUID id
    ) {
        galleryService.delete(id);

        return ResponseEntity.ok(
                "Gallery deleted successfully"
        );
    }
}