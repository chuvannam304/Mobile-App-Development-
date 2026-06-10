package com.chuvannam.applogin.service.impl;

import com.chuvannam.applogin.entity.Card;
import com.chuvannam.applogin.entity.CardItem;
import com.chuvannam.applogin.entity.Customer;
import com.chuvannam.applogin.entity.Product;
import com.chuvannam.applogin.repository.CardItemRepository;
import com.chuvannam.applogin.repository.CardRepository;
import com.chuvannam.applogin.repository.CustomerRepository;
import com.chuvannam.applogin.repository.ProductRepository;
import com.chuvannam.applogin.service.CardService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final CardItemRepository cardItemRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Override
    public List<Card> findAll() {
        return cardRepository.findAll();
    }

    @Override
    public Card findById(UUID id) {
        return cardRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Card not found: " + id));
    }

    @Override
    public Card findByCustomerId(UUID customerId) {
        return cardRepository.findByCustomerId(customerId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Card not found for customer: " + customerId
                        ));
    }

    @Override
    public Card save(Card card) {
        return cardRepository.save(card);
    }

    @Override
    public Card update(UUID id, Card card) {

        Card existing = findById(id);

        existing.setCustomer(
                card.getCustomer()
        );

        return cardRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        cardRepository.deleteById(id);
    }

    @Override
    public Map<String, Object> addToCart(
            String authorization,
            UUID productId,
            String sizeText,
            String colorText
    ) throws Exception {

        String email = getEmailFromAuthorization(authorization);

        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found: " + email));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found: " + productId));

        Card card = cardRepository.findByCustomerId(customer.getId())
                .orElseGet(() -> {
                    Card newCard = Card.builder()
                            .customer(customer)
                            .build();

                    return cardRepository.save(newCard);
                });

        Optional<CardItem> existingItem =
                cardItemRepository.findByCardIdAndProductIdAndSizeTextAndColorText(
                        card.getId(),
                        product.getId(),
                        sizeText,
                        colorText
                );

        CardItem item;

        if (existingItem.isPresent()) {
            item = existingItem.get();
            item.setQuantity(item.getQuantity() + 1);
        } else {
            item = CardItem.builder()
                    .card(card)
                    .product(product)
                    .quantity(1)
                    .sizeText(sizeText)
                    .colorText(colorText)
                    .build();
        }

        CardItem savedItem = cardItemRepository.save(item);

        return Map.of(
                "message", "Added to cart successfully",
                "cardId", card.getId(),
                "itemId", savedItem.getId(),
                "productId", product.getId(),
                "quantity", savedItem.getQuantity(),
                "sizeText", savedItem.getSizeText(),
                "colorText", savedItem.getColorText()
        );
    }

    @Override
    public List<Map<String, Object>> getMyBag(
            String authorization
    ) throws Exception {

        String email = getEmailFromAuthorization(authorization);

        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found: " + email));

        Optional<Card> cardOptional = cardRepository.findByCustomerId(customer.getId());

        if (cardOptional.isEmpty()) {
            return new ArrayList<>();
        }

        Card card = cardOptional.get();

        List<CardItem> items = cardItemRepository.findByCardId(card.getId());

        List<Map<String, Object>> result = new ArrayList<>();

        for (CardItem item : items) {
            Product product = item.getProduct();

            result.add(
                    Map.of(
                            "itemId", item.getId(),
                            "cardId", card.getId(),
                            "productId", product.getId(),
                            "productName", product.getProductName(),
                            "imageUrl", product.getImageUrl() == null ? "" : product.getImageUrl(),
                            "price", product.getSalePrice(),
                            "quantity", item.getQuantity(),
                            "sizeText", item.getSizeText() == null ? "" : item.getSizeText(),
                            "colorText", item.getColorText() == null ? "" : item.getColorText()
                    )
            );
        }

        return result;
    }

    private String getEmailFromAuthorization(String authorization) throws Exception {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new RuntimeException("Missing token");
        }

        String token = authorization.substring(7);
        String[] parts = token.split("\\.");

        if (parts.length < 2) {
            throw new RuntimeException("Invalid token");
        }

        byte[] decodedBytes = Base64.getUrlDecoder().decode(parts[1]);
        String payload = new String(decodedBytes, StandardCharsets.UTF_8);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(payload);

        String email = jsonNode.has("sub") ? jsonNode.get("sub").asText() : null;

        if (email == null || email.isBlank()) {
            throw new RuntimeException("Token does not contain email");
        }

        return email;
    }
}