package com.chuvannam.applogin.service;

import com.chuvannam.applogin.entity.Favorite;

import java.util.List;
import java.util.UUID;

public interface FavoriteService {

    Favorite addFavorite(UUID staffAccountId, UUID productId, String size, String color);

    List<Favorite> getFavorites(UUID staffAccountId);

    void removeFavorite(UUID staffAccountId, UUID productId, String size, String color);
}