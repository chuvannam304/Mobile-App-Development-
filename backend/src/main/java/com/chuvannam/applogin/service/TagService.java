package com.chuvannam.applogin.service;

import com.chuvannam.applogin.entity.Tag;

import java.util.List;
import java.util.UUID;

public interface TagService {

    List<Tag> findAll();

    Tag findById(UUID id);

    Tag findByTagName(String tagName);

    List<Tag> searchByName(String keyword);

    Tag save(Tag tag);

    Tag update(UUID id, Tag tag);

    void delete(UUID id);
}