package com.example.newsBlock.web.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.util.List;

@Data
@Getter
@Setter
@FieldNameConstants
public class NewsDetailDTO {
    private Long id;
    private String title;
    private String content;
    private UserDTO author;
    private List<CommentDTO> comments;
}
