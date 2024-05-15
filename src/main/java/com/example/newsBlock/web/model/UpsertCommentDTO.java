package com.example.newsBlock.web.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Data
@Getter
@Setter
@FieldNameConstants
public class UpsertCommentDTO {
    @NotNull(message = "Title must not be null")
    @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
    private String content;
    private UserDTO user;
    @NotNull(message = "News need include")
    private NewsDTO news; // А также этого
    // ... остальные поля
}
