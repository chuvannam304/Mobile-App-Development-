package com.chuvannam.applogin.service;

import com.chuvannam.applogin.entity.Country;

import java.util.List;

public interface CountryService {

    List<Country> findAll();

    Country findById(Integer id);

    Country findByIso(String iso);

    Country findByName(String name);

    Country save(Country country);

    Country update(Integer id, Country country);

    void delete(Integer id);
}