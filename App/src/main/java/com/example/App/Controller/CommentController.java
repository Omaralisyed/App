package com.example.App.Controller;
import com.example.App.Model.Comment;
import com.example.App.exception.CommentNotFoundException;
import com.example.App.exception.PostNotFoundException;
import com.example.App.exception.UserNotFoundException;
import com.example.App.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {



    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @PostMapping
    public ResponseEntity<String> createComment(@RequestBody Comment comment) {
        try {
            commentService.createComment(comment);
            return ResponseEntity.ok("Comment created successfully");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not exist");
        } catch (PostNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post does not exist");
        }
    }


    @GetMapping("/{commentId}")
    public ResponseEntity<?> getComment(@PathVariable Long commentId) {
        try {
            Comment comment = commentService.getComment(commentId);
            return ResponseEntity.ok(comment);
        } catch (CommentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment does not exist");
        }
    }


    @PatchMapping("/{commentId}")
    public ResponseEntity<String> editComment(@PathVariable Long commentId, @RequestBody Comment updatedComment) {
        try {
            commentService.editComment(commentId, updatedComment);
            return ResponseEntity.ok("Comment edited successfully");
        } catch (CommentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment does not exist");
        }
    }


    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        try {
            commentService.deleteComment(commentId);
            return ResponseEntity.ok("Comment deleted");
        } catch (CommentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment does not exist");
        }
    }
}
