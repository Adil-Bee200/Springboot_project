package com.example.social_media_backend.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import com.example.social_media_backend.DTO.UserRegisterDTO;
import com.example.social_media_backend.DTO.UserResponseDTO;
import com.example.social_media_backend.services.UserService;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserResponseDTO testUser1;
    private UserResponseDTO testUser2;
    private UserRegisterDTO testUserRegisterDTO;

    @BeforeEach
    void setUp() {
        testUser1 = new UserResponseDTO(1L, "test1@example.com");
        testUser2 = new UserResponseDTO(2L, "test2@example.com");
        testUserRegisterDTO = new UserRegisterDTO("newuser@example.com", "password123");
    }

    @Test
    void testGetAllUsers() {
        List<UserResponseDTO> users = Arrays.asList(testUser1, testUser2);
        when(userService.getUsers()).thenReturn(users);

        List<UserResponseDTO> result = userController.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(testUser1.getId(), result.get(0).getId());
        assertEquals(testUser1.getEmail(), result.get(0).getEmail());
        assertEquals(testUser2.getId(), result.get(1).getId());
        assertEquals(testUser2.getEmail(), result.get(1).getEmail());

        verify(userService, times(1)).getUsers();
    }

    @Test
    void testGetAllUsersWhenNoUsers() {
        when(userService.getUsers()).thenReturn(Arrays.asList());

        List<UserResponseDTO> result = userController.getAllUsers();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(userService, times(1)).getUsers();
    }

    @Test
    void testCreateUserSuccessful() {
        UserResponseDTO createdUser = new UserResponseDTO(3L, testUserRegisterDTO.getEmail());
        when(userService.createUser(testUserRegisterDTO)).thenReturn(createdUser);

        ResponseEntity<?> result = userController.createUser(testUserRegisterDTO);

        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(createdUser, result.getBody());

        verify(userService, times(1)).createUser(testUserRegisterDTO);
    }

    @Test
    void testCreateUserWhenEmailAlreadyExists() {
        when(userService.createUser(testUserRegisterDTO)).thenReturn(null);

        ResponseEntity<?> result = userController.createUser(testUserRegisterDTO);

        assertNotNull(result);
        assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
        assertEquals("User with this email already exists", result.getBody());

        verify(userService, times(1)).createUser(testUserRegisterDTO);
    }

    @Test
    void testGetUserById() {
        when(userService.getUserById(1L)).thenReturn(testUser1);

        UserResponseDTO result = userController.getUser(1L);

        assertNotNull(result);
        assertEquals(testUser1.getId(), result.getId());
        assertEquals(testUser1.getEmail(), result.getEmail());

        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void testGetUserByIdButNoUser() {
        when(userService.getUserById(999L)).thenReturn(null);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userController.getUser(999L);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

        verify(userService, times(1)).getUserById(999L);
    }

    @Test
    void testGetUserByEmail() {
        when(userService.getUserByEmail("test1@example.com")).thenReturn(testUser1);

        UserResponseDTO result = userController.getUserByEmail("test1@example.com");

        assertNotNull(result);
        assertEquals(testUser1.getId(), result.getId());
        assertEquals(testUser1.getEmail(), result.getEmail());

        verify(userService, times(1)).getUserByEmail("test1@example.com");
    }

    @Test
    void testGetUserByEmailButNoUser() {
        when(userService.getUserByEmail("nonexistent@example.com")).thenReturn(null);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userController.getUserByEmail("nonexistent@example.com");
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

        verify(userService, times(1)).getUserByEmail("nonexistent@example.com");
    }
} 