package com.example.social_media_backend.DTO;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

class UserRegisterDTOTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    void testConstructorEmailAndPassword() {
        UserRegisterDTO user = new UserRegisterDTO("test@example.com", "password123");
        assertEquals("test@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
    }

    @Test
    void testSetEmail() {
        UserRegisterDTO user = new UserRegisterDTO("old@example.com", "password123");
        user.setEmail("new@example.com");
        assertEquals("new@example.com", user.getEmail());
    }

    @Test
    void testSetPassword() {
        UserRegisterDTO user = new UserRegisterDTO("test@example.com", "oldpassword");
        user.setPassword("newpassword");
        assertEquals("newpassword", user.getPassword());
    }

    @Test
    void testValidationWithValidEmailAndPassword() {
        UserRegisterDTO user = new UserRegisterDTO("test@example.com", "password123");
        Set<ConstraintViolation<UserRegisterDTO>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testValidationWithInvalidEmail() {
        UserRegisterDTO user = new UserRegisterDTO("invalid-email", "password123");
        Set<ConstraintViolation<UserRegisterDTO>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    void testValidationWithEmptyEmail() {
        UserRegisterDTO user = new UserRegisterDTO("", "password123");
        Set<ConstraintViolation<UserRegisterDTO>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    void testValidationWithNullEmail() {
        UserRegisterDTO user = new UserRegisterDTO(null, "password123");
        Set<ConstraintViolation<UserRegisterDTO>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    void testValidationWithEmptyPassword() {
        UserRegisterDTO user = new UserRegisterDTO("test@example.com", "");
        Set<ConstraintViolation<UserRegisterDTO>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }

    @Test
    void testValidationWithNullPassword() {
        UserRegisterDTO user = new UserRegisterDTO("test@example.com", null);
        Set<ConstraintViolation<UserRegisterDTO>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }

} 