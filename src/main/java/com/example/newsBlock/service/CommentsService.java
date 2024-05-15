package com.example.newsBlock.service;

import com.example.newsBlock.entity.Comments;
import com.example.newsBlock.entity.News;
import com.example.newsBlock.entity.Users;

import java.util.List;

public interface CommentsService {
    // Сохранение комментария
    Comments save(Comments comment);

    // Поиск комментария по идентификатору
    Comments findById(Long id);

    // Получение списка всех комментариев


    // Удаление комментария по идентификатору
    void deleteById(Long id, Long UserId);

    // Найти все комментарии для конкретной новости

    Comments update(Comments comments, Long commentId);


    // Другие методы, если необходимо, в соответствии с вашими требованиями
}