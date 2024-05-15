package com.example.newsBlock.web.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Data
@Getter
@Setter
@FieldNameConstants
public class NewsDTO {
    private Long id;
    private String title;
    private String content;
    private UserDTO author; // Including full UserDTO
    private int commentCount;
}