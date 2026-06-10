package com.chuvannam.applogin.service;

import com.chuvannam.applogin.entity.ShippingZone;

import java.util.List;
import java.util.UUID;

public interface ShippingZoneService {

    List<ShippingZone> findAll();

    ShippingZone findById(UUID id);

    List<ShippingZone> findActive();

    List<ShippingZone> findFreeShipping();

    List<ShippingZone> searchByName(String keyword);

    ShippingZone save(ShippingZone shippingZone);

    ShippingZone update(UUID id, ShippingZone shippingZone);

    void delete(UUID id);
}