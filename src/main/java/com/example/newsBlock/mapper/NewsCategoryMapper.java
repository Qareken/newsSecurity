package com.example.newsBlock.mapper;

import com.example.newsBlock.Exception.CategoryException;
import com.example.newsBlock.entity.NewsCategory;
import com.example.newsBlock.entity.enumurated.Category;
import com.example.newsBlock.web.model.NewsCategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
public interface NewsCategoryMapper {

    default Category mapCategoryNameToCategory(String categoryName) {
        for (Category category : Category.values()) {
            if (category.getLabel().equals(categoryName)) {
                return category;
            }
        }
        throw new CategoryException("Неверное имя категории: " + categoryName);
    }

    default String mapCategoryToCategoryName(Category category) {
        return category == null ? null : category.getLabel();
    }

    @Mapping(target = "category", expression = "java(mapCategoryNameToCategory(newsCategoryDTO.getCategoryName()))")
    @Mapping(target = "news", ignore = true)
    NewsCategory toEntity(NewsCategoryDTO newsCategoryDTO);

    @Mapping(target = "categoryName", expression = "java(mapCategoryToCategoryName(newsCategory.getCategory()))")
    NewsCategoryDTO toDto(NewsCategory newsCategory);

}
