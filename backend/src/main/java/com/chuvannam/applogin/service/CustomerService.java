package com.chuvannam.applogin.service;

import com.chuvannam.applogin.entity.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    List<Customer> findAll();

    Customer findById(UUID id);

    Customer findByEmail(String email);

    Customer save(Customer customer);

    Customer update(UUID id, Customer customer);

    void delete(UUID id);
}