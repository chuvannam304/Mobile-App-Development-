package com.chuvannam.applogin.controller;

import com.chuvannam.applogin.entity.Card;
import com.chuvannam.applogin.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CardController {

    private final CardService cardService;

    @GetMapping
    public ResponseEntity<List<Card>> getAll() {
        return ResponseEntity.ok(
                cardService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Card> getById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                cardService.findById(id)
        );
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Card> getByCustomerId(
            @PathVariable UUID customerId
    ) {
        return ResponseEntity.ok(
                cardService.findByCustomerId(customerId)
        );
    }

    @PostMapping
    public ResponseEntity<Card> create(
            @RequestBody Card card
    ) {
        return ResponseEntity.ok(
                cardService.save(card)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Card> update(
            @PathVariable UUID id,
            @RequestBody Card card
    ) {
        return ResponseEntity.ok(
                cardService.update(id, card)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable UUID id
    ) {
        cardService.delete(id);

        return ResponseEntity.ok(
                "Card deleted successfully"
        );
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addToCart(
            @RequestHeader("Authorization") String authorization,
            @RequestBody Map<String, String> body
    ) throws Exception {

        UUID productId = UUID.fromString(
                body.get("productId")
        );

        String sizeText = body.getOrDefault(
                "sizeText",
                ""
        );

        String colorText = body.getOrDefault(
                "colorText",
                ""
        );

        return ResponseEntity.ok(
                cardService.addToCart(
                        authorization,
                        productId,
                        sizeText,
                        colorText
                )
        );
    }

    @GetMapping("/my-bag")
    public ResponseEntity<List<Map<String, Object>>> getMyBag(
            @RequestHeader("Authorization") String authorization
    ) throws Exception {

        return ResponseEntity.ok(
                cardService.getMyBag(
                        authorization
                )
        );
    }
}