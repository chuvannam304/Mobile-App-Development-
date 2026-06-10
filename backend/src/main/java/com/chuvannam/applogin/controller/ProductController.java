package com.chuvannam.applogin.controller;

import com.chuvannam.applogin.entity.Product;
import com.chuvannam.applogin.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProductController {

        private final JdbcTemplate jdbcTemplate;
        private final ProductService productService;

        @PutMapping("/{productId}/tag")
        public Map<String, Object> updateProductTag(
                        @PathVariable UUID productId,
                        @RequestBody Map<String, String> body) {
                String tagName = body.get("tagName");

                UUID tagId = jdbcTemplate.queryForObject(
                                "SELECT id FROM tags WHERE LOWER(tag_name) = LOWER(?)",
                                UUID.class,
                                tagName);

                jdbcTemplate.update(
                                "DELETE FROM product_tags WHERE product_id = ?",
                                productId);

                jdbcTemplate.update(
                                "INSERT INTO product_tags(product_id, tag_id) VALUES (?, ?)",
                                productId,
                                tagId);

                return Map.of(
                                "message", "Cập nhật tag thành công",
                                "productId", productId,
                                "tagName", tagName);
        }

        @GetMapping("/category/{categoryId}")
        public List<Map<String, Object>> getProductsByCategory(
                        @PathVariable UUID categoryId) {
                return jdbcTemplate.queryForList(
                                """
                                                SELECT p.*
                                                FROM products p
                                                JOIN product_categories pc ON pc.product_id = p.id
                                                WHERE pc.category_id = ?
                                                AND p.published = true
                                                ORDER BY p.created_at DESC
                                                """,
                                categoryId);
        }

        @GetMapping("/category/{categoryId}/all")
        public List<Map<String, Object>> getAllProductsByCategoryTree(
                        @PathVariable UUID categoryId) {
                return jdbcTemplate.queryForList(
                                """
                                                SELECT DISTINCT p.*
                                                FROM products p
                                                JOIN product_categories pc ON pc.product_id = p.id
                                                WHERE pc.category_id IN (
                                                    SELECT id
                                                    FROM categories
                                                    WHERE parent_id = ?
                                                )
                                                AND p.published = true
                                                ORDER BY p.created_at DESC
                                                """,
                                categoryId);
        }

        @GetMapping("/{productId}/related")
        public List<Map<String, Object>> getRelatedProducts(
                        @PathVariable UUID productId) {
                return jdbcTemplate.queryForList(
                                """
                                                SELECT DISTINCT p.*
                                                FROM products p
                                                JOIN product_categories pc ON pc.product_id = p.id
                                                WHERE pc.category_id IN (
                                                    SELECT category_id
                                                    FROM product_categories
                                                    WHERE product_id = ?
                                                )
                                                AND p.id <> ?
                                                AND p.published = true
                                                ORDER BY p.created_at DESC
                                                LIMIT 10
                                                """,
                                productId,
                                productId);
        }


        // CRUD cho bảng products - giữ nguyên endpoint Flutter cũ
        @GetMapping("/all")
        public List<Product> getAll() {
                return productService.findAll();
        }

        @GetMapping("/crud/{id}")
        public Product getById(@PathVariable UUID id) {
                return productService.findById(id);
        }

        @PostMapping
        public Product create(@RequestBody Product product) {
                return productService.save(product);
        }

        @PutMapping("/crud/{id}")
        public Product update(@PathVariable UUID id, @RequestBody Product product) {
                return productService.update(id, product);
        }

        @DeleteMapping("/crud/{id}")
        public void delete(@PathVariable UUID id) {
                productService.delete(id);
        }

}