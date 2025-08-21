package com.example.social_media_backend.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.social_media_backend.DTO.UserRegisterDTO;
import com.example.social_media_backend.DTO.UserResponseDTO;
import com.example.social_media_backend.services.UserService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;




@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponseDTO> getAllUsers() {
        return userService.getUsers(); 
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRegisterDTO user) {
        UserResponseDTO createdUser = userService.createUser(user);
        
        if (createdUser == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("User with this email already exists");
        }
        
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/byId/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public UserResponseDTO getUser(@PathVariable Long id) {
        UserResponseDTO user = userService.getUserById(id);

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return user;
    }

    @GetMapping("/byEmail/{email}")
    @ResponseStatus(HttpStatus.FOUND)
    public UserResponseDTO getUserByEmail(@PathVariable String email) {
        UserResponseDTO user = userService.getUserByEmail(email);

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return user;
    }


}
