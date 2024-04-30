package com.example.App.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PostDTO {
    private Long postID;
    private String postBody;
    private Date date;
    private List<CommentDTO> comments;

    // Getters and setters
}