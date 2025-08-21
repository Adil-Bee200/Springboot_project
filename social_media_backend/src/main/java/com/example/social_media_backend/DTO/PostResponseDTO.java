package com.example.social_media_backend.DTO;

import java.time.Instant;

public class PostResponseDTO {
    private Long id;
    private String title;
    private String content;
    private Boolean published;
    private Instant createdAt;
    private Long ownerId;
    private String ownerEmail;

    public PostResponseDTO() {}

    public PostResponseDTO(Long id, String title, String content, Boolean published, Instant createdAt, Long ownerId, String ownerEmail) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.published = published;
        this.createdAt = createdAt;
        this.ownerId = ownerId;
        this.ownerEmail = ownerEmail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }
} 