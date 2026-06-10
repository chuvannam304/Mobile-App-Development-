package com.chuvannam.applogin.repository;

import com.chuvannam.applogin.entity.CardItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;
import java.util.UUID;

public interface CardItemRepository
        extends JpaRepository<CardItem, UUID> {

    List<CardItem> findByCardId(UUID cardId);

    List<CardItem> findByProductId(UUID productId);
    Optional<CardItem> findByCardIdAndProductIdAndSizeTextAndColorText(
        UUID cardId,
        UUID productId,
        String sizeText,
        String colorText
);
}