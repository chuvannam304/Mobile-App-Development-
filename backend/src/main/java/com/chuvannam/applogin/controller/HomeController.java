package com.chuvannam.applogin.controller;

import com.chuvannam.applogin.entity.HomeProductResponse;
import com.chuvannam.applogin.entity.Product;
import com.chuvannam.applogin.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/home")
@RequiredArgsConstructor
@CrossOrigin("*")
public class HomeController {

    private final ProductRepository productRepository;

    @GetMapping
    public Map<String, List<HomeProductResponse>> home() {
        return Map.of(
                "newProducts", mapProducts(productRepository.findByTagName("NEW")),
                "saleProducts", mapProducts(productRepository.findByTagName("SALE"))
        );
    }

    private List<HomeProductResponse> mapProducts(List<Product> products) {
        return products.stream()
                .map(p -> new HomeProductResponse(
                        p.getId(),
                        p.getSlug(),
                        p.getProductName(),
                        p.getSalePrice(),
                        p.getComparePrice(),
                        p.getShortDescription(),
                        p.getImageUrl()
                ))
                .toList();
    }
}