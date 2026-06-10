package com.chuvannam.applogin.service.impl;

import com.chuvannam.applogin.entity.Tag;
import com.chuvannam.applogin.repository.TagRepository;
import com.chuvannam.applogin.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public Tag findById(UUID id) {
        return tagRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Tag not found: " + id));
    }

    @Override
    public Tag findByTagName(String tagName) {
        return tagRepository.findByTagName(tagName)
                .orElseThrow(() ->
                        new RuntimeException("Tag not found: " + tagName));
    }

    @Override
    public List<Tag> searchByName(String keyword) {
        return tagRepository.findByTagNameContainingIgnoreCase(keyword);
    }

    @Override
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag update(UUID id, Tag tag) {

        Tag existing = findById(id);

        existing.setTagName(tag.getTagName());
        existing.setIcon(tag.getIcon());

        existing.setCreatedBy(tag.getCreatedBy());
        existing.setUpdatedBy(tag.getUpdatedBy());

        return tagRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        tagRepository.deleteById(id);
    }
}