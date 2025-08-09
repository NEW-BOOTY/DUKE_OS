/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */
package com.java1kind.controller;

import com.java1kind.service.EncryptionService;
import com.java1kind.service.StorageService;
import com.java1kind.model.UploadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/admin/upload")
public class UploadController {

    @Autowired private EncryptionService encryptionService;
    @Autowired private StorageService storageService;

    @PostMapping
    public ResponseEntity<UploadResponse> handleUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty() || !StringUtils.hasText(file.getOriginalFilename())) {
            return ResponseEntity.badRequest().body(new UploadResponse("❌ Invalid file upload request."));
        }

        try {
            byte[] encryptedBytes = encryptionService.encrypt(file.getBytes());
            String storedPath = storageService.storeEncryptedFile(file.getOriginalFilename(), encryptedBytes);
            return ResponseEntity.ok(new UploadResponse("✅ File uploaded and encrypted successfully: " + storedPath));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(new UploadResponse("❌ Upload failed: " + e.getMessage()));
        }
    }
}
