package com.chuvannam.applogin.service.impl;

import com.chuvannam.applogin.entity.Notification;
import com.chuvannam.applogin.repository.NotificationRepository;
import com.chuvannam.applogin.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification findById(UUID id) {
        return notificationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Notification not found: " + id));
    }

    @Override
    public List<Notification> findByAccountId(UUID accountId) {
        return notificationRepository.findByAccountId(accountId);
    }

    @Override
    public List<Notification> findUnseen() {
        return notificationRepository.findBySeenFalse();
    }

    @Override
    public List<Notification> findSeen() {
        return notificationRepository.findBySeenTrue();
    }

    @Override
    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public Notification update(UUID id, Notification notification) {

        Notification existing = findById(id);

        existing.setAccount(notification.getAccount());
        existing.setTitle(notification.getTitle());
        existing.setContent(notification.getContent());
        existing.setSeen(notification.getSeen());
        existing.setReceiveTime(notification.getReceiveTime());
        existing.setNotificationExpiryDate(
                notification.getNotificationExpiryDate()
        );

        return notificationRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        notificationRepository.deleteById(id);
    }
}