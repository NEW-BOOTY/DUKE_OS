package com.java1kind.repository;

import com.java1kind.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserId(Long userId);
}
