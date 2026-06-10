package com.chuvannam.applogin.controller;

import com.chuvannam.applogin.entity.CategoryProductResponse;
import com.chuvannam.applogin.entity.Product;
import com.chuvannam.applogin.entity.ProductCategory;
import com.chuvannam.applogin.service.ProductCategoryService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CategoryProductController {

    private final ProductCategoryService productCategoryService;

    @GetMapping("/{categoryId}/products")
    public List<CategoryProductResponse> getProductsByCategory(
            @PathVariable UUID categoryId) {

        List<Product> products =
                productCategoryService.findProductsByCategoryId(categoryId);

        return products.stream()
                .map(product -> new CategoryProductResponse(
                        product.getId(),
                        product.getSlug(),
                        product.getProductName(),
                        product.getSalePrice(),
                        product.getComparePrice(),
                        product.getShortDescription(),
                        product.getImageUrl()
                ))
                .toList();
    }


    // CRUD cho bảng product_categories - dùng đường dẫn riêng để không đụng Flutter
    @GetMapping("/product-categories/all")
    public List<ProductCategory> getAllProductCategories() {
        return productCategoryService.findAll();
    }

    @GetMapping("/product-categories/{id}")
    public ProductCategory getProductCategoryById(@PathVariable UUID id) {
        return productCategoryService.findById(id);
    }

    @PostMapping("/product-categories")
    public ProductCategory createProductCategory(@RequestBody ProductCategory productCategory) {
        return productCategoryService.save(productCategory);
    }

    @PutMapping("/product-categories/{id}")
    public ProductCategory updateProductCategory(@PathVariable UUID id, @RequestBody ProductCategory productCategory) {
        return productCategoryService.update(id, productCategory);
    }

    @DeleteMapping("/product-categories/{id}")
    public void deleteProductCategory(@PathVariable UUID id) {
        productCategoryService.delete(id);
    }

}