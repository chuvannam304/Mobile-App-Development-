package com.chuvannam.applogin.controller;

import com.chuvannam.applogin.entity.Customer;
import com.chuvannam.applogin.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> getAll() {
        return ResponseEntity.ok(
                customerService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                customerService.findById(id)
        );
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Customer> getByEmail(
            @PathVariable String email
    ) {
        return ResponseEntity.ok(
                customerService.findByEmail(email)
        );
    }

    @PostMapping
    public ResponseEntity<Customer> create(
            @RequestBody Customer customer
    ) {
        return ResponseEntity.ok(
                customerService.save(customer)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(
            @PathVariable UUID id,
            @RequestBody Customer customer
    ) {
        return ResponseEntity.ok(
                customerService.update(id, customer)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable UUID id
    ) {
        customerService.delete(id);

        return ResponseEntity.ok(
                "Customer deleted successfully"
        );
    }
}