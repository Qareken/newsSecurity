package com.example.newsBlock.Exception;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
@Slf4j
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
        log.warn(message);
    }
}
