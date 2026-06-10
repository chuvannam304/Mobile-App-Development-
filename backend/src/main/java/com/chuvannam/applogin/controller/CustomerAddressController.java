package com.chuvannam.applogin.controller;

import com.chuvannam.applogin.entity.CustomerAddress;
import com.chuvannam.applogin.service.CustomerAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customer-addresses")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CustomerAddressController {

    private final CustomerAddressService customerAddressService;

    @GetMapping
    public ResponseEntity<List<CustomerAddress>> getAll() {
        return ResponseEntity.ok(
                customerAddressService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerAddress> getById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                customerAddressService.findById(id)
        );
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<CustomerAddress>> getByCustomerId(
            @PathVariable UUID customerId
    ) {
        return ResponseEntity.ok(
                customerAddressService.findByCustomerId(customerId)
        );
    }

    @PostMapping
    public ResponseEntity<CustomerAddress> create(
            @RequestBody CustomerAddress customerAddress
    ) {
        return ResponseEntity.ok(
                customerAddressService.save(customerAddress)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerAddress> update(
            @PathVariable UUID id,
            @RequestBody CustomerAddress customerAddress
    ) {
        return ResponseEntity.ok(
                customerAddressService.update(id, customerAddress)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable UUID id
    ) {
        customerAddressService.delete(id);

        return ResponseEntity.ok(
                "CustomerAddress deleted successfully"
        );
    }
}