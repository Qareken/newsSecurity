package com.example.newsBlock.service;

import com.example.newsBlock.entity.News;
import com.example.newsBlock.entity.NewsCategory;
import com.example.newsBlock.entity.Users;
import com.example.newsBlock.web.model.NewsDTO;
import com.example.newsBlock.web.model.NewsFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface NewsService {
    // Сохранение новости
    News save(News news);

    // Поиск новости по идентификатору
    News findById(Long id);

    // Получение списка всех новостей
    Page<NewsDTO> findAll(Pageable pageable);

    // Удаление новости по идентификатору
    void deleteById(Long id, Long userId);
    Page<NewsDTO> findByFilter(Pageable pageable, NewsFilter newsFilter);

    News update(News news,Long newsId);

    // Другие методы, если необходимо, в соответствии с вашими требованиями
}