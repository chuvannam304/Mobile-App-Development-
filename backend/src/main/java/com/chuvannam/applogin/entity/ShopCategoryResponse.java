package com.chuvannam.applogin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ShopCategoryResponse {

    private UUID id;

    private String categoryName;

    private String image;
}