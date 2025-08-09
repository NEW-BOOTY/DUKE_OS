/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */
package com.java1kind.backend.controller;

import com.java1kind.backend.service.DownloadAccessService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/download")
public class SecureDownloadController {

    private final DownloadAccessService downloadAccessService;

    public SecureDownloadController(DownloadAccessService downloadAccessService) {
        this.downloadAccessService = downloadAccessService;
    }

    @GetMapping("/token/{sessionId}")
    public ResponseEntity<String> issueDownloadToken(@PathVariable String sessionId) {
        try {
            String token = downloadAccessService.generateDownloadToken(sessionId);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid or unverified session");
        }
    }

    @GetMapping("/file/{token}")
    public void downloadFile(@PathVariable String token, HttpServletResponse response) throws IOException {
        if (!downloadAccessService.validateToken(token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
            return;
        }

        byte[] fileBytes = downloadAccessService.fetchFileFromSessionToken(token);
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=your_java_program.zip");
        response.getOutputStream().write(fileBytes);
        response.flushBuffer();
    }
}
