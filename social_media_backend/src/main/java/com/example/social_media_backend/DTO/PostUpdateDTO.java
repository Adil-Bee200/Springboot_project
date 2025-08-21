package com.example.social_media_backend.DTO;

import jakarta.validation.constraints.Size;

public class PostUpdateDTO {
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
    private String title;

    @Size(min = 1, max = 10000, message = "Content must be between 1 and 10000 characters")
    private String content;

    private Boolean published;

    public PostUpdateDTO() {}

    public PostUpdateDTO(String title, String content, Boolean published) {
        this.title = title;
        this.content = content;
        this.published = published;
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

    // Helper method to check if any field is being updated
    public boolean hasUpdates() {
        return title != null || content != null || published != null;
    }
} 