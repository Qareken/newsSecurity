package com.example.newsBlock.Exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CategoryException extends RuntimeException {
    public CategoryException(String message) {
        super(message);
    }
    // Дополнительные конструкторы или методы по необходимости
}
