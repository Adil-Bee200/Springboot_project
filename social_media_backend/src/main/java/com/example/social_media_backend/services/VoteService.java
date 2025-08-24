package com.example.social_media_backend.services;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.social_media_backend.repositories.PostRepository;
import com.example.social_media_backend.repositories.UserRepository;
import com.example.social_media_backend.repositories.VoteRepository;
import com.example.social_media_backend.models.Post;
import com.example.social_media_backend.models.User;
import com.example.social_media_backend.models.Vote;

@Service
public class VoteService {
    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public VoteService(VoteRepository voteRepository, PostRepository postRepository, UserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Vote voteOnPost(String userEmail, Long postId, int direction) {
        if (direction != 1 && direction != -1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Direction must be 1 (upvote) or -1 (downvote)");
        }

        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));

        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Optional<Vote> existingVote = voteRepository.findByOwnerAndPost(user, post);

        if (existingVote.isPresent()) {
            Vote vote = existingVote.get();
            
            // If user is trying to vote in the same direction we will remove the vote
            if (vote.getDirection() == direction) {
                voteRepository.delete(vote);
                return null; // Vote removed
            } else {
                // If user is changing vote direction, update the existing vote
                vote.setDirection(direction);
                return voteRepository.save(vote);
            }
        } else {
            Vote newVote = new Vote(user, post, direction);
            
            return voteRepository.save(newVote);
        }
    }

    public void removeVote(String userEmail, Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));

        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Optional<Vote> existingVote = voteRepository.findByOwnerAndPost(user, post);
        
        if (existingVote.isPresent()) {
            voteRepository.delete(existingVote.get());
        }
    }

    public long getVoteCountForPost(Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
        
        return voteRepository.countByPost(post);
    }

    public boolean hasUserVotedOnPost(String userEmail, Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));

        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return voteRepository.findByOwnerAndPost(user, post).isPresent();
    }
}
