package com.example.social_media_backend.DTO;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

class PostUpdateDTOTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    void testConstructor() {
        PostUpdateDTO post = new PostUpdateDTO("Test Title", "Test Content", true);

        assertEquals("Test Title", post.getTitle());
        assertEquals("Test Content", post.getContent());
        assertTrue(post.getPublished());
    }

    @Test
    void testConstructorWithNullValues() {
        PostUpdateDTO post = new PostUpdateDTO(null, null, null);

        assertNull(post.getTitle());
        assertNull(post.getContent());
        assertNull(post.getPublished());
    }

    @Test
    void testConstructorEmptyPost() {
        PostUpdateDTO post = new PostUpdateDTO();

        assertNotNull(post);
        assertNull(post.getTitle());
        assertNull(post.getContent());
        assertNull(post.getPublished());
    }

    @Test
    void testSetTitle() {
        PostUpdateDTO post = new PostUpdateDTO();

        post.setTitle("New Title");

        assertEquals("New Title", post.getTitle());
    }

    @Test
    void testSetContent() {
        PostUpdateDTO post = new PostUpdateDTO();

        post.setContent("New Content");

        assertEquals("New Content", post.getContent());
    }

    @Test
    void testSetPublished() {
        PostUpdateDTO post = new PostUpdateDTO();

        post.setPublished(false);

        assertFalse(post.getPublished());
    }

    @Test
    void testHasUpdatesWhenAllFieldsAreNull() {
        PostUpdateDTO post = new PostUpdateDTO(null, null, null);

        boolean result = post.hasUpdates();

        assertFalse(result);
    }

    @Test
    void testHasUpdatesWhenTitleIsNotNull() {
        PostUpdateDTO post = new PostUpdateDTO("Title", null, null);

        boolean result = post.hasUpdates();

        assertTrue(result);
    }

    @Test
    void testHasUpdatesWhenContentIsNotNull() {
        PostUpdateDTO post = new PostUpdateDTO(null, "Content", null);

        boolean result = post.hasUpdates();

        assertTrue(result);
    }

    @Test
    void testHasUpdatesWhenPublishedIsNotNull() {
        PostUpdateDTO post = new PostUpdateDTO(null, null, true);

        boolean result = post.hasUpdates();

        assertTrue(result);
    }

    @Test
    void testHasUpdatesWhenMultipleFieldsAreNotNull() {
        PostUpdateDTO post = new PostUpdateDTO("Title", "Content", true);

        boolean result = post.hasUpdates();

        assertTrue(result);
    }

    @Test
    void testValidationWithValidData() {
        PostUpdateDTO post = new PostUpdateDTO("Valid Title", "Valid Content", true);

        Set<ConstraintViolation<PostUpdateDTO>> violations = validator.validate(post);

        assertTrue(violations.isEmpty());
    }

    @Test
    void testValidationWithNullValues() {
        PostUpdateDTO post = new PostUpdateDTO(null, null, null);

        Set<ConstraintViolation<PostUpdateDTO>> violations = validator.validate(post);

        assertTrue(violations.isEmpty());
    }

    @Test
    void testValidationWithEmptyTitle() {
        PostUpdateDTO post = new PostUpdateDTO();
        post.setTitle("");
        post.setContent("Valid Content");
        post.setPublished(true);

        Set<ConstraintViolation<PostUpdateDTO>> violations = validator.validate(post);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("title")));
    }

    @Test
    void testValidationWithEmptyContent() {
        PostUpdateDTO post = new PostUpdateDTO();
        post.setTitle("Valid Title");
        post.setContent("");
        post.setPublished(true);

        Set<ConstraintViolation<PostUpdateDTO>> violations = validator.validate(post);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("content")));
    }

    @Test
    void testValidationWithTitleTooLong() {
        PostUpdateDTO post = new PostUpdateDTO();
        post.setTitle("a".repeat(256));
        post.setContent("Valid Content");
        post.setPublished(true);

        Set<ConstraintViolation<PostUpdateDTO>> violations = validator.validate(post);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("title")));
    }

    @Test
    void testValidationWithContentTooLong() {
        PostUpdateDTO post = new PostUpdateDTO();
        post.setTitle("Valid Title");
        post.setContent("a".repeat(10001)); 
        post.setPublished(true);

        Set<ConstraintViolation<PostUpdateDTO>> violations = validator.validate(post);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("content")));
    }

    @Test
    void testValidationWithTitleAtMaxLength() {
        PostUpdateDTO post = new PostUpdateDTO();
        post.setTitle("a".repeat(255)); 
        post.setContent("Valid Content");
        post.setPublished(true);

        Set<ConstraintViolation<PostUpdateDTO>> violations = validator.validate(post);

        assertTrue(violations.isEmpty());
    }

    @Test
    void testValidationWithContentAtMaxLength() {
        PostUpdateDTO post = new PostUpdateDTO();
        post.setTitle("Valid Title");
        post.setContent("a".repeat(10000)); 
        post.setPublished(true);

        Set<ConstraintViolation<PostUpdateDTO>> violations = validator.validate(post);

        assertTrue(violations.isEmpty());
    }

    @Test
    void testValidationWithOnlyTitleSet() {
        PostUpdateDTO post = new PostUpdateDTO();
        post.setTitle("Valid Title");

        Set<ConstraintViolation<PostUpdateDTO>> violations = validator.validate(post);

        assertTrue(violations.isEmpty());
    }

    @Test
    void testValidationWithOnlyContentSet() {
        PostUpdateDTO post = new PostUpdateDTO();
        post.setContent("Valid Content");

        Set<ConstraintViolation<PostUpdateDTO>> violations = validator.validate(post);

        assertTrue(violations.isEmpty());
    }

    @Test
    void testValidationWithOnlyPublishedSet() {
        PostUpdateDTO post = new PostUpdateDTO();
        post.setPublished(true);

        Set<ConstraintViolation<PostUpdateDTO>> violations = validator.validate(post);

        assertTrue(violations.isEmpty());
    }
} 