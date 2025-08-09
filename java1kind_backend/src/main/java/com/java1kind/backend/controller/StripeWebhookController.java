/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package com.java1kind.backend.controller;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.checkout.Session;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.net.Webhook;
import com.stripe.net.ApiResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/webhook")
public class StripeWebhookController {

    private static final Logger logger = LoggerFactory.getLogger(StripeWebhookController.class);

    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    @PostMapping
    public ResponseEntity<Map<String, String>> handleStripeEvent(@RequestBody String payload,
                                                                 @RequestHeader("Stripe-Signature") String sigHeader) {
        try {
            Event event = Webhook.constructEvent(payload, sigHeader, endpointSecret);

            String eventType = event.getType();
            logger.info("🔔 Stripe Event Received: {}", eventType);

            switch (eventType) {
                case "checkout.session.completed":
                    EventDataObjectDeserializer deserializer = event.getDataObjectDeserializer();
                    if (deserializer.getObject().isPresent()) {
                        Session session = (Session) deserializer.getObject().get();
                        logger.info("✅ Checkout session completed for session ID: {}", session.getId());

                        // TODO: Implement business logic (e.g., mark download as unlocked for this user/session)
                        // Future: map session.getClientReferenceId() to a User or Product license

                    } else {
                        logger.warn("⚠️ Unable to deserialize session object.");
                    }
                    break;

                default:
                    logger.info("ℹ️ Unhandled event type: {}", eventType);
                    break;
            }

            Map<String, String> response = new HashMap<>();
            response.put("status", "received");
            return ResponseEntity.ok(response);

        } catch (SignatureVerificationException e) {
            logger.error("❌ Signature verification failed: {}", e.getMessage());
            return ResponseEntity.status(400).body(Map.of("error", "Invalid signature."));
        } catch (Exception e) {
            logger.error("🔥 Exception in webhook handler: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(Map.of("error", "Internal server error."));
        }
    }
}
