package com.chuvannam.applogin.controller;

import com.chuvannam.applogin.entity.Tag;
import com.chuvannam.applogin.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TagController {

    private final TagService tagService;

    @GetMapping
    public ResponseEntity<List<Tag>> getAll() {
        return ResponseEntity.ok(
                tagService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> getById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                tagService.findById(id)
        );
    }

    @GetMapping("/name/{tagName}")
    public ResponseEntity<Tag> getByTagName(
            @PathVariable String tagName
    ) {
        return ResponseEntity.ok(
                tagService.findByTagName(tagName)
        );
    }

    @GetMapping("/search")
    public ResponseEntity<List<Tag>> search(
            @RequestParam String keyword
    ) {
        return ResponseEntity.ok(
                tagService.searchByName(keyword)
        );
    }

    @PostMapping
    public ResponseEntity<Tag> create(
            @RequestBody Tag tag
    ) {
        return ResponseEntity.ok(
                tagService.save(tag)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tag> update(
            @PathVariable UUID id,
            @RequestBody Tag tag
    ) {
        return ResponseEntity.ok(
                tagService.update(id, tag)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable UUID id
    ) {
        tagService.delete(id);

        return ResponseEntity.ok(
                "Tag deleted successfully"
        );
    }
}