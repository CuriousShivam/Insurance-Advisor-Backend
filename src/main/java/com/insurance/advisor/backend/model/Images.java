package com.insurance.advisor.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "images")
@Getter
@Setter
@NoArgsConstructor
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false, name = "file_id")
    private String fileId;

    @Column(name = "alt_text")
    private String altText;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // Standard Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; } // This fixes "cannot resolve setUrl"

    public String getFileId() { return fileId; }
    public void setFileId(String fileId) { this.fileId = fileId; } // This fixes "cannot resolve setFileId"

    public String getAltText() { return altText; }
    public void setAltText(String altText) { this.altText = altText; } // This fixes "cannot resolve setAltText"

    public LocalDateTime getCreatedAt() { return createdAt; }
}