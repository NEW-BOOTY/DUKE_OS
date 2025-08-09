/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */
package com.java1kind.backend.controller;

import com.java1kind.backend.service.PaymentSessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/stripe")
public class StripePaymentController {

    private final PaymentSessionService paymentSessionService;

    public StripePaymentController(PaymentSessionService paymentSessionService) {
        this.paymentSessionService = paymentSessionService;
    }

    @PostMapping("/create-checkout-session")
    public ResponseEntity<Map<String, String>> createCheckoutSession(@RequestBody Map<String, String> request) {
        String uploadId = request.get("uploadId");
        String email = request.get("email");

        try {
            String sessionUrl = paymentSessionService.createSession(uploadId, email);
            return ResponseEntity.ok(Map.of("checkoutUrl", sessionUrl));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Could not create Stripe session"));
        }
    }
}
