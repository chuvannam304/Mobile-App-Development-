package com.chuvannam.applogin.controller;

import com.chuvannam.applogin.entity.Slideshow;
import com.chuvannam.applogin.service.SlideshowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/slideshows")
@RequiredArgsConstructor
@CrossOrigin("*")
public class SlideshowController {

    private final SlideshowService slideshowService;

    @GetMapping
    public ResponseEntity<List<Slideshow>> getAll() {
        return ResponseEntity.ok(
                slideshowService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Slideshow> getById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                slideshowService.findById(id)
        );
    }

    @GetMapping("/published")
    public ResponseEntity<List<Slideshow>> getPublished() {
        return ResponseEntity.ok(
                slideshowService.findPublished()
        );
    }

    @GetMapping("/search")
    public ResponseEntity<List<Slideshow>> search(
            @RequestParam String keyword
    ) {
        return ResponseEntity.ok(
                slideshowService.searchByTitle(keyword)
        );
    }

    @PostMapping
    public ResponseEntity<Slideshow> create(
            @RequestBody Slideshow slideshow
    ) {
        return ResponseEntity.ok(
                slideshowService.save(slideshow)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Slideshow> update(
            @PathVariable UUID id,
            @RequestBody Slideshow slideshow
    ) {
        return ResponseEntity.ok(
                slideshowService.update(id, slideshow)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable UUID id
    ) {
        slideshowService.delete(id);

        return ResponseEntity.ok(
                "Slideshow deleted successfully"
        );
    }
}