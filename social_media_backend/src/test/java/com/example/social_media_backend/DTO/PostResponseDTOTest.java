package com.example.social_media_backend.DTO;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.time.Instant;

class PostResponseDTOTest {

    @Test
    void testConstructor() {
        Instant now = Instant.now();
        Long id = 1L;
        String title = "Test Title";
        String content = "Test Content";
        Boolean published = true;
        Long ownerId = 2L;
        String ownerEmail = "test@example.com";

        PostResponseDTO post = new PostResponseDTO(id, title, content, published, now, ownerId, ownerEmail);

        assertEquals(id, post.getId());
        assertEquals(title, post.getTitle());
        assertEquals(content, post.getContent());
        assertEquals(published, post.getPublished());
        assertEquals(now, post.getCreatedAt());
        assertEquals(ownerId, post.getOwnerId());
        assertEquals(ownerEmail, post.getOwnerEmail());
    }

    @Test
    void testConstructorWithNullValues() {
        PostResponseDTO post = new PostResponseDTO(null, null, null, null, null, null, null);

        assertNull(post.getId());
        assertNull(post.getTitle());
        assertNull(post.getContent());
        assertNull(post.getPublished());
        assertNull(post.getCreatedAt());
        assertNull(post.getOwnerId());
        assertNull(post.getOwnerEmail());
    }

    @Test
    void testConstructorCreateEmptyPost() {
        PostResponseDTO post = new PostResponseDTO();

        assertNotNull(post);
        assertNull(post.getId());
        assertNull(post.getTitle());
        assertNull(post.getContent());
        assertNull(post.getPublished());
        assertNull(post.getCreatedAt());
        assertNull(post.getOwnerId());
        assertNull(post.getOwnerEmail());
    }

    @Test
    void testSetId() {
        PostResponseDTO post = new PostResponseDTO();

        post.setId(1L);

        assertEquals(1L, post.getId());
    }

    @Test
    void testSetTitle() {
        PostResponseDTO post = new PostResponseDTO();

        post.setTitle("New Title");

        assertEquals("New Title", post.getTitle());
    }

    @Test
    void testSetContent() {
        PostResponseDTO post = new PostResponseDTO();

        post.setContent("New Content");

        assertEquals("New Content", post.getContent());
    }

    @Test
    void testSetPublished() {
        PostResponseDTO post = new PostResponseDTO();

        post.setPublished(false);

        assertFalse(post.getPublished());
    }

    @Test
    void testSetCreatedAt() {
        PostResponseDTO post = new PostResponseDTO();
        Instant now = Instant.now();

        post.setCreatedAt(now);

        assertEquals(now, post.getCreatedAt());
    }

    @Test
    void testSetOwnerId() {
        PostResponseDTO post = new PostResponseDTO();

        post.setOwnerId(3L);

        assertEquals(3L, post.getOwnerId());
    }

    @Test
    void testSetOwnerEmail() {
        PostResponseDTO post = new PostResponseDTO();

        post.setOwnerEmail("new@example.com");

        assertEquals("new@example.com", post.getOwnerEmail());
    }

    @Test
    void testGetters() {
        Instant now = Instant.now();
        PostResponseDTO post = new PostResponseDTO(1L, "Title", "Content", true, now, 2L, "email@example.com");

        assertEquals(1L, post.getId());
        assertEquals("Title", post.getTitle());
        assertEquals("Content", post.getContent());
        assertTrue(post.getPublished());
        assertEquals(now, post.getCreatedAt());
        assertEquals(2L, post.getOwnerId());
        assertEquals("email@example.com", post.getOwnerEmail());
    }
} 