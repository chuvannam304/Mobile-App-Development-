package com.chuvannam.applogin.service;

import com.chuvannam.applogin.entity.Attribute;

import java.util.List;
import java.util.UUID;

public interface AttributeService {

    List<Attribute> findAll();

    Attribute findById(UUID id);

    Attribute save(Attribute attribute);

    Attribute update(UUID id, Attribute attribute);

    void delete(UUID id);
}