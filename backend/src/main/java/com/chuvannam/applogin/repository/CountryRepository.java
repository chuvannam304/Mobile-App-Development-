package com.chuvannam.applogin.repository;

import com.chuvannam.applogin.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository
        extends JpaRepository<Country, Integer> {

    Optional<Country> findByIso(String iso);

    Optional<Country> findByName(String name);

    boolean existsByIso(String iso);
}