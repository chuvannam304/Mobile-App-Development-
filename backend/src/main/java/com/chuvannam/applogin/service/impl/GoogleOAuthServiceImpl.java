package com.chuvannam.applogin.service.impl;

import com.chuvannam.applogin.entity.AuthResponse;
import com.chuvannam.applogin.entity.GoogleAuthRequest;
import com.chuvannam.applogin.entity.Role;
import com.chuvannam.applogin.entity.StaffAccount;
import com.chuvannam.applogin.repository.RoleRepository;
import com.chuvannam.applogin.repository.StaffAccountRepository;
import com.chuvannam.applogin.service.GoogleOAuthService;
import com.chuvannam.applogin.service.JwtService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GoogleOAuthServiceImpl implements GoogleOAuthService {

    private final StaffAccountRepository staffAccountRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;

    @Override
    public AuthResponse loginWithGoogle(GoogleAuthRequest request) {
        try {
            if (request == null || request.getIdToken() == null) {
                throw new RuntimeException("idToken is null");
            }

            String token = request.getIdToken()
                    .replaceAll("\\s+", "")
                    .trim();

            FirebaseToken decodedToken = FirebaseAuth.getInstance()
                    .verifyIdToken(token);

            String email = decodedToken.getEmail();
            String name = decodedToken.getName();

            if (email == null || email.isEmpty()) {
                throw new RuntimeException("Email missing from Firebase token");
            }

            StaffAccount staff = staffAccountRepository.findByEmail(email).orElse(null);

            if (staff == null) {
                staff = createGoogleStaff(email, name);
            }

            String jwt = jwtService.generateToken(staff.getEmail());

            return new AuthResponse(
                    jwt,
                    staff.getFirstName() + " " + staff.getLastName(),
                    staff.getEmail(),
                    "Google login success"
            );

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private StaffAccount createGoogleStaff(String email, String fullName) {
        Role role = roleRepository.findByRoleName("USER")
                .orElseThrow(() -> new RuntimeException("Role USER not found"));

        String firstName = fullName != null ? fullName.trim() : "";
        String lastName = "User";

        if (firstName.contains(" ")) {
            int lastSpaceIndex = firstName.lastIndexOf(" ");
            lastName = firstName.substring(lastSpaceIndex + 1);
            firstName = firstName.substring(0, lastSpaceIndex);
        }

        StaffAccount staff = new StaffAccount();
        staff.setId(UUID.randomUUID());
        staff.setRole(role);
        staff.setProvider("GOOGLE");
        staff.setFirstName(firstName.isEmpty() ? "Google" : firstName);
        staff.setLastName(lastName.isEmpty() ? "User" : lastName);
        staff.setEmail(email);
        staff.setPasswordHash("");
        staff.setActive(true);
        staff.setCreatedAt(OffsetDateTime.now());
        staff.setUpdatedAt(OffsetDateTime.now());

        return staffAccountRepository.save(staff);
    }
}