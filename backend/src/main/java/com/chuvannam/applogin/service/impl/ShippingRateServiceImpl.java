package com.chuvannam.applogin.service.impl;

import com.chuvannam.applogin.entity.ShippingRate;
import com.chuvannam.applogin.repository.ShippingRateRepository;
import com.chuvannam.applogin.service.ShippingRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShippingRateServiceImpl implements ShippingRateService {

    private final ShippingRateRepository shippingRateRepository;

    @Override
    public List<ShippingRate> findAll() {
        return shippingRateRepository.findAll();
    }

    @Override
    public ShippingRate findById(UUID id) {
        return shippingRateRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("ShippingRate not found: " + id));
    }

    @Override
    public List<ShippingRate> findByShippingZoneId(UUID shippingZoneId) {
        return shippingRateRepository.findByShippingZoneId(shippingZoneId);
    }

    @Override
    public List<ShippingRate> findByWeightUnit(String weightUnit) {
        return shippingRateRepository.findByWeightUnit(weightUnit);
    }

    @Override
    public ShippingRate save(ShippingRate shippingRate) {
        return shippingRateRepository.save(shippingRate);
    }

    @Override
    public ShippingRate update(UUID id, ShippingRate shippingRate) {

        ShippingRate existing = findById(id);

        existing.setShippingZone(
                shippingRate.getShippingZone()
        );

        existing.setWeightUnit(
                shippingRate.getWeightUnit()
        );

        existing.setMinValue(
                shippingRate.getMinValue()
        );

        existing.setMaxValue(
                shippingRate.getMaxValue()
        );

        existing.setNoMax(
                shippingRate.getNoMax()
        );

        existing.setPrice(
                shippingRate.getPrice()
        );

        return shippingRateRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        shippingRateRepository.deleteById(id);
    }
}