package com.chuvannam.applogin.controller;

import com.chuvannam.applogin.entity.Attribute;
import com.chuvannam.applogin.service.AttributeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/attributes")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AttributeController {

    private final AttributeService attributeService;

    @GetMapping
    public ResponseEntity<List<Attribute>> getAll() {
        return ResponseEntity.ok(attributeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Attribute> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(attributeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Attribute> create(@RequestBody Attribute attribute) {
        return ResponseEntity.ok(attributeService.save(attribute));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Attribute> update(
            @PathVariable UUID id,
            @RequestBody Attribute attribute
    ) {
        return ResponseEntity.ok(attributeService.update(id, attribute));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        attributeService.delete(id);
        return ResponseEntity.ok("Attribute deleted successfully");
    }
}