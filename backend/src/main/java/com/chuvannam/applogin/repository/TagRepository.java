package com.chuvannam.applogin.repository;

import com.chuvannam.applogin.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TagRepository
        extends JpaRepository<Tag, UUID> {

    Optional<Tag> findByTagName(String tagName);

    boolean existsByTagName(String tagName);

    List<Tag> findByTagNameContainingIgnoreCase(String keyword);
}