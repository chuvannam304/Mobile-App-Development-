package com.chuvannam.applogin.service.impl;

import com.chuvannam.applogin.entity.Supplier;
import com.chuvannam.applogin.repository.SupplierRepository;
import com.chuvannam.applogin.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Override
    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    @Override
    public Supplier findById(UUID id) {
        return supplierRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Supplier not found: " + id));
    }

    @Override
    public List<Supplier> findByCountryId(Integer countryId) {
        return supplierRepository.findByCountryId(countryId);
    }

    @Override
    public List<Supplier> searchByName(String keyword) {
        return supplierRepository.findBySupplierNameContainingIgnoreCase(keyword);
    }

    @Override
    public Supplier save(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    @Override
    public Supplier update(UUID id, Supplier supplier) {

        Supplier existing = findById(id);

        existing.setSupplierName(supplier.getSupplierName());
        existing.setCompany(supplier.getCompany());
        existing.setPhoneNumber(supplier.getPhoneNumber());

        existing.setAddressLine1(supplier.getAddressLine1());
        existing.setAddressLine2(supplier.getAddressLine2());

        existing.setCountry(supplier.getCountry());
        existing.setCity(supplier.getCity());

        existing.setNote(supplier.getNote());

        existing.setCreatedBy(supplier.getCreatedBy());
        existing.setUpdatedBy(supplier.getUpdatedBy());

        return supplierRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        supplierRepository.deleteById(id);
    }
}