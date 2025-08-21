package com.example.social_media_backend.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.social_media_backend.DTO.LoginRequestDTO;
import com.example.social_media_backend.DTO.LoginResponseDTO;
import com.example.social_media_backend.models.User;
import com.example.social_media_backend.repositories.UserRepository;
import com.example.social_media_backend.security.JwtUtil;

@Service
public class AuthService {
    
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElse(null);
        
        if (user == null) {
            return new LoginResponseDTO(null, null, null, "Invalid email or password");
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return new LoginResponseDTO(null, null, null, "Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return new LoginResponseDTO(
            token,
            user.getEmail(),
            user.getId(),
            "Login successful"
        );
    }

    public boolean validateToken(String token, String email) {
        return jwtUtil.validateToken(token, email);
    }

    public String extractEmailFromToken(String token) {
        return jwtUtil.extractUsername(token);
    }
} 