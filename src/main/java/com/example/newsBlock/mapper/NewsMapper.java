package com.example.newsBlock.mapper;

import com.example.newsBlock.entity.News;
import com.example.newsBlock.web.model.NewsDTO;
import com.example.newsBlock.web.model.NewsDetailDTO;
import com.example.newsBlock.web.model.UpsertNewsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, NewsCategoryMapper.class, CommentMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NewsMapper {
    @Mapping(target = "commentCount", expression = "java(news.getComments().size())")
    NewsDTO toDto(News news);

    @Mapping(target = "author", source = "author")
    News toEntity(UpsertNewsDTO upsertNewsDTO);

    NewsDetailDTO toDetailDto(News news);
    List<NewsDTO> listNewsAllDto(List<NewsDTO> newsDTO);
    List<NewsDTO> toNewsDTOList(List<News> newsList);

}
