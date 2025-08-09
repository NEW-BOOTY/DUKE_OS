/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */
package com.java1kind.backend.service;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentSessionService {

    @Value("${stripe.secret-key}")
    private String stripeSecretKey;

    @Value("${stripe.success-url}")
    private String successUrl;

    @Value("${stripe.cancel-url}")
    private String cancelUrl;

    public String createSession(String uploadId, String email) throws Exception {
        Stripe.apiKey = stripeSecretKey;

        SessionCreateParams params =
            SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(successUrl + "?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl(cancelUrl)
                .setCustomerEmail(email)
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .addLineItem(
                    SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(
                            SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("usd")
                                .setUnitAmount(500L) // $5.00
                                .setProductData(
                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("Java Program")
                                        .build()
                                )
                                .build()
                        )
                        .build()
                )
                .putMetadata("uploadId", uploadId)
                .build();

        Session session = Session.create(params);
        return session.getUrl();
    }

    public Session retrieveSession(String sessionId) throws Exception {
        Stripe.apiKey = stripeSecretKey;
        return Session.retrieve(sessionId);
    }
}
