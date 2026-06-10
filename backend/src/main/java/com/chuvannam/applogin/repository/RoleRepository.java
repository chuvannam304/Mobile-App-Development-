package com.chuvannam.applogin.repository;

import com.chuvannam.applogin.entity.Role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository
        extends JpaRepository<Role, UUID> {

    Optional<Role> findByRoleName(String roleName);

}