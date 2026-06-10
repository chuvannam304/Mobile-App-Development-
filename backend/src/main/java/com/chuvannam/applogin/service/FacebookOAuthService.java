package com.chuvannam.applogin.service;

import com.chuvannam.applogin.entity.AuthResponse;
import com.chuvannam.applogin.entity.FacebookAuthRequest;

public interface FacebookOAuthService {

    AuthResponse loginWithFacebook(
            FacebookAuthRequest request
    );

    AuthResponse registerWithFacebook(
            FacebookAuthRequest request
    );
}