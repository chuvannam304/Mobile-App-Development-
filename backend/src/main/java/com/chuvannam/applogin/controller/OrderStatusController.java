package com.chuvannam.applogin.controller;

import com.chuvannam.applogin.entity.OrderStatus;
import com.chuvannam.applogin.service.OrderStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/order-statuses")
@RequiredArgsConstructor
@CrossOrigin("*")
public class OrderStatusController {

    private final OrderStatusService orderStatusService;

    @GetMapping
    public ResponseEntity<List<OrderStatus>> getAll() {
        return ResponseEntity.ok(
                orderStatusService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderStatus> getById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                orderStatusService.findById(id)
        );
    }

    @GetMapping("/name/{statusName}")
    public ResponseEntity<OrderStatus> getByStatusName(
            @PathVariable String statusName
    ) {
        return ResponseEntity.ok(
                orderStatusService.findByStatusName(statusName)
        );
    }

    @GetMapping("/privacy/{privacy}")
    public ResponseEntity<List<OrderStatus>> getByPrivacy(
            @PathVariable String privacy
    ) {
        return ResponseEntity.ok(
                orderStatusService.findByPrivacy(privacy)
        );
    }

    @PostMapping
    public ResponseEntity<OrderStatus> create(
            @RequestBody OrderStatus orderStatus
    ) {
        return ResponseEntity.ok(
                orderStatusService.save(orderStatus)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderStatus> update(
            @PathVariable UUID id,
            @RequestBody OrderStatus orderStatus
    ) {
        return ResponseEntity.ok(
                orderStatusService.update(id, orderStatus)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable UUID id
    ) {
        orderStatusService.delete(id);

        return ResponseEntity.ok(
                "OrderStatus deleted successfully"
        );
    }
}