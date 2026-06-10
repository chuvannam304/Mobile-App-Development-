package com.chuvannam.applogin.repository;

import com.chuvannam.applogin.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository
        extends JpaRepository<Notification, UUID> {

    List<Notification> findByAccountId(UUID accountId);

    List<Notification> findBySeenFalse();

    List<Notification> findBySeenTrue();
}