package com.example.newsBlock.repository;

import com.example.newsBlock.entity.Comments;
import com.example.newsBlock.entity.News;
import com.example.newsBlock.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {
    List<Comments> findAllByNews(News news);

    List<Comments> findAllByUsers(Users user);
    Comments findCommentsByContentAndNews(String content, Users users);

}
