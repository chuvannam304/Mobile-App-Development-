package com.chuvannam.applogin.service.impl;

import com.chuvannam.applogin.entity.CustomerAddress;
import com.chuvannam.applogin.repository.CustomerAddressRepository;
import com.chuvannam.applogin.service.CustomerAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerAddressServiceImpl implements CustomerAddressService {

    private final CustomerAddressRepository customerAddressRepository;

    @Override
    public List<CustomerAddress> findAll() {
        return customerAddressRepository.findAll();
    }

    @Override
    public CustomerAddress findById(UUID id) {
        return customerAddressRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("CustomerAddress not found: " + id));
    }

    @Override
    public List<CustomerAddress> findByCustomerId(UUID customerId) {
        return customerAddressRepository.findByCustomerId(customerId);
    }

    @Override
    public CustomerAddress save(CustomerAddress customerAddress) {
        return customerAddressRepository.save(customerAddress);
    }

    @Override
    public CustomerAddress update(UUID id, CustomerAddress customerAddress) {

        CustomerAddress existing = findById(id);

        existing.setCustomer(customerAddress.getCustomer());
        existing.setAddressLine1(customerAddress.getAddressLine1());
        existing.setAddressLine2(customerAddress.getAddressLine2());
        existing.setPhoneNumber(customerAddress.getPhoneNumber());
        existing.setDialCode(customerAddress.getDialCode());
        existing.setCountry(customerAddress.getCountry());
        existing.setPostalCode(customerAddress.getPostalCode());
        existing.setCity(customerAddress.getCity());

        return customerAddressRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        customerAddressRepository.deleteById(id);
    }
}