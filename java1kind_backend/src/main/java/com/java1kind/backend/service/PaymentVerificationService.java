/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package com.java1kind.backend.service;

import com.stripe.model.checkout.Session;
import com.java1kind.backend.model.PaymentVerification;
import com.java1kind.backend.repository.PaymentVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Optional;

@Service
public class PaymentVerificationService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentVerificationService.class);

    @Autowired
    private PaymentVerificationRepository paymentVerificationRepository;

    public void verifyAndStoreSession(Session session) {
        try {
            String sessionId = session.getId();
            String email = session.getCustomerDetails() != null ? session.getCustomerDetails().getEmail() : "unknown";

            Optional<PaymentVerification> existing = paymentVerificationRepository.findBySessionId(sessionId);

            if (existing.isEmpty()) {
                PaymentVerification verification = new PaymentVerification();
                verification.setSessionId(sessionId);
                verification.setEmail(email);
                verification.setVerifiedAt(Instant.now());
                verification.setProductId(session.getClientReferenceId());

                paymentVerificationRepository.save(verification);
                logger.info("✅ Payment session {} verified and saved for email: {}", sessionId, email);
            } else {
                logger.info("ℹ️ Session {} already verified.", sessionId);
            }

        } catch (Exception e) {
            logger.error("🔥 Failed to verify and store session: {}", e.getMessage(), e);
        }
    }

    public boolean isSessionVerified(String sessionId) {
        return paymentVerificationRepository.findBySessionId(sessionId).isPresent();
    }
}
