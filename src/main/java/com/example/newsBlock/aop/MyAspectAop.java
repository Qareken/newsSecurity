package com.example.newsBlock.aop;

import com.example.newsBlock.Exception.EntityNotFoundException;
import com.example.newsBlock.Exception.ValidException;
import com.example.newsBlock.entity.News;
import com.example.newsBlock.entity.Users;
import com.example.newsBlock.entity.enumurated.RoleType;
import com.example.newsBlock.security.SecurityService;
import com.example.newsBlock.service.impl.CommentsServiceImpl;
import com.example.newsBlock.service.impl.NewsServiceImpl;
import com.example.newsBlock.service.impl.RefreshTokenService;
import com.example.newsBlock.service.impl.UsersServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import org.springframework.stereotype.Component;

import java.util.List;

import java.util.Optional;

@Aspect
@RequiredArgsConstructor
@Component
@Slf4j
public class MyAspectAop {

    private final UsersServiceImpl usersService;
    private final SecurityService securityService;
    private final RefreshTokenService refreshTokenService;

    @Pointcut(value = "execution(* com.example.newsBlock.service.impl.UsersServiceImpl.update(com.example.newsBlock.entity.Users)) ")
    public void update() {

    }

    @Pointcut(value = "execution(* com.example.newsBlock.service.impl.UsersServiceImpl.delete(com.example.newsBlock.entity.Users)) ")
    public void delete() {

    }

    @Around(value = "update()  && args(users)", argNames = "joinPoint, users")
    public Users checkForUpdate(ProceedingJoinPoint joinPoint, Users users) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        if (methodName.contains("update")) {
            securityService.isUser(users);
        }
        return (Users) joinPoint.proceed();
    }

    @Around(value = "delete()&&args(users)", argNames = "joinPoint,users")
    public Users checkForDelete(ProceedingJoinPoint joinPoint, Users users) throws Throwable {
        securityService.isUser(users);
        var existedUser = usersService.findByEmail(users.getEmail());
        if (existedUser == null) {
            throw new ValidException("Access denied");
        }
        refreshTokenService.deleteByUserId(existedUser.getId());
        return (Users) joinPoint.proceed();

    }

    @Pointcut("execution(* com.example.newsBlock.service.impl.UsersServiceImpl.findAll(..))")
    public void findAllPointcut() {
        // Пустое тело метода, здесь просто определяется pointcut
    }

    @Around("findAllPointcut()")
    @SneakyThrows
    public List<Users> checkFindAllUsers(ProceedingJoinPoint joinPoint) {

        if (securityService.isAdmin()) {
            log.info("admin");
            return (List<Users>) joinPoint.proceed();
        }
        throw new ValidException("Access denied");
    }

    @Around(value = "execution(* com.example.newsBlock.service.impl.UsersServiceImpl.findById(..)) && args(id)", argNames = "joinPoint, id")
    public Optional<Users> findById(ProceedingJoinPoint joinPoint, Long id) throws Throwable {
        if (securityService.isUserRole() && !securityService.getCurrentId().equals(id)) {
            throw new ValidException("Access denied");
        }
        return (Optional<Users>) joinPoint.proceed();
    }

}
