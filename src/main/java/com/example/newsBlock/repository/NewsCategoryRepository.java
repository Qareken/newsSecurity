package com.example.newsBlock.repository;

import com.example.newsBlock.entity.NewsCategory;
import com.example.newsBlock.entity.enumurated.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsCategoryRepository extends JpaRepository<NewsCategory, Long> {
    NewsCategory findNewsCategoryByCategory(Category category);
}
