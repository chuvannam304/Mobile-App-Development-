package com.chuvannam.applogin.controller;

import com.chuvannam.applogin.entity.CardItem;
import com.chuvannam.applogin.service.CardItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/card-items")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CardItemController {

    private final CardItemService cardItemService;

    @GetMapping
    public ResponseEntity<List<CardItem>> getAll() {
        return ResponseEntity.ok(
                cardItemService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardItem> getById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                cardItemService.findById(id)
        );
    }

    @GetMapping("/card/{cardId}")
    public ResponseEntity<List<CardItem>> getByCardId(
            @PathVariable UUID cardId
    ) {
        return ResponseEntity.ok(
                cardItemService.findByCardId(cardId)
        );
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<CardItem>> getByProductId(
            @PathVariable UUID productId
    ) {
        return ResponseEntity.ok(
                cardItemService.findByProductId(productId)
        );
    }

    @PostMapping
    public ResponseEntity<CardItem> create(
            @RequestBody CardItem cardItem
    ) {
        return ResponseEntity.ok(
                cardItemService.save(cardItem)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CardItem> update(
            @PathVariable UUID id,
            @RequestBody CardItem cardItem
    ) {
        return ResponseEntity.ok(
                cardItemService.update(id, cardItem)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable UUID id
    ) {
        cardItemService.delete(id);

        return ResponseEntity.ok(
                "CardItem deleted successfully"
        );
    }
}