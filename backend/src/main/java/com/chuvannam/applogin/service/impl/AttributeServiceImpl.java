package com.chuvannam.applogin.service.impl;

import com.chuvannam.applogin.entity.Attribute;
import com.chuvannam.applogin.repository.AttributeRepository;
import com.chuvannam.applogin.service.AttributeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttributeServiceImpl implements AttributeService {

    private final AttributeRepository attributeRepository;

    @Override
    public List<Attribute> findAll() {
        return attributeRepository.findAll();
    }

    @Override
    public Attribute findById(UUID id) {
        return attributeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Attribute not found: " + id));
    }

    @Override
    public Attribute save(Attribute attribute) {
        return attributeRepository.save(attribute);
    }

    @Override
    public Attribute update(UUID id, Attribute attribute) {

        Attribute existing = findById(id);

        existing.setAttributeName(attribute.getAttributeName());

        if (attribute.getCreatedBy() != null) {
            existing.setCreatedBy(attribute.getCreatedBy());
        }

        if (attribute.getUpdatedBy() != null) {
            existing.setUpdatedBy(attribute.getUpdatedBy());
        }

        return attributeRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        attributeRepository.deleteById(id);
    }
}