/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */
package com.java1kind.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String stripePaymentId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private LocalDateTime paidAt = LocalDateTime.now();

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private String currency = "USD";

    // Getters & Setters
}
