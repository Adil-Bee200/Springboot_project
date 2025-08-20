package com.example.social_media_backend.DTO;


public class UserResponseDTO {
    private Long id;
    private String email;

    public UserResponseDTO(Long id, String email) {
        this.email = email;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
