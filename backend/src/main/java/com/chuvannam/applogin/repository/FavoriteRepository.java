package com.chuvannam.applogin.repository;

import com.chuvannam.applogin.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FavoriteRepository extends JpaRepository<Favorite, UUID> {

    List<Favorite> findByStaffAccountIdOrderByCreatedAtDesc(UUID staffAccountId);

    Optional<Favorite> findByStaffAccountIdAndProductIdAndSizeTextAndColorText(
            UUID staffAccountId,
            UUID productId,
            String sizeText,
            String colorText
    );

    boolean existsByStaffAccountIdAndProductIdAndSizeTextAndColorText(
            UUID staffAccountId,
            UUID productId,
            String sizeText,
            String colorText
    );

    void deleteByStaffAccountIdAndProductIdAndSizeTextAndColorText(
            UUID staffAccountId,
            UUID productId,
            String sizeText,
            String colorText
    );
}