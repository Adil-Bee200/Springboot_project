package com.example.social_media_backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.social_media_backend.models.Vote;
import com.example.social_media_backend.services.VoteService;
import com.example.social_media_backend.DTO.VoteRequestDTO;
import com.example.social_media_backend.DTO.VoteResponseDTO;

@RestController
@RequestMapping("/votes")
public class VoteController {
    
    private final VoteService voteService;
    
    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }
    
    @PostMapping("/{postId}")
    public ResponseEntity<?> voteOnPost(@PathVariable Long postId, @RequestBody VoteRequestDTO voteRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
          
        try {
            Vote result = voteService.voteOnPost(userEmail, postId, voteRequest.getDirection());
            
            if (result == null) {
                return ResponseEntity.ok().body("Vote removed successfully");
            } else {
                VoteResponseDTO responseDTO = new VoteResponseDTO(
                    result.getOwnerId(),
                    result.getPostId(),
                    result.getDirection(),
                    result.getCreatedAt()
                );
                return ResponseEntity.ok(responseDTO);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> removeVote(@PathVariable Long postId) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        
        try {
            voteService.removeVote(userEmail, postId);
            return ResponseEntity.ok().body("Vote removed successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/{postId}/count")
    public ResponseEntity<Long> getVoteCount(@PathVariable Long postId) {
        try {
            long count = voteService.getVoteCountForPost(postId);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/{postId}/hasVoted")
    public ResponseEntity<Boolean> hasUserVoted(@PathVariable Long postId) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        
        try {
            boolean hasVoted = voteService.hasUserVotedOnPost(userEmail, postId);
            return ResponseEntity.ok(hasVoted);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
