package com.example.newsBlock.mapper;

import com.example.newsBlock.entity.Comments;
import com.example.newsBlock.web.model.CommentDTO;
import com.example.newsBlock.web.model.UpsertCommentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, NewsMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {
    @Mapping(target = "newsId", source = "comment.news.id")
    CommentDTO toDto(Comments comment);

    @Mapping(target = "users", source = "user")
    @Mapping(target = "news", source = "news")
    Comments toEntity(UpsertCommentDTO upsertCommentDTO);
}

