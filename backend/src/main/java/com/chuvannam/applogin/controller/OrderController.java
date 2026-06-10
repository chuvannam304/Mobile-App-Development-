package com.chuvannam.applogin.controller;

import com.chuvannam.applogin.entity.Order;
import com.chuvannam.applogin.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin("*")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getAll() {
        return ResponseEntity.ok(
                orderService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(
            @PathVariable String id
    ) {
        return ResponseEntity.ok(
                orderService.findById(id)
        );
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Order>> getByCustomerId(
            @PathVariable UUID customerId
    ) {
        return ResponseEntity.ok(
                orderService.findByCustomerId(customerId)
        );
    }

    @GetMapping("/status/{orderStatusId}")
    public ResponseEntity<List<Order>> getByOrderStatusId(
            @PathVariable UUID orderStatusId
    ) {
        return ResponseEntity.ok(
                orderService.findByOrderStatusId(orderStatusId)
        );
    }

    @GetMapping("/coupon/{couponId}")
    public ResponseEntity<List<Order>> getByCouponId(
            @PathVariable UUID couponId
    ) {
        return ResponseEntity.ok(
                orderService.findByCouponId(couponId)
        );
    }

    @PostMapping
    public ResponseEntity<Order> create(
            @RequestBody Order order
    ) {
        return ResponseEntity.ok(
                orderService.save(order)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> update(
            @PathVariable String id,
            @RequestBody Order order
    ) {
        return ResponseEntity.ok(
                orderService.update(id, order)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable String id
    ) {
        orderService.delete(id);

        return ResponseEntity.ok(
                "Order deleted successfully"
        );
    }
}