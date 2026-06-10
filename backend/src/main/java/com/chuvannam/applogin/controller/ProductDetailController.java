package com.chuvannam.applogin.controller;

import com.chuvannam.applogin.entity.Product;
import com.chuvannam.applogin.entity.ProductDetailResponse;
import com.chuvannam.applogin.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProductDetailController {

    private final ProductRepository productRepository;

    @GetMapping("/{id}")
    public ProductDetailResponse getProductDetail(
            @PathVariable UUID id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));

        return new ProductDetailResponse(
                product.getId(),
                product.getSlug(),
                product.getProductName(),
                product.getSku(),
                product.getSalePrice(),
                product.getComparePrice(),
                product.getQuantity(),
                product.getShortDescription(),
                product.getProductDescription(),
                product.getImageUrl()
        );
    }
}