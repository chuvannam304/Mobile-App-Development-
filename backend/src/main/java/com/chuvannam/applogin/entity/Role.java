package com.chuvannam.applogin.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "role_name", nullable = false)
    private String roleName;

    @Column(name = "privileges")
    private String privileges;
}