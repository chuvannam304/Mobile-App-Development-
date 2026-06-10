package com.chuvannam.applogin.controller;

import com.chuvannam.applogin.entity.AuthResponse;
import com.chuvannam.applogin.entity.FacebookAuthRequest;
import com.chuvannam.applogin.entity.LoginRequest;
import com.chuvannam.applogin.entity.RegisterRequest;
import com.chuvannam.applogin.entity.Role;
import com.chuvannam.applogin.entity.StaffAccount;
import com.chuvannam.applogin.repository.RoleRepository;
import com.chuvannam.applogin.repository.StaffAccountRepository;
import com.chuvannam.applogin.service.AuthService;
import com.chuvannam.applogin.service.FacebookOAuthService;
import com.chuvannam.applogin.service.JwtService;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;
    private final FacebookOAuthService facebookOAuthService;
    private final StaffAccountRepository staffAccountRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;

    // =========================
    // REGISTER EMAIL
    // =========================
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    // =========================
    // LOGIN EMAIL
    // =========================
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    // =========================
    // TEST
    // =========================
    @GetMapping("/ping")
    public String ping() {
        return "JAVA OK";
    }

    @GetMapping("/db-test")
    public String dbTest() {
        return staffAccountRepository.count() + " staff_accounts in DB";
    }

    // =========================
    // GOOGLE LOGIN
    // =========================
    @PostMapping("/google")
    public ResponseEntity<?> googleLogin(@RequestBody Map<String, String> body) {
        try {
            String idToken = body.get("idToken");

            FirebaseToken decodedToken =
                    FirebaseAuth.getInstance().verifyIdToken(idToken);

            String email = decodedToken.getEmail();

            StaffAccount staff = staffAccountRepository
                    .findByEmail(email)
                    .orElse(null);

            if (staff == null) {
                return ResponseEntity.status(401)
                        .body(Map.of("message", "Tài khoản chưa đăng ký"));
            }

            String jwt = jwtService.generateToken(staff.getEmail());

            return ResponseEntity.ok(Map.of(
                    "token", jwt,
                    "email", staff.getEmail(),
                    "name", getFullName(staff)
            ));

        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    // =========================
    // GOOGLE REGISTER
    // =========================
    @PostMapping("/google/register")
    public ResponseEntity<?> googleRegister(@RequestBody Map<String, String> body) {
        try {
            String idToken = body.get("idToken");

            FirebaseToken decodedToken =
                    FirebaseAuth.getInstance().verifyIdToken(idToken);

            String email = decodedToken.getEmail();
            String name = decodedToken.getName();

            StaffAccount staff = staffAccountRepository
                    .findByEmail(email)
                    .orElse(null);

            if (staff == null) {
                staff = createSocialStaff(email, name);
            }

            String jwt = jwtService.generateToken(staff.getEmail());

            return ResponseEntity.ok(Map.of(
                    "token", jwt,
                    "email", staff.getEmail(),
                    "name", getFullName(staff)
            ));

        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    // =========================
    // FACEBOOK LOGIN
    // =========================
    @PostMapping("/facebook")
    public ResponseEntity<AuthResponse> facebookLogin(
            @RequestBody FacebookAuthRequest request) {

        return ResponseEntity.ok(
                facebookOAuthService.loginWithFacebook(request));
    }

    // =========================
    // FACEBOOK REGISTER
    // =========================
    @PostMapping("/facebook/register")
    public ResponseEntity<?> facebookRegister(
            @RequestBody FacebookAuthRequest request) {
        try {
            AuthResponse response =
                    facebookOAuthService.registerWithFacebook(request);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(400)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    // =========================
    // HELPER
    // =========================
    private StaffAccount createSocialStaff(String email, String fullName) {
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
        staff.setFirstName(firstName.isEmpty() ? "Google" : firstName);
        staff.setLastName(lastName.isEmpty() ? "User" : lastName);
        staff.setEmail(email);
        staff.setPasswordHash("");
        staff.setActive(true);
        staff.setCreatedAt(OffsetDateTime.now());
        staff.setUpdatedAt(OffsetDateTime.now());

        return staffAccountRepository.save(staff);
    }

    private String getFullName(StaffAccount staff) {
        return staff.getFirstName() + " " + staff.getLastName();
    }
}