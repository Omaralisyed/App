package com.example.App.service;

import com.example.App.Model.Comment;
import com.example.App.exception.CommentNotFoundException;
import com.example.App.exception.PostNotFoundException;
import com.example.App.exception.UserNotFoundException;
import com.example.App.repo.CommentRepository;
import com.example.App.repo.PostRepository;
import com.example.App.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository,
                          UserRepository userRepository,
                          PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }
//    public void createComment(Comment comment) {
//        if (!userRepository.existsById(comment.getUserID())) {
//            throw new UserNotFoundException("User does not exist");
//        }
//        if (!postRepository.existsById(comment.getPostID())) {
//            throw new PostNotFoundException("Post does not exist");
//        }
//        commentRepository.save(comment);
//    }
    public void createComment(Comment comment) {
    if (comment.getUser() == null || !userRepository.existsById(comment.getUser().getId())) {
        throw new UserNotFoundException("User does not exist");
    }
    if (comment.getPost() == null || !postRepository.existsById(comment.getPost().getId())) {
        throw new PostNotFoundException("Post does not exist");
    }
    commentRepository.save(comment);
}

    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException("Comment does not exist"));
    }

    public void editComment(Long commentId, Comment updatedComment) {
        Comment existingComment = getComment(commentId);
        existingComment.setCommentBody(updatedComment.getCommentBody());
        commentRepository.save(existingComment);
    }

    public void deleteComment(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new CommentNotFoundException("Comment does not exist");
        }
        commentRepository.deleteById(commentId);
    }


}
