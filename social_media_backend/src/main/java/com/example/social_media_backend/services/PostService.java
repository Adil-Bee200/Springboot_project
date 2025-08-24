package com.example.social_media_backend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.social_media_backend.DTO.PostCreateDTO;
import com.example.social_media_backend.DTO.PostResponseDTO;
import com.example.social_media_backend.DTO.PostUpdateDTO;
import com.example.social_media_backend.models.Post;
import com.example.social_media_backend.models.User;
import com.example.social_media_backend.repositories.PostRepository;
import com.example.social_media_backend.repositories.UserRepository;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }
    
    public List<PostResponseDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostResponseDTO> postResponses = new ArrayList<>();

        for (Post post : posts) {
            PostResponseDTO postResponse = convertToResponseDTO(post);
            postResponses.add(postResponse);
        }

        return postResponses;
    }

    public List<PostResponseDTO> getPublishedPosts() {
        List<Post> posts = postRepository.findByPublishedTrue();
        List<PostResponseDTO> postResponses = new ArrayList<>();

        for (Post post : posts) {
            PostResponseDTO postResponse = convertToResponseDTO(post);
            postResponses.add(postResponse);
        }

        return postResponses;
    }

    public List<PostResponseDTO> getPublishedPostsOrderByCreatedAt() {
        List<Post> posts = postRepository.findAllPublishedPostsOrderByCreatedAtDesc();
        List<PostResponseDTO> postResponses = new ArrayList<>();

        for (Post post : posts) {
            PostResponseDTO postResponse = convertToResponseDTO(post);
            postResponses.add(postResponse);
        }

        return postResponses;
    }

    public PostResponseDTO getPostById(Long id) {
        Optional<Post> post = postRepository.findById(id);

        if (post.isPresent()) {
            return convertToResponseDTO(post.get());
        } else {
            return null;
        }
    }

    public List<PostResponseDTO> searchPostsByTitle(String title) {
        List<Post> posts = postRepository.findByTitleContainingIgnoreCase(title);
        List<PostResponseDTO> postResponses = new ArrayList<>();

        for (Post post : posts) {
            PostResponseDTO postResponse = convertToResponseDTO(post);
            postResponses.add(postResponse);
        }

        return postResponses;
    }

    public PostResponseDTO createPost(PostCreateDTO postCreateDTO, String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElse(null);
        if (user == null) {
            return null; 
        }

        Post newPost = new Post();
        newPost.setTitle(postCreateDTO.getTitle());
        newPost.setContent(postCreateDTO.getContent());
        newPost.setPublished(postCreateDTO.getPublished());
        newPost.setOwner(user);

        Post createdPost = postRepository.save(newPost);

        return convertToResponseDTO(createdPost);
    }

    public PostResponseDTO updatePost(Long id, PostUpdateDTO postUpdateDTO) {
        Optional<Post> existingPost = postRepository.findById(id);

        if (existingPost.isPresent()) {
            Post post = existingPost.get();
            
            if (postUpdateDTO.getTitle() != null) {
                post.setTitle(postUpdateDTO.getTitle());
            }
            if (postUpdateDTO.getContent() != null) {
                post.setContent(postUpdateDTO.getContent());
            }
            if (postUpdateDTO.getPublished() != null) {
                post.setPublished(postUpdateDTO.getPublished());
            }

            Post updatedPost = postRepository.save(post);

            return convertToResponseDTO(updatedPost);
        } else {
            return null;
        }
    }

    public boolean deletePost(Long id) {
        Optional<Post> existingPost = postRepository.findById(id);

        if (existingPost.isPresent()) {
            postRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public boolean togglePostPublishedStatus(Long id) {
        Optional<Post> existingPost = postRepository.findById(id);

        if (existingPost.isPresent()) {
            Post post = existingPost.get();
            post.setPublished(!post.getPublished());
            postRepository.save(post);
            return true;
        } else {
            return false;
        }
    }

    private PostResponseDTO convertToResponseDTO(Post post) {
        Long ownerId = post.getOwnerId();
        
        String ownerEmail = "Unknown User";
        if (ownerId != null) {
            Optional<User> user = userRepository.findById(ownerId);
            ownerEmail = user.map(User::getEmail).orElse("Unknown User");
        }
        
        return new PostResponseDTO(
            post.getId(),
            post.getTitle(),
            post.getContent(),
            post.getPublished(),
            post.getCreatedAt(),
            ownerId,
            ownerEmail
        );
    }
} 