package com.chuvannam.applogin.service;

import com.chuvannam.applogin.entity.AuthResponse;
import com.chuvannam.applogin.entity.GoogleAuthRequest;

public interface GoogleOAuthService {

    AuthResponse loginWithGoogle(
            GoogleAuthRequest request
    );

}