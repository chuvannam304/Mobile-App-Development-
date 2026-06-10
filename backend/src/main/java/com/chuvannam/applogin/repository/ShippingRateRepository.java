package com.chuvannam.applogin.repository;

import com.chuvannam.applogin.entity.ShippingRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ShippingRateRepository
        extends JpaRepository<ShippingRate, UUID> {

    List<ShippingRate> findByShippingZoneId(UUID shippingZoneId);

    List<ShippingRate> findByWeightUnit(String weightUnit);
}