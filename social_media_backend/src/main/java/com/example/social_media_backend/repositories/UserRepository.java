package com.example.social_media_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.social_media_backend.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
} 
