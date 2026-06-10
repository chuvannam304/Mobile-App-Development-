package com.chuvannam.applogin.service;

import com.chuvannam.applogin.entity.Supplier;

import java.util.List;
import java.util.UUID;

public interface SupplierService {

    List<Supplier> findAll();

    Supplier findById(UUID id);

    List<Supplier> findByCountryId(Integer countryId);

    List<Supplier> searchByName(String keyword);

    Supplier save(Supplier supplier);

    Supplier update(UUID id, Supplier supplier);

    void delete(UUID id);
}