package com.example.newsBlock.aop;

import com.example.newsBlock.Exception.ValidException;
import com.example.newsBlock.entity.NewsCategory;
import com.example.newsBlock.security.SecurityService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@RequiredArgsConstructor
@Component
@Slf4j
public class CategoriesAspectAop {
    private final SecurityService securityService;
    @Pointcut("execution(* com.example.newsBlock.service.impl.NewsCategoryServiceImpl.*(..))&&args(newsCategory,..)")
    public void updateOrDelete(NewsCategory newsCategory){
    }
    @Around("updateOrDelete(newsCategory)")
    @SneakyThrows
    public NewsCategory methodSaveOrUpdateOrDelete(ProceedingJoinPoint joinPoint, NewsCategory newsCategory){
        if(securityService.isUserRole()){
            throw new ValidException("Access denied");
        }
        return (NewsCategory) joinPoint.proceed();
    }
}
