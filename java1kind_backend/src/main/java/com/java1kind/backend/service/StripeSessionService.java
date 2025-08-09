/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package com.java1kind.backend.service;

import com.java1kind.backend.exception.NotFoundException;
import com.java1kind.backend.model.FileMetadata;
import com.java1kind.backend.repository.FileMetadataRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StripeSessionService {

    private final FileMetadataRepository fileMetadataRepository;

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @Value("${stripe.success.url}")
    private String successUrl;

    @Value("${stripe.cancel.url}")
    private String cancelUrl;

    public StripeSessionService(FileMetadataRepository fileMetadataRepository) {
        this.fileMetadataRepository = fileMetadataRepository;
    }

    @PostConstruct
    private void init() {
        Stripe.apiKey = stripeSecretKey;
    }

    public String createSession(Long fileId) {
        FileMetadata file = fileMetadataRepository.findById(fileId)
                .orElseThrow(() -> new NotFoundException("File not found with id: " + fileId));

        try {
            SessionCreateParams params =
                SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(successUrl + "?session_id={CHECKOUT_SESSION_ID}")
                    .setCancelUrl(cancelUrl)
                    .addLineItem(
                        SessionCreateParams.LineItem.builder()
                            .setQuantity(1L)
                            .setPriceData(
                                SessionCreateParams.LineItem.PriceData.builder()
                                    .setCurrency("usd")
                                    .setUnitAmount((long)(file.getPrice() * 100))
                                    .setProductData(
                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                            .setName(file.getFilename())
                                            .build()
                                    )
                                    .build()
                            )
                            .build()
                    )
                    .build();

            Session session = Session.create(params);
            file.setStripeSessionId(session.getId());
            fileMetadataRepository.save(file);

            return session.getUrl();

        } catch (StripeException e) {
            throw new RuntimeException("Failed to create Stripe session: " + e.getMessage(), e);
        }
    }

    public FileMetadata validateAndGetFileBySession(String sessionId) {
        return fileMetadataRepository.findByStripeSessionId(sessionId)
                .orElseThrow(() -> new NotFoundException("No file found with session id: " + sessionId));
    }
}
