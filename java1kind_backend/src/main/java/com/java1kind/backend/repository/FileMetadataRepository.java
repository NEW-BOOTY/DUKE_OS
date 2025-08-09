/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package com.java1kind.backend.repository;

import com.java1kind.backend.model.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {

    Optional<FileMetadata> findByFilename(String filename);

    Optional<FileMetadata> findByStripeSessionId(String sessionId);

}
