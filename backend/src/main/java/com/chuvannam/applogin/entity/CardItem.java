package com.chuvannam.applogin.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "card_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardItem {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private Card card;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private Integer quantity = 1;

    @Column(name = "size_text")
    private String sizeText;

    @Column(name = "color_text")
    private String colorText;
}