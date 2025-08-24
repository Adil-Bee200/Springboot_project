package com.example.social_media_backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;


@Embeddable
public class VoteId implements Serializable {
    
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "post_id")
    private Long postId;

    public VoteId() {}

    public VoteId(Long userId, Long postId) {
        this.userId = userId;
        this.postId = postId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoteId voteId = (VoteId) o;
        return Objects.equals(userId, voteId.userId) && Objects.equals(postId, voteId.postId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, postId);
    }

    @Override
    public String toString() {
        return "VoteId{" +
                "userId=" + userId +
                ", postId=" + postId +
                '}';
    }
} 