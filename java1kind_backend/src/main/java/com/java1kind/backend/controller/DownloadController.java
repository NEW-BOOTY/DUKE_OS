/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */
package com.java1kind.controller;

import com.java1kind.service.DownloadLogService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("/api/download")
public class DownloadController {

    private final DownloadLogService logService;

    public DownloadController(DownloadLogService logService) {
        this.logService = logService;
    }

    @GetMapping("/{versionId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable long versionId, HttpServletRequest request) {
        File file = logService.getDecryptedFile(versionId);
        if (file == null || !file.exists()) {
            return ResponseEntity.notFound().build();
        }

        logService.logDownload(versionId, request.getRemoteAddr(), request.getHeader("User-Agent"));
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + file.getName())
                .body(new FileSystemResource(file));
    }
}
