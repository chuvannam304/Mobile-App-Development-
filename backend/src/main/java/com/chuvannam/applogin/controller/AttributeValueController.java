package com.chuvannam.applogin.controller;

import com.chuvannam.applogin.entity.AttributeValue;
import com.chuvannam.applogin.service.AttributeValueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/attribute-values")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AttributeValueController {

    private final AttributeValueService attributeValueService;

    @GetMapping
    public ResponseEntity<List<AttributeValue>> getAll() {
        return ResponseEntity.ok(
                attributeValueService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttributeValue> getById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                attributeValueService.findById(id)
        );
    }

    @GetMapping("/attribute/{attributeId}")
    public ResponseEntity<List<AttributeValue>> getByAttributeId(
            @PathVariable UUID attributeId
    ) {
        return ResponseEntity.ok(
                attributeValueService.findByAttributeId(attributeId)
        );
    }

    @PostMapping
    public ResponseEntity<AttributeValue> create(
            @RequestBody AttributeValue attributeValue
    ) {
        return ResponseEntity.ok(
                attributeValueService.save(attributeValue)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<AttributeValue> update(
            @PathVariable UUID id,
            @RequestBody AttributeValue attributeValue
    ) {
        return ResponseEntity.ok(
                attributeValueService.update(id, attributeValue)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable UUID id
    ) {
        attributeValueService.delete(id);

        return ResponseEntity.ok(
                "AttributeValue deleted successfully"
        );
    }
}