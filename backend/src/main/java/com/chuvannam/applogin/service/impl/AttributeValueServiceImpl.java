package com.chuvannam.applogin.service.impl;

import com.chuvannam.applogin.entity.AttributeValue;
import com.chuvannam.applogin.repository.AttributeValueRepository;
import com.chuvannam.applogin.service.AttributeValueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttributeValueServiceImpl implements AttributeValueService {

    private final AttributeValueRepository attributeValueRepository;

    @Override
    public List<AttributeValue> findAll() {
        return attributeValueRepository.findAll();
    }

    @Override
    public AttributeValue findById(UUID id) {
        return attributeValueRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("AttributeValue not found: " + id));
    }

    @Override
    public List<AttributeValue> findByAttributeId(UUID attributeId) {
        return attributeValueRepository.findByAttributeId(attributeId);
    }

    @Override
    public AttributeValue save(AttributeValue attributeValue) {
        return attributeValueRepository.save(attributeValue);
    }

    @Override
    public AttributeValue update(UUID id, AttributeValue attributeValue) {

        AttributeValue existing = findById(id);

        existing.setAttribute(attributeValue.getAttribute());
        existing.setAttributeValue(attributeValue.getAttributeValue());
        existing.setColor(attributeValue.getColor());

        return attributeValueRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        attributeValueRepository.deleteById(id);
    }
}