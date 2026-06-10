package com.chuvannam.applogin.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProductReviewController {

        private final JdbcTemplate jdbcTemplate;

        @GetMapping("/product/{productId}")
        public List<Map<String, Object>> getReviewsByProduct(
                        @PathVariable UUID productId) {
                List<Map<String, Object>> reviews = jdbcTemplate.queryForList(
                                """
                                                SELECT
                                                    pr.*
                                                FROM product_reviews pr
                                                WHERE pr.product_id = ?
                                                ORDER BY pr.created_at DESC
                                                """,
                                productId);

                for (Map<String, Object> review : reviews) {
                        UUID reviewId = (UUID) review.get("id");

                        List<String> images = jdbcTemplate.queryForList(
                                        """
                                                        SELECT image_url
                                                        FROM review_images
                                                        WHERE review_id = ?
                                                        """,
                                        String.class,
                                        reviewId);

                        review.put("images", images);
                }

                return reviews;
        }

        @GetMapping("/product/{productId}/summary")
        public Map<String, Object> getReviewSummary(
                        @PathVariable UUID productId) {
                Map<String, Object> summary = jdbcTemplate.queryForMap(
                                """
                                                SELECT
                                                    COALESCE(ROUND(AVG(rating)::numeric, 1), 0) AS average_rating,
                                                    COUNT(*) AS review_count
                                                FROM product_reviews
                                                WHERE product_id = ?
                                                """,
                                productId);

                return Map.of(
                                "averageRating", summary.get("average_rating"),
                                "reviewCount", summary.get("review_count"));
        }

        @GetMapping("/check/{productId}")
        public ResponseEntity<Boolean> checkReviewed(
                        @PathVariable UUID productId,
                        @RequestHeader("Authorization") String authHeader) throws Exception {

                String email = getEmailFromAuthorization(authHeader);

                Map<String, Object> customer = jdbcTemplate.queryForMap(
                                """
                                                SELECT id
                                                FROM staff_accounts
                                                WHERE email = ?
                                                """,
                                email);

                UUID customerId = (UUID) customer.get("id");

                Integer count = jdbcTemplate.queryForObject(
                                """
                                                SELECT COUNT(*)
                                                FROM product_reviews
                                                WHERE product_id = ?
                                                AND customer_id = ?
                                                """,
                                Integer.class,
                                productId,
                                customerId);

                return ResponseEntity.ok(
                                count != null && count > 0);
        }

        @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public Map<String, Object> createReview(
                        @RequestHeader("Authorization") String authorization,
                        @RequestParam UUID productId,
                        @RequestParam Integer rating,
                        @RequestParam(required = false) String reviewText,
                        @RequestParam(required = false) List<MultipartFile> images) throws Exception {

                String email = getEmailFromAuthorization(authorization);

                Map<String, Object> staff = jdbcTemplate.queryForMap(
                                """
                                                SELECT *
                                                FROM staff_accounts
                                                WHERE email = ?
                                                """,
                                email);

                UUID staffId = (UUID) staff.get("id");

                String firstName = staff.get("first_name") == null
                                ? "User"
                                : staff.get("first_name").toString();

                String lastName = staff.get("last_name") == null
                                ? "Customer"
                                : staff.get("last_name").toString();

                String passwordHash = staff.get("password_hash") == null
                                ? ""
                                : staff.get("password_hash").toString();

                Boolean active = staff.get("active") == null
                                ? true
                                : (Boolean) staff.get("active");

                Integer countCustomer = jdbcTemplate.queryForObject(
                                """
                                                SELECT COUNT(*)
                                                FROM customers
                                                WHERE id = ?
                                                """,
                                Integer.class,
                                staffId);

                if (countCustomer == null || countCustomer == 0) {
                        jdbcTemplate.update(
                                        """
                                                        INSERT INTO customers (
                                                            id,
                                                            first_name,
                                                            last_name,
                                                            email,
                                                            password_hash,
                                                            active,
                                                            registered_at,
                                                            updated_at
                                                        )
                                                        VALUES (?, ?, ?, ?, ?, ?, NOW(), NOW())
                                                        """,
                                        staffId,
                                        firstName,
                                        lastName,
                                        email,
                                        passwordHash,
                                        active);
                }

                UUID reviewId = UUID.randomUUID();

                if (reviewText == null) {
                        reviewText = "";
                }

                String userName = (firstName + " " + lastName).trim();

                jdbcTemplate.update(
                                """
                                                INSERT INTO product_reviews (
                                                    id,
                                                    product_id,
                                                    customer_id,
                                                    user_name,
                                                    rating,
                                                    review_text,
                                                    helpful_count,
                                                    created_at
                                                )
                                                VALUES (?, ?, ?, ?, ?, ?, 0, NOW())
                                                """,
                                reviewId,
                                productId,
                                staffId,
                                userName,
                                rating,
                                reviewText);

                List<String> imageUrls = new ArrayList<>();

                if (images != null && !images.isEmpty()) {
                        Path uploadDir = Paths.get("uploads", "reviews");
                        Files.createDirectories(uploadDir);

                        for (MultipartFile image : images) {
                                if (image.isEmpty())
                                        continue;

                                String originalName = image.getOriginalFilename();
                                String ext = "";

                                if (originalName != null && originalName.contains(".")) {
                                        ext = originalName.substring(originalName.lastIndexOf("."));
                                }

                                String fileName = UUID.randomUUID() + ext;
                                Path filePath = uploadDir.resolve(fileName);

                                Files.copy(image.getInputStream(), filePath);

                                String imageUrl = "/uploads/reviews/" + fileName;
                                imageUrls.add(imageUrl);

                                jdbcTemplate.update(
                                                """
                                                                INSERT INTO review_images (
                                                                    review_id,
                                                                    image_url
                                                                )
                                                                VALUES (?, ?)
                                                                """,
                                                reviewId,
                                                imageUrl);
                        }
                }

                return Map.of(
                                "message", "Review created successfully",
                                "reviewId", reviewId,
                                "productId", productId,
                                "customerId", staffId,
                                "userName", userName,
                                "images", imageUrls);
        }

        private String getEmailFromAuthorization(String authorization) throws Exception {
                if (authorization == null || !authorization.startsWith("Bearer ")) {
                        throw new RuntimeException("Missing token");
                }

                String token = authorization.substring(7);
                String[] parts = token.split("\\.");

                if (parts.length < 2) {
                        throw new RuntimeException("Invalid token");
                }

                byte[] decodedBytes = Base64.getUrlDecoder().decode(parts[1]);
                String payload = new String(decodedBytes, StandardCharsets.UTF_8);

                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(payload);

                String email = jsonNode.has("sub") ? jsonNode.get("sub").asText() : null;

                if (email == null || email.isBlank()) {
                        throw new RuntimeException("Token does not contain email");
                }

                return email;
        }
}