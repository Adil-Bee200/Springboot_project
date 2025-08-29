package com.example.social_media_backend.DTO;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UserResponseDTOTest {

    @Test
    void testConstructor() {
        UserResponseDTO user = new UserResponseDTO(1L, "test@example.com");
        assertEquals(1L, user.getId());
        assertEquals("test@example.com", user.getEmail());
    }

    @Test
    void testConstructorWithNullId() {
        UserResponseDTO user = new UserResponseDTO(null, "test@example.com");
        assertNull(user.getId());
        assertEquals("test@example.com", user.getEmail());
    }

    @Test
    void testConstructorWithNullEmail() {
        UserResponseDTO user = new UserResponseDTO(1L, null);
        assertEquals(1L, user.getId());
        assertNull(user.getEmail());
    }

    @Test
    void testGetId() {
        UserResponseDTO user = new UserResponseDTO(123L, "test@example.com");
        Long id = user.getId();
        assertEquals(123L, id);
    }

    @Test
    void testGetEmail() {
        UserResponseDTO user = new UserResponseDTO(1L, "user@example.com");
        String email = user.getEmail();
        assertEquals("user@example.com", email);
    }
} 