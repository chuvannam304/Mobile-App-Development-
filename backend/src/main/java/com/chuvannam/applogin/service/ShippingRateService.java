package com.chuvannam.applogin.service;

import com.chuvannam.applogin.entity.ShippingRate;

import java.util.List;
import java.util.UUID;

public interface ShippingRateService {

    List<ShippingRate> findAll();

    ShippingRate findById(UUID id);

    List<ShippingRate> findByShippingZoneId(UUID shippingZoneId);

    List<ShippingRate> findByWeightUnit(String weightUnit);

    ShippingRate save(ShippingRate shippingRate);

    ShippingRate update(UUID id, ShippingRate shippingRate);

    void delete(UUID id);
}