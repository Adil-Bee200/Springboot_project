package com.example.social_media_backend.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.social_media_backend.DTO.UserRegisterDTO;
import com.example.social_media_backend.DTO.UserResponseDTO;
import com.example.social_media_backend.models.User;
import com.example.social_media_backend.repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    
    public List<UserResponseDTO> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponseDTO> userResponses = new ArrayList<>();

        for (User u : users) {
            UserResponseDTO user = new UserResponseDTO(u.getId(), u.getEmail());
            userResponses.add(user);
        }

        return userResponses;
    }

    public UserResponseDTO getUserById(Long id) {
        User rawUser = userRepository.findById(id).orElse(null);

        if (rawUser == null) {
            return null;
        } else {
            return new UserResponseDTO(rawUser.getId(), rawUser.getEmail());
        }
    }

    public UserResponseDTO getUserByEmail(String email) {
        User rawUser = userRepository.findByEmail(email).orElse(null);

        if (rawUser == null) {
            return null;
        } else {
            return new UserResponseDTO(rawUser.getId(), rawUser.getEmail());
        }
    }

    public UserResponseDTO createUser(UserRegisterDTO user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return null; 
        }
        
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(encodedPassword);

        User createdUser = userRepository.save(newUser);

        return new UserResponseDTO(createdUser.getId(), createdUser.getEmail());
    }
}
