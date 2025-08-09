/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */
package com.java1kind.service;

import com.java1kind.model.DownloadLog;
import com.java1kind.repository.DownloadLogRepository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;

@Service
public class DownloadLogService {

    private final DownloadLogRepository repository;
    private final FileVersionService versionService;

    public DownloadLogService(DownloadLogRepository repository, FileVersionService versionService) {
        this.repository = repository;
        this.versionService = versionService;
    }

    public void logDownload(long versionId, String ip, String userAgent) {
        DownloadLog log = new DownloadLog();
        log.setFileVersionId(versionId);
        log.setIpAddress(ip);
        log.setUserAgent(userAgent);
        log.setDownloadedAt(LocalDateTime.now());
        repository.save(log);
    }

    public File getDecryptedFile(long versionId) {
        return versionService.getDecryptedFileFromVersion(versionId);
    }
}
