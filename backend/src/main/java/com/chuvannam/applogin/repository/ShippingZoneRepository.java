package com.chuvannam.applogin.repository;

import com.chuvannam.applogin.entity.ShippingZone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ShippingZoneRepository
        extends JpaRepository<ShippingZone, UUID> {

    List<ShippingZone> findByActiveTrue();

    List<ShippingZone> findByFreeShippingTrue();

    List<ShippingZone> findByNameContainingIgnoreCase(String keyword);
}