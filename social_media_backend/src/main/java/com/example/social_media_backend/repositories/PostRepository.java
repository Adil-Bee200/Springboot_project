package com.example.social_media_backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.social_media_backend.models.Post;
import com.example.social_media_backend.models.User;

public interface PostRepository extends JpaRepository<Post, Long> {
    
    List<Post> findByOwner(User owner);
    
    List<Post> findByOwnerId(Long ownerId);
    
    List<Post> findByPublishedTrue();
    
    List<Post> findByTitleContainingIgnoreCase(String title);
    
    @Query("SELECT p FROM Post p WHERE p.published = true ORDER BY p.createdAt DESC")
    List<Post> findAllPublishedPostsOrderByCreatedAtDesc();
} 