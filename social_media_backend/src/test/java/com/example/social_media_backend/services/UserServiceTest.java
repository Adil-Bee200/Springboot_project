package com.example.social_media_backend.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.social_media_backend.DTO.UserRegisterDTO;
import com.example.social_media_backend.DTO.UserResponseDTO;
import com.example.social_media_backend.models.User;
import com.example.social_media_backend.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserService userService;

    private User testUser1;
    private User testUser2;
    private UserRegisterDTO testUserRegisterDTO;

    @BeforeEach
    void setUp() {
        testUser1 = new User();
        testUser1.setId(1L);
        testUser1.setEmail("test1@example.com");
        testUser1.setPassword("encodedPassword1");

        testUser2 = new User();
        testUser2.setId(2L);
        testUser2.setEmail("test2@example.com");
        testUser2.setPassword("encodedPassword2");

        testUserRegisterDTO = new UserRegisterDTO("newuser@example.com", "password123");
    }

    @Test
    void testGetUsersShouldReturnAllUsers() {
        List<User> users = Arrays.asList(testUser1, testUser2);
        when(userRepository.findAll()).thenReturn(users);     // setting mock behavior of repo

        List<UserResponseDTO> result = userService.getUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(testUser1.getId(), result.get(0).getId());
        assertEquals(testUser1.getEmail(), result.get(0).getEmail());
        assertEquals(testUser2.getId(), result.get(1).getId());
        assertEquals(testUser2.getEmail(), result.get(1).getEmail());

        verify(userRepository, times(1)).findAll(); // check that service acc called repo func
    }

    @Test
    void testGetUsersEmptyUsersDB() {
        when(userRepository.findAll()).thenReturn(Arrays.asList());

        List<UserResponseDTO> result = userService.getUsers();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser1));

        UserResponseDTO result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(testUser1.getId(), result.getId());
        assertEquals(testUser1.getEmail(), result.getEmail());

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testgetUserByIdNoUserExists() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        UserResponseDTO result = userService.getUserById(999L);

        assertNull(result);

        verify(userRepository, times(1)).findById(999L);
    }

    @Test
    void testGetUserByEmail() {
        when(userRepository.findByEmail("test1@example.com")).thenReturn(Optional.of(testUser1));

        UserResponseDTO result = userService.getUserByEmail("test1@example.com");

        assertNotNull(result);
        assertEquals(testUser1.getId(), result.getId());
        assertEquals(testUser1.getEmail(), result.getEmail());

        verify(userRepository, times(1)).findByEmail("test1@example.com");
    }

    @Test
    void testGetUserByEmailNoUserExists() {
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        UserResponseDTO result = userService.getUserByEmail("nonexistent@example.com");

        assertNull(result);

        verify(userRepository, times(1)).findByEmail("nonexistent@example.com");
    }

    @Test
    void testCreateUserShouldEncodePassword() {
        when(userRepository.existsByEmail(testUserRegisterDTO.getEmail())).thenReturn(false);
        when(bCryptPasswordEncoder.encode(testUserRegisterDTO.getPassword())).thenReturn("encodedPassword");
        
        User newUser = new User();
        newUser.setEmail(testUserRegisterDTO.getEmail());
        newUser.setPassword("encodedPassword");
        newUser.setId(3L);
        
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        userService.createUser(testUserRegisterDTO);

        verify(userRepository, times(1)).existsByEmail(testUserRegisterDTO.getEmail());
        verify(bCryptPasswordEncoder, times(1)).encode(testUserRegisterDTO.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testCreateUserSuccessful() {
        when(userRepository.existsByEmail(testUserRegisterDTO.getEmail())).thenReturn(false);
        when(bCryptPasswordEncoder.encode(testUserRegisterDTO.getPassword())).thenReturn("encodedPassword");
        
        User newUser = new User();
        newUser.setEmail(testUserRegisterDTO.getEmail());
        newUser.setPassword("encodedPassword");
        newUser.setId(3L);
        
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        UserResponseDTO result = userService.createUser(testUserRegisterDTO);

        assertNotNull(result);
        assertEquals(newUser.getId(), result.getId());
        assertEquals(newUser.getEmail(), result.getEmail());

        verify(userRepository, times(1)).existsByEmail(testUserRegisterDTO.getEmail());
        verify(bCryptPasswordEncoder, times(1)).encode(testUserRegisterDTO.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testCreateUserWhenEmailAlreadyExists() {
        when(userRepository.existsByEmail(testUserRegisterDTO.getEmail())).thenReturn(true);

        UserResponseDTO result = userService.createUser(testUserRegisterDTO);

        assertNull(result);

        verify(userRepository, times(1)).existsByEmail(testUserRegisterDTO.getEmail());
        verify(bCryptPasswordEncoder, never()).encode(anyString());
    }

} 