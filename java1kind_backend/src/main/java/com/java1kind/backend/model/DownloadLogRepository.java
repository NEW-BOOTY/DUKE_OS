package com.java1kind.repository;

import com.java1kind.model.DownloadLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */
public interface DownloadLogRepository extends JpaRepository<DownloadLog, Long> {
    List<DownloadLog> findByUserId(Long userId);
}
