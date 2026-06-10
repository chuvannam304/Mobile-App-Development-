package com.chuvannam.applogin.service.impl;

import com.chuvannam.applogin.entity.AuthResponse;
import com.chuvannam.applogin.entity.LoginRequest;
import com.chuvannam.applogin.entity.RegisterRequest;
import com.chuvannam.applogin.entity.Role;
import com.chuvannam.applogin.entity.StaffAccount;
import com.chuvannam.applogin.repository.RoleRepository;
import com.chuvannam.applogin.repository.StaffAccountRepository;
import com.chuvannam.applogin.service.AuthService;
import com.chuvannam.applogin.service.JwtService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final StaffAccountRepository staffAccountRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterRequest request) {

        if (staffAccountRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Role role = roleRepository.findByRoleName("USER")
                .orElseThrow(() -> new RuntimeException("Role USER not found"));

        String fullName = request.getName() != null
                ? request.getName().trim()
                : "";

        String firstName = fullName;
        String lastName = "User";

        if (fullName.contains(" ")) {
            int lastSpaceIndex = fullName.lastIndexOf(" ");
            firstName = fullName.substring(0, lastSpaceIndex);
            lastName = fullName.substring(lastSpaceIndex + 1);
        }

        StaffAccount staff = new StaffAccount();
        staff.setId(UUID.randomUUID());
        staff.setRole(role);
        staff.setProvider("LOCAL");
        staff.setFirstName(firstName.isEmpty() ? "User" : firstName);
        staff.setLastName(lastName.isEmpty() ? "User" : lastName);
        staff.setEmail(request.getEmail());
        staff.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        staff.setActive(true);
        staff.setCreatedAt(OffsetDateTime.now());
        staff.setUpdatedAt(OffsetDateTime.now());

        staffAccountRepository.save(staff);

        String token = jwtService.generateToken(staff.getEmail());

        return new AuthResponse(
                token,
                staff.getFirstName() + " " + staff.getLastName(),
                staff.getEmail(),
                "Register success"
        );
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        StaffAccount staff = staffAccountRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Staff account not found"));

        String token = jwtService.generateToken(staff.getEmail());

        return new AuthResponse(
                token,
                staff.getFirstName() + " " + staff.getLastName(),
                staff.getEmail(),
                "Login success"
        );
    }
}