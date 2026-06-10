package com.chuvannam.applogin.repository;

import com.chuvannam.applogin.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderStatusRepository
        extends JpaRepository<OrderStatus, UUID> {

    Optional<OrderStatus> findByStatusName(String statusName);

    List<OrderStatus> findByPrivacy(String privacy);
}