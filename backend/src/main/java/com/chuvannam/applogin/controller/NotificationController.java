package com.chuvannam.applogin.controller;

import com.chuvannam.applogin.entity.Notification;
import com.chuvannam.applogin.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@CrossOrigin("*")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<Notification>> getAll() {
        return ResponseEntity.ok(
                notificationService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                notificationService.findById(id)
        );
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<Notification>> getByAccountId(
            @PathVariable UUID accountId
    ) {
        return ResponseEntity.ok(
                notificationService.findByAccountId(accountId)
        );
    }

    @GetMapping("/unseen")
    public ResponseEntity<List<Notification>> getUnseen() {
        return ResponseEntity.ok(
                notificationService.findUnseen()
        );
    }

    @GetMapping("/seen")
    public ResponseEntity<List<Notification>> getSeen() {
        return ResponseEntity.ok(
                notificationService.findSeen()
        );
    }

    @PostMapping
    public ResponseEntity<Notification> create(
            @RequestBody Notification notification
    ) {
        return ResponseEntity.ok(
                notificationService.save(notification)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notification> update(
            @PathVariable UUID id,
            @RequestBody Notification notification
    ) {
        return ResponseEntity.ok(
                notificationService.update(id, notification)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable UUID id
    ) {
        notificationService.delete(id);

        return ResponseEntity.ok(
                "Notification deleted successfully"
        );
    }
}