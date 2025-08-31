package com.example.social_media_backend.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.social_media_backend.DTO.PostCreateDTO;
import com.example.social_media_backend.DTO.PostResponseDTO;
import com.example.social_media_backend.DTO.PostUpdateDTO;
import com.example.social_media_backend.models.Post;
import com.example.social_media_backend.models.User;
import com.example.social_media_backend.repositories.PostRepository;
import com.example.social_media_backend.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostService postService;

    private User testUser;
    private Post testPost1;
    private Post testPost2;
    private PostResponseDTO testPostResponse1;
    private PostResponseDTO testPostResponse2;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@example.com");

        testPost1 = new Post();
        testPost1.setId(1L);
        testPost1.setTitle("Test Post 1");
        testPost1.setContent("Test content 1");
        testPost1.setPublished(true);
        testPost1.setCreatedAt(Instant.now());
        testPost1.setOwner(testUser);

        testPost2 = new Post();
        testPost2.setId(2L);
        testPost2.setTitle("Test Post 2");
        testPost2.setContent("Test content 2");
        testPost2.setPublished(false);
        testPost2.setCreatedAt(Instant.now());
        testPost2.setOwner(testUser);

        testPostResponse1 = new PostResponseDTO(1L, "Test Post 1", "Test content 1", true, testPost1.getCreatedAt(), 1L, "test@example.com");
        testPostResponse2 = new PostResponseDTO(2L, "Test Post 2", "Test content 2", false, testPost2.getCreatedAt(), 1L, "test@example.com");
    }

    @Test
    void testGetAllPosts() {
        List<Post> posts = Arrays.asList(testPost1, testPost2);
        when(postRepository.findAll()).thenReturn(posts);

        List<PostResponseDTO> result = postService.getAllPosts();

        assertEquals(2, result.size());
        assertEquals(testPostResponse1.getId(), result.get(0).getId());
        assertEquals(testPostResponse2.getId(), result.get(1).getId());
        verify(postRepository, times(1)).findAll();
    }

    @Test
    void testGetAllPostsWhenNoPostsExist() {
        when(postRepository.findAll()).thenReturn(Arrays.asList());

        List<PostResponseDTO> result = postService.getAllPosts();

        assertTrue(result.isEmpty());
        verify(postRepository, times(1)).findAll();
    }

    @Test
    void testGetPublishedPosts() {
        List<Post> publishedPosts = Arrays.asList(testPost1);
        when(postRepository.findByPublishedTrue()).thenReturn(publishedPosts);

        List<PostResponseDTO> result = postService.getPublishedPosts();

        assertEquals(1, result.size());
        assertEquals(testPostResponse1.getId(), result.get(0).getId());
        assertTrue(result.get(0).getPublished());
        verify(postRepository, times(1)).findByPublishedTrue();
    }

    @Test
    void testGetPublishedPostsWhenNoPublishedPostsExist() {
        when(postRepository.findByPublishedTrue()).thenReturn(Arrays.asList());

        List<PostResponseDTO> result = postService.getPublishedPosts();

        assertTrue(result.isEmpty());
        verify(postRepository, times(1)).findByPublishedTrue();
    }

    @Test
    void testGetPostById() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(testPost1));
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        PostResponseDTO result = postService.getPostById(1L);

        assertNotNull(result);
        assertEquals(testPostResponse1.getId(), result.getId());
        assertEquals(testPostResponse1.getTitle(), result.getTitle());
        verify(postRepository, times(1)).findById(1L);
    }

    @Test
    void testGetPostByIdWhenPostDoesNotExist() {
        when(postRepository.findById(999L)).thenReturn(Optional.empty());

        PostResponseDTO result = postService.getPostById(999L);

        assertNull(result);
        verify(postRepository, times(1)).findById(999L);
    }

    @Test
    void testSearchPostsByTitle() {
        List<Post> matchingPosts = Arrays.asList(testPost1);
        when(postRepository.findByTitleContainingIgnoreCase("Test")).thenReturn(matchingPosts);
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        List<PostResponseDTO> result = postService.searchPostsByTitle("Test");

        assertEquals(1, result.size());
        assertEquals(testPostResponse1.getId(), result.get(0).getId());
        verify(postRepository, times(1)).findByTitleContainingIgnoreCase("Test");
    }

    @Test
    void testSearchPostsByTitleWhenNoPostsFound() {
        when(postRepository.findByTitleContainingIgnoreCase("Nonexistent")).thenReturn(Arrays.asList());

        List<PostResponseDTO> result = postService.searchPostsByTitle("Nonexistent");

        assertTrue(result.isEmpty());
        verify(postRepository, times(1)).findByTitleContainingIgnoreCase("Nonexistent");
    }

    @Test
    void testCreatePost() {
        PostCreateDTO postCreateDTO = new PostCreateDTO("New Post", "New content", true);
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));
        when(postRepository.save(any(Post.class))).thenReturn(testPost1);
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        PostResponseDTO result = postService.createPost(postCreateDTO, "test@example.com");

        assertNotNull(result);
        assertEquals(testPostResponse1.getId(), result.getId());
        verify(userRepository, times(1)).findByEmail("test@example.com");
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void testCreatePostWhenUserDoesNotExist() {
        PostCreateDTO postCreateDTO = new PostCreateDTO("New Post", "New content", true);
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        PostResponseDTO result = postService.createPost(postCreateDTO, "nonexistent@example.com");

        assertNull(result);
        verify(userRepository, times(1)).findByEmail("nonexistent@example.com");
        verify(postRepository, never()).save(any(Post.class));
    }

    @Test
    void testUpdatePost() {
        PostUpdateDTO postUpdateDTO = new PostUpdateDTO("Updated Title", "Updated content", false);
        when(postRepository.findById(1L)).thenReturn(Optional.of(testPost1));
        when(postRepository.save(any(Post.class))).thenReturn(testPost1);
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        PostResponseDTO result = postService.updatePost(1L, postUpdateDTO);

        assertNotNull(result);
        assertEquals(testPostResponse1.getId(), result.getId());
        verify(postRepository, times(1)).findById(1L);
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void testUpdatePostWhenPostDoesNotExist() {
        PostUpdateDTO postUpdateDTO = new PostUpdateDTO("Updated Title", "Updated content", false);
        when(postRepository.findById(999L)).thenReturn(Optional.empty());

        PostResponseDTO result = postService.updatePost(999L, postUpdateDTO);

        assertNull(result);
        verify(postRepository, times(1)).findById(999L);
        verify(postRepository, never()).save(any(Post.class));
    }

    @Test
    void testUpdatePostWithPartialUpdates() {
        PostUpdateDTO postUpdateDTO = new PostUpdateDTO("Updated Title", null, null);
        when(postRepository.findById(1L)).thenReturn(Optional.of(testPost1));
        when(postRepository.save(any(Post.class))).thenReturn(testPost1);
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        PostResponseDTO result = postService.updatePost(1L, postUpdateDTO);

        assertNotNull(result);
        verify(postRepository, times(1)).findById(1L);
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void testDeletePost() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(testPost1));

        boolean result = postService.deletePost(1L);

        assertTrue(result);
        verify(postRepository, times(1)).findById(1L);
        verify(postRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeletePostWhenPostDoesNotExist() {
        when(postRepository.findById(999L)).thenReturn(Optional.empty());

        boolean result = postService.deletePost(999L);

        assertFalse(result);
        verify(postRepository, times(1)).findById(999L);
        verify(postRepository, never()).deleteById(anyLong());
    }

    @Test
    void testTogglePostPublishedStatus() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(testPost1));
        when(postRepository.save(any(Post.class))).thenReturn(testPost1);

        boolean result = postService.togglePostPublishedStatus(1L);

        assertTrue(result);
        verify(postRepository, times(1)).findById(1L);
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void testTogglePostPublishedStatusWhenPostDoesNotExist() {
        when(postRepository.findById(999L)).thenReturn(Optional.empty());

        boolean result = postService.togglePostPublishedStatus(999L);

        assertFalse(result);
        verify(postRepository, times(1)).findById(999L);
        verify(postRepository, never()).save(any(Post.class));
    }

    @Test
    void testConvertToResponseDTO() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(testPost1));
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        PostResponseDTO result = postService.getPostById(1L);

        assertNotNull(result);
        assertEquals(testPostResponse1.getId(), result.getId());
        assertEquals(testPostResponse1.getTitle(), result.getTitle());
        assertEquals(testPostResponse1.getContent(), result.getContent());
        assertEquals(testPostResponse1.getPublished(), result.getPublished());
        assertEquals(testPostResponse1.getOwnerId(), result.getOwnerId());
        assertEquals(testPostResponse1.getOwnerEmail(), result.getOwnerEmail());
    }

    @Test
    void testConvertToResponseDTOWhenOwnerDoesNotExist() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(testPost1));
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        PostResponseDTO result = postService.getPostById(1L);

        assertNotNull(result);
        assertEquals("Unknown User", result.getOwnerEmail());
    }
} 