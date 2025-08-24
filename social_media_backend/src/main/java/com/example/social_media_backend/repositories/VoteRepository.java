package com.example.social_media_backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.social_media_backend.models.Post;
import com.example.social_media_backend.models.User;
import com.example.social_media_backend.models.Vote;
import com.example.social_media_backend.models.VoteId;

public interface VoteRepository extends JpaRepository<Vote, VoteId> {

    List<Vote> findByOwner(User owner);

    List<Vote> findByPost(Post post);
    
    Optional<Vote> findByOwnerAndPost(User owner, Post post);
    
    @Query("SELECT COUNT(v) FROM Vote v WHERE v.post = :post")
    long countByPost(@Param("post") Post post);
    
    @Query("SELECT COUNT(v) FROM Vote v WHERE v.owner = :owner")
    long countByOwner(@Param("owner") User owner);
    
    void deleteByPost(Post post);
    
    void deleteByOwner(User owner);
} 