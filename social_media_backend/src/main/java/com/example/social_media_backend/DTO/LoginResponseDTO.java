package com.example.social_media_backend.DTO;

public class LoginResponseDTO {
    
    private String token;
    private String email;
    private Long userId;
    private String message;

    public LoginResponseDTO() {}

    public LoginResponseDTO(String token, String email, Long userId, String message) {
        this.token = token;
        this.email = email;
        this.userId = userId;
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
} 