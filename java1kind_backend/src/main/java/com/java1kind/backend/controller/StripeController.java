/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package com.java1kind.backend.controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@RestController
@RequestMapping("/api/checkout")
public class StripeController {

    private static final Logger logger = LoggerFactory.getLogger(StripeController.class);

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @PostMapping("/session")
    public ResponseEntity<Map<String, String>> createStripeCheckoutSession(@RequestBody CheckoutRequest checkoutRequest) {
        try {
            Stripe.apiKey = stripeSecretKey;

            if (checkoutRequest == null || checkoutRequest.getProductName() == null || checkoutRequest.getAmount() <= 0) {
                logger.error("Invalid checkout request.");
                return ResponseEntity.badRequest().body(Map.of("error", "Invalid request."));
            }

            SessionCreateParams params =
                    SessionCreateParams.builder()
                            .setMode(SessionCreateParams.Mode.PAYMENT)
                            .setSuccessUrl("https://java1kind.com/success?session_id={CHECKOUT_SESSION_ID}")
                            .setCancelUrl("https://java1kind.com/cancel")
                            .addLineItem(
                                    SessionCreateParams.LineItem.builder()
                                            .setQuantity(1L)
                                            .setPriceData(
                                                    SessionCreateParams.LineItem.PriceData.builder()
                                                            .setCurrency("usd")
                                                            .setUnitAmount(checkoutRequest.getAmount())
                                                            .setProductData(
                                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                            .setName(checkoutRequest.getProductName())
                                                                            .build()
                                                            )
                                                            .build()
                                            )
                                            .build()
                            )
                            .build();

            Session session = Session.create(params);

            Map<String, String> response = new HashMap<>();
            response.put("checkoutUrl", session.getUrl());
            response.put("sessionId", session.getId());

            logger.info("Stripe session created for product '{}'", checkoutRequest.getProductName());

            return ResponseEntity.ok(response);

        } catch (StripeException e) {
            logger.error("StripeException during session creation: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(Map.of("error", "Stripe error: " + e.getMessage()));
        } catch (Exception ex) {
            logger.error("Exception during Stripe session creation: {}", ex.getMessage(), ex);
            return ResponseEntity.status(500).body(Map.of("error", "Internal server error."));
        }
    }

    public static class CheckoutRequest {
        private String productName;
        private long amount;

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public long getAmount() {
            return amount;
        }

        public void setAmount(long amount) {
            this.amount = amount;
        }
    }
}
