package com.chuvannam.applogin.service.impl;

import com.chuvannam.applogin.entity.Category;
import com.chuvannam.applogin.repository.CategoryRepository;
import com.chuvannam.applogin.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Override
    public Category save(Category category) {
        if (category.getId() == null) category.setId(UUID.randomUUID());
        if (category.getActive() == null) category.setActive(true);
        if (category.getCreatedAt() == null) category.setCreatedAt(OffsetDateTime.now());
        category.setUpdatedAt(OffsetDateTime.now());
        return categoryRepository.save(category);
    }

    @Override
    public Category update(UUID id, Category category) {
        Category old = findById(id);
        old.setParentId(category.getParentId());
        old.setCategoryName(category.getCategoryName());
        old.setCategoryDescription(category.getCategoryDescription());
        old.setIcon(category.getIcon());
        old.setImage(category.getImage());
        old.setPlaceholder(category.getPlaceholder());
        old.setActive(category.getActive());
        old.setUpdatedAt(OffsetDateTime.now());
        return categoryRepository.save(old);
    }

    @Override
    public void delete(UUID id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> findParentCategories() {
        return categoryRepository.findByParentIdIsNullAndActiveTrueOrderByCategoryNameAsc();
    }

    @Override
    public List<Category> findChildrenByParentId(UUID parentId) {
        return categoryRepository.findByParentIdAndActiveTrueOrderByCategoryNameAsc(parentId);
    }
}
