package com.example.social_media_backend.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import com.example.social_media_backend.models.User;
import com.example.social_media_backend.services.UserService;

import java.util.List;

import org.springframework.http.HttpStatus;

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
    public List<User> getAllUsers() {
        return userService.getUsers(); 
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public User getUser(@PathVariable Long id) {
        User user = userService.getUser(id);

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return userService.getUser(id);
    }
}
