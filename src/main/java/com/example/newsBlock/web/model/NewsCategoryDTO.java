package com.example.newsBlock.web.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Data
@Getter
@Setter
@FieldNameConstants
public class NewsCategoryDTO {
    private Long id;
    @NotNull(message = "Category name is required")
    private String categoryName;
    // Other fields as required
}