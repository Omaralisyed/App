package com.example.App.Controller;

import com.example.App.Model.*;
import com.example.App.exception.PostNotFoundException;
import com.example.App.exception.UserNotFoundException;
import com.example.App.service.PostService;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PostController {



    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> postDTOs = postService.getAllPostsSortedByDate().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(postDTOs);
    }

    private PostDTO convertToDto(Post post) {
        PostDTO dto = new PostDTO();
        dto.setPostID(post.getId());
        dto.setPostBody(post.getPostBody());
        dto.setDate(post.getCreatedAt());
        // Convert comments to DTOs here if needed
        return dto;
    }

    @PostMapping("/post")
    public ResponseEntity<String> createPost(@RequestBody Post post) {
        try {
            postService.createPost(post);
            return ResponseEntity.ok("Post created successfully");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }


    @GetMapping("/post/{postId}")
    public ResponseEntity<?> getPost(@PathVariable Long postId) {
        try {
            Post post = postService.getPost(postId);
            return ResponseEntity.ok(post);
        } catch (PostNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
        }
    }


    @PatchMapping("/post/{postId}")
    public ResponseEntity<String> editPost(@PathVariable Long postId, @RequestBody Post post) {
        try {
            postService.editPost(postId, post);
            return ResponseEntity.ok("Post edited successfully");
        } catch (PostNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
        }
    }


    @DeleteMapping("/post/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        try {
            postService.deletePost(postId);
            return ResponseEntity.ok("Post deleted");
        } catch (PostNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
        }
    }
}


