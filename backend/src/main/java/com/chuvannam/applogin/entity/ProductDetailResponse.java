package com.chuvannam.applogin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ProductDetailResponse {

    private UUID id;

    private String slug;

    private String productName;

    private String sku;

    private BigDecimal salePrice;

    private BigDecimal comparePrice;

    private Integer quantity;

    private String shortDescription;

    private String productDescription;

    private String imageUrl;
}