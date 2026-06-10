package com.chuvannam.applogin.service;

import com.chuvannam.applogin.entity.StaffAccount;
import com.chuvannam.applogin.repository.StaffAccountRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService
        implements UserDetailsService {

    private final StaffAccountRepository staffAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        StaffAccount staff = staffAccountRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Staff account not found")
                );

        return new org.springframework.security.core.userdetails.User(
                staff.getEmail(),
                staff.getPasswordHash(),
                Collections.emptyList()
        );
    }
}