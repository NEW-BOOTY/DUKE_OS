package com.java1kind.repository;

import com.java1kind.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
