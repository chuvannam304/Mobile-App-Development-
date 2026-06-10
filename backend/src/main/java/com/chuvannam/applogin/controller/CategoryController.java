package com.chuvannam.applogin.controller;

import com.chuvannam.applogin.entity.Category;
import com.chuvannam.applogin.entity.ShopCategoryResponse;
import com.chuvannam.applogin.service.CategoryService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CategoryController {

    private final CategoryService categoryService;

    // Lấy category cha: Women, Men, Kids
    @GetMapping("/parents")
    public List<ShopCategoryResponse> getParentCategories() {
        List<Category> categories =
                categoryService.findParentCategories();

        return categories.stream()
                .map(category -> new ShopCategoryResponse(
                        category.getId(),
                        category.getCategoryName(),
                        category.getImage()
                ))
                .toList();
    }

    // Lấy category con theo parentId
    @GetMapping("/{parentId}/children")
    public List<ShopCategoryResponse> getChildrenByParentId(
            @PathVariable UUID parentId
    ) {
        List<Category> categories =
                categoryService.findChildrenByParentId(parentId);

        return categories.stream()
                .map(category -> new ShopCategoryResponse(
                        category.getId(),
                        category.getCategoryName(),
                        category.getImage()
                ))
                .toList();
    }


    // CRUD cho bảng categories - không đụng endpoint Flutter cũ
    @GetMapping("/all")
    public List<Category> getAll() {
        return categoryService.findAll();
    }

    @GetMapping("/detail/{id}")
    public Category getById(@PathVariable UUID id) {
        return categoryService.findById(id);
    }

    @PostMapping
    public Category create(@RequestBody Category category) {
        return categoryService.save(category);
    }

    @PutMapping("/{id}")
    public Category update(@PathVariable UUID id, @RequestBody Category category) {
        return categoryService.update(id, category);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        categoryService.delete(id);
    }

}