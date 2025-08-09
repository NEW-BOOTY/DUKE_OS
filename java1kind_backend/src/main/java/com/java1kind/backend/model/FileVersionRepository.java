package com.java1kind.repository;

import com.java1kind.model.FileVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */
public interface FileVersionRepository extends JpaRepository<FileVersion, Long> {
    List<FileVersion> findByRepositoryId(Long repoId);
}
