package com.example.newsBlock.restController;

import com.example.newsBlock.mapper.CommentMapper;
import com.example.newsBlock.security.AppUserDetails;
import com.example.newsBlock.service.impl.CommentsServiceImpl;
import com.example.newsBlock.service.impl.UsersServiceImpl;
import com.example.newsBlock.web.model.CommentDTO;
import com.example.newsBlock.web.model.UpsertCommentDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
@Slf4j
public class CommentsController {
    private final CommentMapper commentMapper;
    private final CommentsServiceImpl service;
    private final UsersServiceImpl usersService;


    @PostMapping("/create")
    public ResponseEntity<CommentDTO> createComment(@Valid @RequestBody UpsertCommentDTO commentDTO, @AuthenticationPrincipal UserDetails userDetails) {
        var comment = commentMapper.toEntity(commentDTO);
        var author = usersService.findByEmail(userDetails.getUsername());
        comment.setUsers(author);
        log.info(comment.getUsers().getLastName() + " " + comment.getNews().getId());
        return ResponseEntity.ok().body(commentMapper.toDto(service.save(comment)));
    }

    @PutMapping("/update")
    public ResponseEntity<CommentDTO> update(@Valid @RequestBody UpsertCommentDTO commentDTO, Long commentId, @AuthenticationPrincipal UserDetails userDetails) {
        var comment = commentMapper.toEntity(commentDTO);
        return ResponseEntity.ok().body(commentMapper.toDto(service.update(comment, commentId)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        if(userDetails instanceof AppUserDetails users){
            service.deleteById(id, users.getId());
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request with userDetails has an error");
    }
}
