package com.example.newsBlock.service;

import com.example.newsBlock.entity.NewsCategory;
import com.example.newsBlock.entity.enumurated.Category;

import java.util.List;

public interface NewsCategoryService {
    // Сохранение категории новостей
    NewsCategory save(NewsCategory category);

    // Поиск категории по идентификатору
    NewsCategory findById(Long id);
    NewsCategory findByCategory(String category);

    // Получение списка всех категорий

    NewsCategory update(NewsCategory newsCategory);
    void delete(NewsCategory newsCategory);
    // Другие методы, если необходимо, в соответствии с вашими требованиями
}
