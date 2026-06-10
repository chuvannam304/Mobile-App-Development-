package com.chuvannam.applogin.controller;

import com.chuvannam.applogin.entity.Favorite;
import com.chuvannam.applogin.entity.Product;
import com.chuvannam.applogin.entity.StaffAccount;
import com.chuvannam.applogin.repository.StaffAccountRepository;
import com.chuvannam.applogin.service.FavoriteService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final StaffAccountRepository staffAccountRepository;

    @PostMapping
    public Map<String, Object> addFavorite(
            Authentication authentication,
            @RequestBody FavoriteRequest request
    ) {
        StaffAccount staff = getStaff(authentication);

        Favorite favorite = favoriteService.addFavorite(
                staff.getId(),
                request.getProductId(),
                request.getSizeText(),
                request.getColorText()
        );

        return toMap(favorite);
    }

    @GetMapping
    public List<Map<String, Object>> getFavorites(Authentication authentication) {
        StaffAccount staff = getStaff(authentication);

        return favoriteService.getFavorites(staff.getId())
                .stream()
                .map(this::toMap)
                .toList();
    }

    @DeleteMapping
    public String removeFavorite(
            Authentication authentication,
            @RequestBody FavoriteRequest request
    ) {
        StaffAccount staff = getStaff(authentication);

        favoriteService.removeFavorite(
                staff.getId(),
                request.getProductId(),
                request.getSizeText(),
                request.getColorText()
        );

        return "Removed favorite successfully";
    }

    private StaffAccount getStaff(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        String email;

        if (principal instanceof UserDetails userDetails) {
            email = userDetails.getUsername();
        } else {
            email = authentication.getName();
        }

        return staffAccountRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private Map<String, Object> toMap(Favorite favorite) {
        Product p = favorite.getProduct();

        Map<String, Object> product = new HashMap<>();
        product.put("id", p.getId());
        product.put("productName", p.getProductName());
        product.put("salePrice", p.getSalePrice());
        product.put("comparePrice", p.getComparePrice());
        product.put("imageUrl", p.getImageUrl());

        Map<String, Object> data = new HashMap<>();
        data.put("id", favorite.getId());
        data.put("sizeText", favorite.getSizeText());
        data.put("colorText", favorite.getColorText());
        data.put("createdAt", favorite.getCreatedAt());
        data.put("product", product);

        return data;
    }
}

@Data
class FavoriteRequest {
    private UUID productId;
    private String sizeText;
    private String colorText;
}