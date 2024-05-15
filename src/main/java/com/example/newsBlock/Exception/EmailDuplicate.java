package com.example.newsBlock.Exception;

import org.springframework.dao.DataIntegrityViolationException;

public class EmailDuplicate extends DataIntegrityViolationException {
    public EmailDuplicate(String msg) {
        super(msg);
    }
}
