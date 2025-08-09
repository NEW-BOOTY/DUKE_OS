/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package com.java1kind.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "uploads")
public class Upload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @Column(name = "mime_type", nullable = false)
    private String mimeType;

    @Column(name = "version", nullable = false)
    private String version;

    @Column(name = "checksum", nullable = false, unique = true)
    private String checksum;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "upload_path", nullable = false)
    private String uploadPath;

    @Column(name = "uploaded_at", nullable = false)
    private LocalDateTime uploadedAt;

    @Column(name = "uploader_ip")
    private String uploaderIp;

    @Column(name = "download_count")
    private Long downloadCount = 0L;

    @Column(name = "repo_name", nullable = false)
    private String repoName;

    @Column(name = "uploaded_by", nullable = false)
    private String uploadedBy;

    @Column(name = "is_public", nullable = false)
    private boolean isPublic = false;

    public Upload() {}

    public Upload(String fileName, Long fileSize, String mimeType, String version,
                  String checksum, String description, String uploadPath,
                  LocalDateTime uploadedAt, String uploaderIp,
                  String repoName, String uploadedBy, boolean isPublic) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.mimeType = mimeType;
        this.version = version;
        this.checksum = checksum;
        this.description = description;
        this.uploadPath = uploadPath;
        this.uploadedAt = uploadedAt;
        this.uploaderIp = uploaderIp;
        this.repoName = repoName;
        this.uploadedBy = uploadedBy;
        this.isPublic = isPublic;
    }

    // ========== GETTERS AND SETTERS ========== //

    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public String getUploaderIp() {
        return uploaderIp;
    }

    public void setUploaderIp(String uploaderIp) {
        this.uploaderIp = uploaderIp;
    }

    public Long getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Long downloadCount) {
        this.downloadCount = downloadCount;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }
}
