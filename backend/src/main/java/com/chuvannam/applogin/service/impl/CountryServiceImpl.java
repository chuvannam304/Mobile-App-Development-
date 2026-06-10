package com.chuvannam.applogin.service.impl;

import com.chuvannam.applogin.entity.Country;
import com.chuvannam.applogin.repository.CountryRepository;
import com.chuvannam.applogin.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    @Override
    public List<Country> findAll() {
        return countryRepository.findAll();
    }

    @Override
    public Country findById(Integer id) {
        return countryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Country not found: " + id));
    }

    @Override
    public Country findByIso(String iso) {
        return countryRepository.findByIso(iso)
                .orElseThrow(() ->
                        new RuntimeException("Country not found with ISO: " + iso));
    }

    @Override
    public Country findByName(String name) {
        return countryRepository.findByName(name)
                .orElseThrow(() ->
                        new RuntimeException("Country not found with name: " + name));
    }

    @Override
    public Country save(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public Country update(Integer id, Country country) {

        Country existing = findById(id);

        existing.setIso(country.getIso());
        existing.setName(country.getName());
        existing.setUpperName(country.getUpperName());
        existing.setIso3(country.getIso3());
        existing.setNumCode(country.getNumCode());
        existing.setPhoneCode(country.getPhoneCode());

        return countryRepository.save(existing);
    }

    @Override
    public void delete(Integer id) {
        countryRepository.deleteById(id);
    }
}