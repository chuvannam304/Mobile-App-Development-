package com.chuvannam.applogin.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void init() throws Exception {

        try (InputStream serviceAccount =
                     getClass().getClassLoader()
                             .getResourceAsStream("firebase-service-account.json")) {

            if (serviceAccount == null) {
                throw new RuntimeException("firebase-service-account.json NOT FOUND");
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setProjectId("applogin-6b688")
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }

            System.out.println("🔥 Firebase INIT OK");
            System.out.println("🔥 APP COUNT: " + FirebaseApp.getApps().size());
        }
    }
}