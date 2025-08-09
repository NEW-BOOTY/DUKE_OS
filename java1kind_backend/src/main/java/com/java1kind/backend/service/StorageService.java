/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */
package com.java1kind.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class StorageService {

    private final String STORAGE_ROOT = "uploads/repos/";

    public String storeEncryptedFile(String originalFileName, byte[] encryptedData) throws IOException {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String safeName = originalFileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
        String fileName = timestamp + "_" + safeName;

        File targetFile = Paths.get(STORAGE_ROOT, fileName).toFile();
        targetFile.getParentFile().mkdirs();

        try (FileOutputStream fos = new FileOutputStream(targetFile)) {
            fos.write(encryptedData);
        }

        return targetFile.getAbsolutePath();
    }
}
