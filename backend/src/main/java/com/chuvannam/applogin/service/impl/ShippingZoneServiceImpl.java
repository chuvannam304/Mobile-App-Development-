package com.chuvannam.applogin.service.impl;

import com.chuvannam.applogin.entity.ShippingZone;
import com.chuvannam.applogin.repository.ShippingZoneRepository;
import com.chuvannam.applogin.service.ShippingZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShippingZoneServiceImpl implements ShippingZoneService {

    private final ShippingZoneRepository shippingZoneRepository;

    @Override
    public List<ShippingZone> findAll() {
        return shippingZoneRepository.findAll();
    }

    @Override
    public ShippingZone findById(UUID id) {
        return shippingZoneRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("ShippingZone not found: " + id));
    }

    @Override
    public List<ShippingZone> findActive() {
        return shippingZoneRepository.findByActiveTrue();
    }

    @Override
    public List<ShippingZone> findFreeShipping() {
        return shippingZoneRepository.findByFreeShippingTrue();
    }

    @Override
    public List<ShippingZone> searchByName(String keyword) {
        return shippingZoneRepository.findByNameContainingIgnoreCase(keyword);
    }

    @Override
    public ShippingZone save(ShippingZone shippingZone) {
        return shippingZoneRepository.save(shippingZone);
    }

    @Override
    public ShippingZone update(UUID id, ShippingZone shippingZone) {

        ShippingZone existing = findById(id);

        existing.setName(shippingZone.getName());
        existing.setDisplayName(shippingZone.getDisplayName());

        existing.setActive(shippingZone.getActive());
        existing.setFreeShipping(shippingZone.getFreeShipping());

        existing.setRateType(shippingZone.getRateType());

        existing.setCreatedBy(shippingZone.getCreatedBy());
        existing.setUpdatedBy(shippingZone.getUpdatedBy());

        return shippingZoneRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        shippingZoneRepository.deleteById(id);
    }
}