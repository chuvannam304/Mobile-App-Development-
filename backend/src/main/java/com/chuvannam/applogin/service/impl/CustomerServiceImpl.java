package com.chuvannam.applogin.service.impl;

import com.chuvannam.applogin.entity.Customer;
import com.chuvannam.applogin.repository.CustomerRepository;
import com.chuvannam.applogin.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findById(UUID id) {
        return customerRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found: " + id));
    }

    @Override
    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found with email: " + email));
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer update(UUID id, Customer customer) {

        Customer existing = findById(id);

        existing.setFirstName(customer.getFirstName());
        existing.setLastName(customer.getLastName());
        existing.setEmail(customer.getEmail());

        if (customer.getPasswordHash() != null
                && !customer.getPasswordHash().isBlank()) {
            existing.setPasswordHash(customer.getPasswordHash());
        }

        existing.setActive(customer.getActive());

        return customerRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        customerRepository.deleteById(id);
    }
}