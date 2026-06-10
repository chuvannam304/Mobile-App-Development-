package com.chuvannam.applogin.entity;

import lombok.Data;

@Data
public class RegisterRequest {

    private String name;

    private String email;

    private String password;

}