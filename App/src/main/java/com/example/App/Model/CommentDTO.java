package com.example.App.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {
    private Long commentID;
    private String commentBody;
    private UserEntityDTO commentCreator;

    // Getters and setters
}