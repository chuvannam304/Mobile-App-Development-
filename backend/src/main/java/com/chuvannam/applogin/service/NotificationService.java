package com.chuvannam.applogin.service;

import com.chuvannam.applogin.entity.Notification;

import java.util.List;
import java.util.UUID;

public interface NotificationService {

    List<Notification> findAll();

    Notification findById(UUID id);

    List<Notification> findByAccountId(UUID accountId);

    List<Notification> findUnseen();

    List<Notification> findSeen();

    Notification save(Notification notification);

    Notification update(UUID id, Notification notification);

    void delete(UUID id);
}