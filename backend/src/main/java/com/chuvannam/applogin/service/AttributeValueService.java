package com.chuvannam.applogin.service;

import com.chuvannam.applogin.entity.AttributeValue;

import java.util.List;
import java.util.UUID;

public interface AttributeValueService {

    List<AttributeValue> findAll();

    AttributeValue findById(UUID id);

    List<AttributeValue> findByAttributeId(UUID attributeId);

    AttributeValue save(AttributeValue attributeValue);

    AttributeValue update(UUID id, AttributeValue attributeValue);

    void delete(UUID id);
}