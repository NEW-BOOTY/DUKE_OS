/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */
package com.java1kind.service;

import com.java1kind.model.Transaction;
import com.java1kind.repository.TransactionRepository;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Value("${stripe.webhook-secret}")
    private String webhookSecret;

    private final TransactionRepository repository;
    private final UserService userService;

    public TransactionService(TransactionRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public void recordPendingTransaction(String userEmail, String stripeId, int amountCents) {
        repository.save(new Transaction(
                userService.resolveUserId(userEmail),
                stripeId,
                amountCents,
                "PENDING"
        ));
    }

    public void verifyAndRecordStripeEvent(String payload, String sigHeader) {
        try {
            Event event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
            if ("checkout.session.completed".equals(event.getType())) {
                Session session = (Session) event.getDataObjectDeserializer().getObject().get();
                repository.markTransactionSuccessful(session.getId());
            }
        } catch (SignatureVerificationException e) {
            System.err.println("❌ Stripe webhook verification failed: " + e.getMessage());
        } catch (Exception ex) {
            System.err.println("❌ Webhook error: " + ex.getMessage());
        }
    }
}
