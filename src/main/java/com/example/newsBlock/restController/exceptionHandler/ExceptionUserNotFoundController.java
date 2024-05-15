package com.example.newsBlock.restController.exceptionHandler;


import com.example.newsBlock.Exception.*;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ExceptionUserNotFoundController {
    @ExceptionHandler(EntityNotFoundException.class)
    ResponseEntity<?> handleEntityNotFound(EntityNotFoundException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
    @ExceptionHandler(CategoryException.class)
    ResponseEntity<?> handleNotCategory(CategoryException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
    @ExceptionHandler(CustomDuplicateException.class)
    ResponseEntity<?> handleDuplicate(CustomDuplicateException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
    @ExceptionHandler(ValidException.class)
    ResponseEntity<?> handleAccess(ValidException exception){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
    @ExceptionHandler(EmailDuplicate.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        // Логирование ошибки для отладки
//        log.error("DataIntegrityViolationException: ", ex);

        // Проверка на конкретное ограничение, например, нарушение уникальности email
//        if (ex.getCause() instanceof ConstraintViolationException cve) {
//            if (cve.getMessage().contains("email_unique_constraint")) {  // имя ограничения может отличаться
//                log.info("email unique Sardar");
//                return ResponseEntity
//                        .status(HttpStatus.CONFLICT)
//                        .body("Email already exists: " + cve.getMessage());
//            }
//        }
        // Для других случаев DataIntegrityViolationException
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Database error: " + ex.getMessage());
    }

}
