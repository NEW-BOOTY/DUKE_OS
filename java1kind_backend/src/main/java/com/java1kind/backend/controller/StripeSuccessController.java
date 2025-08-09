/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */
package com.java1kind.backend.controller;

import com.java1kind.backend.service.DownloadAccessService;
import com.java1kind.backend.service.PaymentSessionService;
import com.stripe.model.checkout.Session;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/stripe")
public class StripeSuccessController {

    private final PaymentSessionService paymentSessionService;
    private final DownloadAccessService downloadAccessService;

    public StripeSuccessController(PaymentSessionService paymentSessionService,
                                   DownloadAccessService downloadAccessService) {
        this.paymentSessionService = paymentSessionService;
        this.downloadAccessService = downloadAccessService;
    }

    @GetMapping("/success")
    public ResponseEntity<Map<String, String>> handleSuccess(@RequestParam("session_id") String sessionId) {
        try {
            Session session = paymentSessionService.retrieveSession(sessionId);
            String uploadId = session.getMetadata().get("uploadId");

            String token = downloadAccessService.generateAccessToken(session.getId());
            return ResponseEntity.ok(Map.of(
                "message", "Payment successful",
                "token", token,
                "uploadId", uploadId
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid session"));
        }
    }
}
