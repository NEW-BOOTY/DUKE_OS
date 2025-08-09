/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package com.java1kind;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.web.multipart.*;
import org.springframework.util.StreamUtils;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.*;
import javax.validation.constraints.*;
import java.io.*;
import java.nio.file.*;
import java.security.Key;
import java.time.*;
import java.util.*;
import java.util.concurrent.*;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import io.jsonwebtoken.*;

@SpringBootApplication
@RestController
public class BackendMonolith {

    private static final String UPLOAD_DIR = "uploads";
    private static final String STRIPE_SECRET_KEY = "sk_test_REPLACE_ME";
    private static final String JWT_SECRET = "super_secure_jwt_secret_value_change_me";
    private static final String ADMIN_TOKEN = "admin_dev_token_2025";
    private static final long JWT_EXPIRATION = 1000 * 60 * 60;

    private final Map<String, String> sessionToFile = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException {
        Files.createDirectories(Paths.get(UPLOAD_DIR));
        Stripe.apiKey = STRIPE_SECRET_KEY;
        SpringApplication.run(BackendMonolith.class, args);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("token") String token,
                                    @RequestParam("file") MultipartFile file) {
        try {
            if (!ADMIN_TOKEN.equals(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized upload.");
            }

            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Empty file.");
            }

            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get(UPLOAD_DIR, filename);
            Files.write(path, file.getBytes());

            return ResponseEntity.ok("Uploaded: " + filename);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(500).body("Upload failed.");
        }
    }

    @PostMapping("/create-checkout-session")
    public ResponseEntity<?> createCheckoutSession(@RequestParam("filename") String filename) {
        try {
            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("https://yourdomain.com/success?session_id={CHECKOUT_SESSION_ID}")
                    .setCancelUrl("https://yourdomain.com/cancel")
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency("usd")
                                                    .setUnitAmount(299L)
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName("Java1Kind Download")
                                                                    .build()
                                                    ).build()
                                    ).build()
                    ).build();

            Session session = Session.create(params);

            sessionToFile.put(session.getId(), filename);

            return ResponseEntity.ok(Map.of("id", session.getId()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Stripe session failed.");
        }
    }

    @PostMapping("/generate-token")
    public ResponseEntity<?> generateToken(@RequestParam("sessionId") String sessionId) {
        try {
            String filename = sessionToFile.get(sessionId);
            if (filename == null) {
                return ResponseEntity.badRequest().body("Invalid session.");
            }

            String jwt = Jwts.builder()
                    .setSubject(filename)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                    .signWith(getSigningKey())
                    .compact();

            return ResponseEntity.ok(Map.of("token", jwt));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Token generation failed.");
        }
    }

    @GetMapping("/download")
    public ResponseEntity<?> download(@RequestParam("token") String jwt, HttpServletResponse response) {
        try {
            String filename = validateJwtAndGetFilename(jwt);
            Path path = Paths.get(UPLOAD_DIR, filename);

            if (!Files.exists(path)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found.");
            }

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);

            InputStream inputStream = Files.newInputStream(path);
            StreamUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
            return null;
        } catch (JwtException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Download failed.");
        }
    }

    private Key getSigningKey() {
        return new SecretKeySpec(JWT_SECRET.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }

    private String validateJwtAndGetFilename(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();
    }
}
