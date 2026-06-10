package com.chuvannam.applogin.service.impl;

import com.chuvannam.applogin.service.JwtService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtUtils jwtUtils;

    @Override
    public String generateToken(String email) {

        return jwtUtils.generateToken(email);
    }
}