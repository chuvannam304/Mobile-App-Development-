package com.chuvannam.applogin.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "products")
@Data
public class Product {

    @Id
    private UUID id;

    private String slug;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "sale_price")
    private BigDecimal salePrice;

    @Column(name = "compare_price")
    private BigDecimal comparePrice;

    private Integer quantity;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "product_description")
    private String productDescription;

    private Boolean published;

    @Column(name = "image")
    private String imageUrl;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;
    @Column(name = "sku")
    private String sku;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}