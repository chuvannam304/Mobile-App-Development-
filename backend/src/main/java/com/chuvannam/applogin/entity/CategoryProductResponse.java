package com.chuvannam.applogin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
public class CategoryProductResponse {

    private UUID id;

    private String slug;

    private String productName;

    private BigDecimal salePrice;

    private BigDecimal comparePrice;

    private String shortDescription;

    private String imageUrl;
}