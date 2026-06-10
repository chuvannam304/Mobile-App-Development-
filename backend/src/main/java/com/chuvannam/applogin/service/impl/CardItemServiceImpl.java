package com.chuvannam.applogin.service.impl;

import com.chuvannam.applogin.entity.CardItem;
import com.chuvannam.applogin.repository.CardItemRepository;
import com.chuvannam.applogin.service.CardItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardItemServiceImpl implements CardItemService {

    private final CardItemRepository cardItemRepository;

    @Override
    public List<CardItem> findAll() {
        return cardItemRepository.findAll();
    }

    @Override
    public CardItem findById(UUID id) {
        return cardItemRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("CardItem not found: " + id));
    }

    @Override
    public List<CardItem> findByCardId(UUID cardId) {
        return cardItemRepository.findByCardId(cardId);
    }

    @Override
    public List<CardItem> findByProductId(UUID productId) {
        return cardItemRepository.findByProductId(productId);
    }

    @Override
    public CardItem save(CardItem cardItem) {
        return cardItemRepository.save(cardItem);
    }

    @Override
    public CardItem update(UUID id, CardItem cardItem) {

        CardItem existing = findById(id);

        existing.setCard(
                cardItem.getCard()
        );

        existing.setProduct(
                cardItem.getProduct()
        );

        existing.setQuantity(
                cardItem.getQuantity()
        );

        return cardItemRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        cardItemRepository.deleteById(id);
    }
}