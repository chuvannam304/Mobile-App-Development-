package com.chuvannam.applogin.service;

import com.chuvannam.applogin.entity.CustomerAddress;

import java.util.List;
import java.util.UUID;

public interface CustomerAddressService {

    List<CustomerAddress> findAll();

    CustomerAddress findById(UUID id);

    List<CustomerAddress> findByCustomerId(UUID customerId);

    CustomerAddress save(CustomerAddress customerAddress);

    CustomerAddress update(UUID id, CustomerAddress customerAddress);

    void delete(UUID id);
}