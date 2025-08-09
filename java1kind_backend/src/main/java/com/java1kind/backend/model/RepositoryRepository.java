package com.java1kind.repository;

import com.java1kind.model.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */
public interface RepositoryRepository extends JpaRepository<Repository, Long> {
    List<Repository> findByUploaderId(Long userId);
}
