package com.example.social_media_backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;


@Embeddable
public class VoteId implements Serializable {
    
    @Column(name = "owner_id")
    private Long ownerId;
    
    @Column(name = "post_id")
    private Long postId;

    public VoteId() {}

    public VoteId(Long ownerId, Long postId) {
        this.ownerId = ownerId;
        this.postId = postId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
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
        return Objects.equals(ownerId, voteId.ownerId) && Objects.equals(postId, voteId.postId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ownerId, postId);
    }

    @Override
    public String toString() {
        return "VoteId{" +
                "ownerId=" + ownerId +
                ", postId=" + postId +
                '}';
    }
} 