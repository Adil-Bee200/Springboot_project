package com.example.social_media_backend.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.social_media_backend.DTO.PostCreateDTO;
import com.example.social_media_backend.DTO.PostResponseDTO;
import com.example.social_media_backend.DTO.PostUpdateDTO;
import com.example.social_media_backend.services.PostService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<PostResponseDTO> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/published")
    @ResponseStatus(HttpStatus.OK)
    public List<PostResponseDTO> getPublishedPosts() {
        return postService.getPublishedPosts();
    }

    @GetMapping("/published/ordered")
    @ResponseStatus(HttpStatus.OK)
    public List<PostResponseDTO> getPublishedPostsOrdered() {
        return postService.getPublishedPostsOrderByCreatedAt();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostResponseDTO getPostById(@PathVariable Long id) {
        PostResponseDTO post = postService.getPostById(id);

        if (post == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }

        return post;
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<PostResponseDTO> searchPostsByTitle(@RequestParam String title) {
        return postService.searchPostsByTitle(title);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponseDTO createPost(@Valid @RequestBody PostCreateDTO postCreateDTO) {
        PostResponseDTO createdPost = postService.createPost(postCreateDTO);
        return createdPost;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostResponseDTO updatePost(@PathVariable Long id, @Valid @RequestBody PostUpdateDTO postUpdateDTO) {
        PostResponseDTO updatedPost = postService.updatePost(id, postUpdateDTO);

        if (updatedPost == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }

        return updatedPost;
    }

    @PutMapping("/{id}/toggle-published")
    @ResponseStatus(HttpStatus.OK)
    public PostResponseDTO togglePostPublishedStatus(@PathVariable Long id) {
        boolean success = postService.togglePostPublishedStatus(id);

        if (!success) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }

        return postService.getPostById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable Long id) {
        boolean success = postService.deletePost(id);

        if (!success) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
    }
} 