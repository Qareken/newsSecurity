package com.example.newsBlock.web.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.util.List;

@Data
@Getter
@Setter
@FieldNameConstants
public class UpsertNewsDTO {
    @NotNull(message = "Title is required")
    @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
    private String title;

    @NotNull(message = "Content is required")
    @Size(min = 10, message = "Content must be at least 10 characters")
    private String content;

    @NotNull(message = "Author information is required")
    @Valid // Это гарантирует, что поля внутри UserDTO также будут валидированы
    private UserDTO author;

    @NotNull(message = "At least one category is required")
    @Size(min = 1, message = "At least one category is required")
    @Valid // Это гарантирует, что поля внутри каждого NewsCategoryDTO также будут валидированы
    private List<NewsCategoryDTO> categories;
}
