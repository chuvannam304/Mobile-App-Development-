package com.chuvannam.applogin.service.impl;

import com.chuvannam.applogin.entity.Favorite;
import com.chuvannam.applogin.entity.Product;
import com.chuvannam.applogin.entity.StaffAccount;
import com.chuvannam.applogin.repository.FavoriteRepository;
import com.chuvannam.applogin.repository.ProductRepository;
import com.chuvannam.applogin.repository.StaffAccountRepository;
import com.chuvannam.applogin.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

        private final FavoriteRepository favoriteRepository;
        private final StaffAccountRepository staffAccountRepository;
        private final ProductRepository productRepository;

        @Override
        public Favorite addFavorite(
                        UUID staffAccountId,
                        UUID productId,
                        String size,
                        String color) {

                StaffAccount staffAccount = staffAccountRepository
                                .findById(staffAccountId)
                                .orElseThrow(() -> new RuntimeException("User not found"));

                Product product = productRepository
                                .findById(productId)
                                .orElseThrow(() -> new RuntimeException("Product not found"));

                return favoriteRepository
                                .findByStaffAccountIdAndProductIdAndSizeTextAndColorText(
                                                staffAccountId,
                                                productId,
                                                size,
                                                color)
                                .orElseGet(() -> favoriteRepository.save(
                                                Favorite.builder()
                                                                .staffAccount(staffAccount)
                                                                .product(product)
                                                                .sizeText(size)
                                                                .colorText(color)
                                                                .build()));
        }

        @Override
        public List<Favorite> getFavorites(UUID staffAccountId) {
                return favoriteRepository
                                .findByStaffAccountIdOrderByCreatedAtDesc(staffAccountId);
        }

        @Override
        @Transactional
        public void removeFavorite(
                        UUID staffAccountId,
                        UUID productId,
                        String size,
                        String color) {
                favoriteRepository
                                .deleteByStaffAccountIdAndProductIdAndSizeTextAndColorText(
                                                staffAccountId,
                                                productId,
                                                size,
                                                color);
        }

}