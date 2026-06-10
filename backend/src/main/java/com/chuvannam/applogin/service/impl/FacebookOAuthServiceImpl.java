package com.chuvannam.applogin.service.impl;

import com.chuvannam.applogin.entity.AuthResponse;
import com.chuvannam.applogin.entity.FacebookAuthRequest;
import com.chuvannam.applogin.entity.Role;
import com.chuvannam.applogin.entity.StaffAccount;
import com.chuvannam.applogin.repository.RoleRepository;
import com.chuvannam.applogin.repository.StaffAccountRepository;
import com.chuvannam.applogin.service.FacebookOAuthService;
import com.chuvannam.applogin.service.JwtService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FacebookOAuthServiceImpl implements FacebookOAuthService {

    private final StaffAccountRepository staffAccountRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;

    @Override
    public AuthResponse loginWithFacebook(FacebookAuthRequest request) {
        Map<String, Object> data = getFacebookUser(request.getAccessToken());

        String email = data.get("email").toString();
        String name = data.get("name") != null
                ? data.get("name").toString()
                : email;

        StaffAccount staff = staffAccountRepository.findByEmail(email).orElse(null);

        if (staff == null) {
            staff = createFacebookStaff(email, name);
        }

        String token = jwtService.generateToken(staff.getEmail());

        return new AuthResponse(
                token,
                staff.getFirstName() + " " + staff.getLastName(),
                staff.getEmail(),
                "Facebook login success"
        );
    }

    @Override
    public AuthResponse registerWithFacebook(FacebookAuthRequest request) {
        Map<String, Object> data = getFacebookUser(request.getAccessToken());

        String email = data.get("email").toString();
        String name = data.get("name") != null
                ? data.get("name").toString()
                : email;

        StaffAccount staff = staffAccountRepository.findByEmail(email).orElse(null);

        if (staff == null) {
            staff = createFacebookStaff(email, name);
        }

        String token = jwtService.generateToken(staff.getEmail());

        return new AuthResponse(
                token,
                staff.getFirstName() + " " + staff.getLastName(),
                staff.getEmail(),
                "Facebook register success"
        );
    }

    private StaffAccount createFacebookStaff(String email, String fullName) {
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
        staff.setProvider("FACEBOOK");
        staff.setFirstName(firstName.isEmpty() ? "Facebook" : firstName);
        staff.setLastName(lastName.isEmpty() ? "User" : lastName);
        staff.setEmail(email);
        staff.setPasswordHash("");
        staff.setActive(true);
        staff.setCreatedAt(OffsetDateTime.now());
        staff.setUpdatedAt(OffsetDateTime.now());

        return staffAccountRepository.save(staff);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getFacebookUser(String accessToken) {
        String url =
                "https://graph.facebook.com/me?fields=id,name,email&access_token="
                        + accessToken;

        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> data =
                (Map<String, Object>) restTemplate.getForObject(url, Map.class);

        if (data == null || data.get("email") == null) {
            throw new RuntimeException("Không lấy được email Facebook");
        }

        return data;
    }
}