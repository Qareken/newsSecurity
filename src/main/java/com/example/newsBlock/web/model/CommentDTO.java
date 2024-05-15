package com.example.newsBlock.web.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Data
@Getter
@Setter
@FieldNameConstants
public class CommentDTO {

    private Long id;

    private String content;
    private UserDTO users;
    private Long newsId;
    // Other fields as required
}