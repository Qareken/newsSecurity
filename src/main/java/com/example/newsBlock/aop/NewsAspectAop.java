package com.example.newsBlock.aop;

import com.example.newsBlock.Exception.ValidException;

import com.example.newsBlock.entity.enumurated.RoleType;
import com.example.newsBlock.security.AppUserDetails;

import com.example.newsBlock.service.impl.NewsServiceImpl;

import com.example.newsBlock.web.model.NewsDetailDTO;
import com.example.newsBlock.web.model.UpsertNewsDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;



@Aspect
@RequiredArgsConstructor
@Component
@Slf4j
public class NewsAspectAop {
    private final NewsServiceImpl newsService;
    @SuppressWarnings("unchecked")
    @Around(value = "execution(* com.example.newsBlock.restController.NewsController.createNews(..))&& args(newsDTO, userDetails,..)", argNames = "joinPoint,newsDTO,userDetails")
    public ResponseEntity<NewsDetailDTO> create(ProceedingJoinPoint joinPoint, UpsertNewsDTO newsDTO, UserDetails userDetails) throws Throwable {
        if (userDetails instanceof AppUserDetails user) {
            if (user.getAuthorities().contains(new SimpleGrantedAuthority(RoleType.ROLE_USER.name()))) {
                throw new ValidException("Access denied");
            }
            return (ResponseEntity<NewsDetailDTO>) joinPoint.proceed();
        }
        throw new ValidException("Access denied");

    }

    @SuppressWarnings("unchecked")
    @Around(value = "execution(* com.example.newsBlock.restController.NewsController.updateNews(..))&&args(newsDto, newsId,userDetails )", argNames = "joinPoint,newsDto,newsId,userDetails")
    public ResponseEntity<NewsDetailDTO> update(ProceedingJoinPoint joinPoint, UpsertNewsDTO newsDto, Long newsId, UserDetails userDetails) throws Throwable {
        if (userDetails instanceof AppUserDetails user) {
            if (user.getAuthorities().contains(new SimpleGrantedAuthority(RoleType.ROLE_USER.name())) && user.getId() != newsId) {
                throw new ValidException("Access denied");
            }
            return (ResponseEntity<NewsDetailDTO>) joinPoint.proceed();
        }
        throw new ValidException("Access denied");
    }

    @SuppressWarnings("unchecked")
    @Around(value = "execution(* com.example.newsBlock.restController.NewsController.deleteNews(..))&&args(newsId, userDetails)", argNames = "joinPoint,newsId,userDetails")
    public ResponseEntity<?> delete(ProceedingJoinPoint joinPoint, Long newsId,UserDetails userDetails) throws Throwable {
        if(userDetails instanceof AppUserDetails user){
            if(user.getAuthorities().contains(new SimpleGrantedAuthority(RoleType.ROLE_USER.name()))&&!user.getId().equals(newsService.findById(newsId).getAuthor().getId())){
                    throw new ValidException("Access denied");
            }
            return (ResponseEntity<?>) joinPoint.proceed();
        }
        throw new ValidException("Access denied");
    }


}
