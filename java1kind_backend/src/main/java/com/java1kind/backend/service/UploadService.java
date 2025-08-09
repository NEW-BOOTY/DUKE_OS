/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */
package com.java1kind.backend.service;

import com.java1kind.backend.model.Upload;
import com.java1kind.backend.repository.UploadRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.HexFormat;

@Service
public class UploadService {

    private static final Logger logger = LoggerFactory.getLogger(UploadService.class);
    private static final String STORAGE_PATH = "/var/java1kind/uploads/secure-repo/";

    private final UploadRepository uploadRepository;

    public UploadService(UploadRepository uploadRepository) {
        this.uploadRepository = uploadRepository;
    }

    @PostConstruct
    public void ensureStorageDirectory() {
        try {
            Files.createDirectories(Paths.get(STORAGE_PATH));
        } catch (IOException e) {
            logger.error("Failed to create secure upload directory", e);
            throw new RuntimeException("Could not initialize storage path", e);
        }
    }

    public Upload store(MultipartFile file, String uploader, String version) throws Exception {
        if (file.isEmpty()) throw new IOException("Empty file upload attempt.");

        String originalFilename = file.getOriginalFilename();
        long size = file.getSize();
        String fileType = file.getContentType();

        byte[] bytes = file.getBytes();
        String checksum = computeSHA256(bytes);

        String securedFileName = System.currentTimeMillis() + "_" + originalFilename;
        Path destination = Paths.get(STORAGE_PATH).resolve(securedFileName);

        Files.write(destination, bytes, StandardOpenOption.CREATE_NEW);

        Upload upload = new Upload(originalFilename, fileType, uploader, size, checksum, version);
        upload.setUploadedAt(LocalDateTime.now());

        Upload saved = uploadRepository.save(upload);

        logger.info("Upload successful: {}", securedFileName);
        return saved;
    }

    private String computeSHA256(byte[] data) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(data);
        return HexFormat.of().formatHex(hash);
    }
}
