package com.chuvannam.applogin.repository;

import com.chuvannam.applogin.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    List<Category> findByParentIdIsNullAndActiveTrueOrderByCategoryNameAsc();

    List<Category> findByParentIdAndActiveTrueOrderByCategoryNameAsc(UUID parentId);
}