package com.example.social_media_backend.DTO;

import java.time.Instant;

public class VoteResponseDTO {
    private Long userId;
    private Long postId;
    private int direction;
    private Instant createdAt;

    public VoteResponseDTO() {}

    public VoteResponseDTO(Long userId, Long postId, int direction, Instant createdAt) {
        this.userId = userId;
        this.postId = postId;
        this.direction = direction;
        this.createdAt = createdAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
} 