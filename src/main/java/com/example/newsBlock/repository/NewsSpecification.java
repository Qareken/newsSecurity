package com.example.newsBlock.repository;

import com.example.newsBlock.Exception.EnumException;
import com.example.newsBlock.entity.News;
import com.example.newsBlock.entity.NewsCategory;
import com.example.newsBlock.entity.Users;
import com.example.newsBlock.entity.enumurated.Category;
import com.example.newsBlock.web.model.NewsFilter;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public interface NewsSpecification {
    static Specification<News>withFilter(NewsFilter filter){
       return Specification.where(byAuthor(filter.getAuthorName()))
                .and(byCategory(filter.getCategoryName()));
    }

    static Specification<News> byCategory(String categoryName) {
        return (root, query, criteriaBuilder) ->{
            if (categoryName == null || categoryName.isEmpty()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true)); // always true = no filtering
            }

            // Присоедините таблицу категорий к новостям
            // Присоедините таблицу категорий к новостям
            Join<News, NewsCategory> newsCategoryJoin = root.join("categories");

            // Получите Enum значение Category из label
            Category categoryEnum;
            try {
                categoryEnum = Category.fromLabel(categoryName);
            } catch (EnumException e) {
                // Если Category не найден, верните false predicate
                return criteriaBuilder.isFalse(criteriaBuilder.literal(true));
            }

            // Фильтруйте новости по Enum категории
            return criteriaBuilder.equal(newsCategoryJoin.get("category"), categoryEnum);
        };

    }


    static Specification<News> byAuthor(String authorName) {
        return (root, query, criteriaBuilder) ->{
            if (authorName == null || authorName.isEmpty()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true)); // always true = no filtering
            }

            // Присоедините таблицу пользователей к новостям
            Join<News, Users> usersJoin = root.join("author");

            // Создайте предикаты для фильтрации по имени или фамилии автора
            Predicate firstNamePredicate = criteriaBuilder.like(usersJoin.get("firstName"), "%" + authorName + "%");
            Predicate lastNamePredicate = criteriaBuilder.like(usersJoin.get("lastName"), "%" + authorName + "%");

            // Комбинируйте предикаты с OR, чтобы фильтровать по имени или фамилии
            return criteriaBuilder.or(firstNamePredicate, lastNamePredicate);
    };
    }
}
