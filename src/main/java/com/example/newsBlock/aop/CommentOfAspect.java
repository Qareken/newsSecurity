package com.example.newsBlock.aop;

import com.example.newsBlock.Exception.ValidException;
import com.example.newsBlock.entity.enumurated.RoleType;
import com.example.newsBlock.repository.CommentsRepository;
import com.example.newsBlock.security.AppUserDetails;
import com.example.newsBlock.web.model.UpsertCommentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Aspect
@RequiredArgsConstructor
@Component
@Slf4j
public class CommentOfAspect {
    private final CommentsRepository commentsRepository;
    @Before(value = "execution(* com.example.newsBlock.restController.CommentsController.update(..)) && args(commentDTO,commentId,userDetails)", argNames = " commentDTO, commentId,userDetails")
    public void update( UpsertCommentDTO commentDTO, Long commentId, UserDetails userDetails){
        var comments = commentsRepository.findById(commentId);
        if(userDetails instanceof AppUserDetails users && comments.isPresent()){
            if(users.getAuthorities().contains(new SimpleGrantedAuthority(RoleType.ROLE_USER.name()))&& !Objects.equals(comments.get().getUsers().getId(), users.getId())){
                throw new ValidException("Access denied");
            }
        }else {
            throw new ValidException("Comments is not existed ");
        }
    }
    @Before(value = "execution(* com.example.newsBlock.restController.CommentsController.delete(..)) && args(id,userDetails)", argNames = "  id,userDetails")
    public void delete( Long id, UserDetails userDetails){
        var comments = commentsRepository.findById(id);
        if(userDetails instanceof AppUserDetails users && comments.isPresent()){
            if(users.getAuthorities().contains(new SimpleGrantedAuthority(RoleType.ROLE_USER.name()))&& !Objects.equals(comments.get().getUsers().getId(), users.getId())){
                throw new ValidException("Access denied");
            }
        }else {
            throw new ValidException("Comments is not existed ");
        }
    }

}
