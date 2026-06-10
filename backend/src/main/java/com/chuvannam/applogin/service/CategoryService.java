package com.chuvannam.applogin.service;

import com.chuvannam.applogin.entity.Category;
import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<Category> findAll();
    Category findById(UUID id);
    Category save(Category category);
    Category update(UUID id, Category category);
    void delete(UUID id);
    List<Category> findParentCategories();
    List<Category> findChildrenByParentId(UUID parentId);
}
