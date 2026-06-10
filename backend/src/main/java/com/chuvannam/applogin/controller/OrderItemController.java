package com.chuvannam.applogin.controller;

import com.chuvannam.applogin.entity.OrderItem;
import com.chuvannam.applogin.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/order-items")
@RequiredArgsConstructor
@CrossOrigin("*")
public class OrderItemController {

    private final OrderItemService orderItemService;

    @GetMapping
    public ResponseEntity<List<OrderItem>> getAll() {
        return ResponseEntity.ok(
                orderItemService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> getById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                orderItemService.findById(id)
        );
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderItem>> getByOrderId(
            @PathVariable String orderId
    ) {
        return ResponseEntity.ok(
                orderItemService.findByOrderId(orderId)
        );
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<OrderItem>> getByProductId(
            @PathVariable UUID productId
    ) {
        return ResponseEntity.ok(
                orderItemService.findByProductId(productId)
        );
    }

    @PostMapping
    public ResponseEntity<OrderItem> create(
            @RequestBody OrderItem orderItem
    ) {
        return ResponseEntity.ok(
                orderItemService.save(orderItem)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItem> update(
            @PathVariable UUID id,
            @RequestBody OrderItem orderItem
    ) {
        return ResponseEntity.ok(
                orderItemService.update(id, orderItem)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable UUID id
    ) {
        orderItemService.delete(id);

        return ResponseEntity.ok(
                "OrderItem deleted successfully"
        );
    }
}