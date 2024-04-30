package com.example.App.service;



import com.example.App.Model.Post;
import com.example.App.exception.PostNotFoundException;
import com.example.App.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAllPostsOrderByCreatedAtDesc() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<Post> getAllPostsSortedByDate() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    public void createPost(Post post) {
        postRepository.save(post);
    }

    public Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post with ID " + postId + " not found"));
    }

    public void editPost(Long postId, Post updatedPost) {
        Post existingPost = getPost(postId);
        existingPost.setPostBody(updatedPost.getPostBody());
        // You can update other fields as needed
        postRepository.save(existingPost);
    }

    public void deletePost(Long postId) {
        Post existingPost = getPost(postId);
        postRepository.delete(existingPost);
    }


}