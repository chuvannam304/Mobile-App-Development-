package com.chuvannam.applogin.service;

import com.chuvannam.applogin.entity.AuthResponse;
import com.chuvannam.applogin.entity.LoginRequest;
import com.chuvannam.applogin.entity.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

}