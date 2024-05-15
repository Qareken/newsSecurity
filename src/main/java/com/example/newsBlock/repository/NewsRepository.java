package com.example.newsBlock.repository;

import com.example.newsBlock.entity.News;
import com.example.newsBlock.entity.NewsCategory;
import com.example.newsBlock.entity.Users;
import com.example.newsBlock.entity.enumurated.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> , JpaSpecificationExecutor<News> {
    List<News> findAllByAuthor(Users author);
    List<News> findAllByCategoriesContains(NewsCategory newsCategory);
    News findNewsByAuthorAndAndContent(Users users, String content);
}
