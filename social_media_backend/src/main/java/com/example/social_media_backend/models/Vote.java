package com.example.social_media_backend.models;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "votes")
public class Vote {
    @EmbeddedId
    private VoteId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("postId")
    @JoinColumn(name = "post_id", nullable = false)
    @JsonIgnore
    private Post post;

    @Column(name = "direction", nullable = false)
    private int direction; // 1 for upvote, -1 for downvote

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public Vote() {}

    public Vote(User owner, Post post, int direction) {
        this.owner = owner;
        this.post = post;
        this.direction = direction;
        this.id = new VoteId(owner.getId(), post.getId());
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
    }

    public VoteId getId() {
        return id;
    }

    public void setId(VoteId id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
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

    public Long getPostId() {
        return post != null ? post.getId() : null;
    }

    public Long getOwnerId() {
        return owner != null ? owner.getId() : null;
    }

    public void setTime() {
        this.createdAt = Instant.now();  
    }
}
