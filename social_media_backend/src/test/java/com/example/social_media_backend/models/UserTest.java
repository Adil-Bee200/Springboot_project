package com.example.social_media_backend.models;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void testConstructor() {
        assertNotNull(user);
        assertNull(user.getId());
        assertNull(user.getEmail());
        assertNull(user.getPassword());
        assertNull(user.getCreatedAt());
    }

    @Test
    void testSetId() {
        Long id = 123L;
        user.setId(id);
        assertEquals(id, user.getId());
    }

    @Test
    void testSetIdWithPrimitiveLong() {
        long id = 456L;
        user.setId(id);
        assertEquals(id, user.getId());
    }

    @Test
    void testGetId() {
        Long id = 789L;
        user.setId(id);
        Long result = user.getId();
        assertEquals(id, result);
    }

    @Test
    void testSetEmail() {
        String email = "test@example.com";
        user.setEmail(email);
        assertEquals(email, user.getEmail());
    }

    @Test
    void testSetEmailAsEmptyString() {
        user.setEmail("");
        assertEquals("", user.getEmail());
    }

    @Test
    void testGetEmail() {
        String email = "user@example.com";
        user.setEmail(email);
        String result = user.getEmail();
        assertEquals(email, result);
    }

    @Test
    void testSetPassword() {
        String password = "Password123";
        user.setPassword(password);
        assertEquals(password, user.getPassword());
    }

    @Test
    void testSetPasswordAsEmptyString() {
        user.setPassword("");
        assertEquals("", user.getPassword());
    }

    @Test
    void testGetPassword() {
        String password = "Password456";
        user.setPassword(password);
        String result = user.getPassword();
        assertEquals(password, result);
    }

    @Test
    void testOnCreateSetTime() {
        assertNull(user.getCreatedAt());
        user.onCreate();
        assertNotNull(user.getCreatedAt());
    }

    @Test
    void testOnCreateTimeIsCorrect() {
        Instant beforeCreation = Instant.now();
        user.onCreate();
        Instant afterCreation = Instant.now();
        assertTrue(user.getCreatedAt().isAfter(beforeCreation) || user.getCreatedAt().equals(beforeCreation));
        assertTrue(user.getCreatedAt().isBefore(afterCreation) || user.getCreatedAt().equals(afterCreation));
    }
} 