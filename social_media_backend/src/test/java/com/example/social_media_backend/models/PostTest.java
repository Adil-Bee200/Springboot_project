package com.example.social_media_backend.models;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PostTest {

    private Post post;
    private User testUser;

    @BeforeEach
    void setUp() {
        post = new Post();
        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@example.com");
    }

    @Test
    void testConstructor() {
        assertNotNull(post);
        assertNull(post.getId());
        assertNull(post.getTitle());
        assertNull(post.getContent());
        assertTrue(post.getPublished()); 
        assertNull(post.getCreatedAt());
        assertNull(post.getOwner());
    }

    @Test
    void testSetId() {
        post.setId(1L);
        assertEquals(1L, post.getId());
    }

    @Test
    void testSetTitle() {
        post.setTitle("Test Title");
        assertEquals("Test Title", post.getTitle());
    }

    @Test
    void testSetContent() {
        post.setContent("Test Content");
        assertEquals("Test Content", post.getContent());
    }

    @Test
    void testSetPublished() {
        post.setPublished(false);
        assertFalse(post.getPublished());
    }

    @Test
    void testSetCreatedAt() {
        Instant now = Instant.now();
        post.setCreatedAt(now);
        assertEquals(now, post.getCreatedAt());
    }

    @Test
    void testSetOwner() {
        post.setOwner(testUser);
        assertEquals(testUser, post.getOwner());
    }

    @Test
    void testGetOwnerId() {
        post.setOwner(testUser);

        Long ownerId = post.getOwnerId();

        assertEquals(1L, ownerId);
    }

    @Test
    void testGetOwnerIdWhenOwnerIsNull() {
        post.setOwner(null);

        Long ownerId = post.getOwnerId();

        assertNull(ownerId);
    }

    @Test
    void getOwnerIdWhenOwnerIdIsNull() {
        User userWithNullId = new User();
        userWithNullId.setId(null);
        post.setOwner(userWithNullId);

        Long ownerId = post.getOwnerId();

        assertNull(ownerId);
    }

    @Test
    void testOnCreate() {
        Post newPost = new Post();
        assertNull(newPost.getCreatedAt());

        Instant beforeCreation = Instant.now();
        newPost.onCreate();
        Instant afterCreation = Instant.now();

        assertNotNull(newPost.getCreatedAt());
        assertTrue(newPost.getCreatedAt().isBefore(afterCreation) || newPost.getCreatedAt().equals(afterCreation));
        assertTrue(newPost.getCreatedAt().isAfter(beforeCreation) || newPost.getCreatedAt().equals(beforeCreation));
    }

    @Test
    void testOnCreateSetsPublishedToTrueIfNull() {
        Post newPost = new Post();
        newPost.setPublished(null);

        newPost.onCreate();

        assertTrue(newPost.getPublished());
    }


    @Test
    void testGettersAndSetters() {
        Instant now = Instant.now();
        post.setId(1L);
        post.setTitle("Test Title");
        post.setContent("Test Content");
        post.setPublished(true);
        post.setCreatedAt(now);
        post.setOwner(testUser);

        assertEquals(1L, post.getId());
        assertEquals("Test Title", post.getTitle());
        assertEquals("Test Content", post.getContent());
        assertTrue(post.getPublished());
        assertEquals(now, post.getCreatedAt());
        assertEquals(testUser, post.getOwner());
    }

} 