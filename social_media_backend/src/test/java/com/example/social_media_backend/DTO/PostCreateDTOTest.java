package com.example.social_media_backend.DTO;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

class PostCreateDTOTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    void testConstructor() {
        PostCreateDTO post = new PostCreateDTO("Test Title", "Test Content", true);

        assertEquals("Test Title", post.getTitle());
        assertEquals("Test Content", post.getContent());
        assertTrue(post.getPublished());
    }

    @Test
    void testConstructorDefaultingPublishedToTrue() {
        PostCreateDTO post = new PostCreateDTO("Test Title", "Test Content", null);

        assertEquals("Test Title", post.getTitle());
        assertEquals("Test Content", post.getContent());
        assertTrue(post.getPublished());
    }

    @Test
    void testConstructorEmptyPost() {
        PostCreateDTO post = new PostCreateDTO();

        assertNotNull(post);
        assertNull(post.getTitle());
        assertNull(post.getContent());
        assertTrue(post.getPublished()); 
    }

    @Test
    void testSetTitle() {
        PostCreateDTO post = new PostCreateDTO();

        post.setTitle("New Title");

        assertEquals("New Title", post.getTitle());
    }

    @Test
    void testSetContent() {
        PostCreateDTO post = new PostCreateDTO();

        post.setContent("New Content");

        assertEquals("New Content", post.getContent());
    }

    @Test
    void testSetPublished() {
        PostCreateDTO post = new PostCreateDTO();

        post.setPublished(false);

        assertFalse(post.getPublished());
    }

    @Test
    void testValidationWithValidData() {
        PostCreateDTO post = new PostCreateDTO("Valid Title", "Valid Content", true);

        Set<ConstraintViolation<PostCreateDTO>> violations = validator.validate(post);

        assertTrue(violations.isEmpty());
    }

    @Test
    void testValidationWithEmptyTitle() {
        PostCreateDTO post = new PostCreateDTO();
        post.setTitle("");
        post.setContent("Valid Content");
        post.setPublished(true);

        Set<ConstraintViolation<PostCreateDTO>> violations = validator.validate(post);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("title")));
    }

    @Test
    void testValidationWithNullTitle() {
        PostCreateDTO post = new PostCreateDTO();
        post.setTitle(null);
        post.setContent("Valid Content");
        post.setPublished(true);

        Set<ConstraintViolation<PostCreateDTO>> violations = validator.validate(post);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("title")));
    }

    @Test
    void testValidationWithEmptyContent() {
        PostCreateDTO post = new PostCreateDTO();
        post.setTitle("Valid Title");
        post.setContent("");
        post.setPublished(true);

        Set<ConstraintViolation<PostCreateDTO>> violations = validator.validate(post);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("content")));
    }

    @Test
    void testValidationWithNullContent() {
        PostCreateDTO post = new PostCreateDTO();
        post.setTitle("Valid Title");
        post.setContent(null);
        post.setPublished(true);

        Set<ConstraintViolation<PostCreateDTO>> violations = validator.validate(post);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("content")));
    }

    @Test
    void testValidationWithTitleTooLong() {
        PostCreateDTO post = new PostCreateDTO();
        post.setTitle("a".repeat(256)); // exceeds max of 255
        post.setContent("Valid Content");
        post.setPublished(true);

        Set<ConstraintViolation<PostCreateDTO>> violations = validator.validate(post);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("title")));
    }

    @Test
    void testValidationWithContentTooLong() {
        PostCreateDTO post = new PostCreateDTO();
        post.setTitle("Valid Title");
        post.setContent("a".repeat(10001)); // 10001 characters exceeds max of 10000
        post.setPublished(true);

        Set<ConstraintViolation<PostCreateDTO>> violations = validator.validate(post);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("content")));
    }

    @Test
    void testValidationWithTitleAtMaxLength() {
        PostCreateDTO post = new PostCreateDTO();
        post.setTitle("a".repeat(255)); 
        post.setContent("Valid Content");
        post.setPublished(true);

        Set<ConstraintViolation<PostCreateDTO>> violations = validator.validate(post);

        assertTrue(violations.isEmpty());
    }

    @Test
    void testValidationWithContentAtMaxLength() {
        PostCreateDTO post = new PostCreateDTO();
        post.setTitle("Valid Title");
        post.setContent("a".repeat(10000)); 
        post.setPublished(true);

        Set<ConstraintViolation<PostCreateDTO>> violations = validator.validate(post);

        assertTrue(violations.isEmpty());
    }
} 