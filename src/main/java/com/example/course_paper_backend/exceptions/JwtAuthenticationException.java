package com.example.course_paper_backend.exceptions;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends RuntimeException {

    public JwtAuthenticationException(String msg) {
        super(msg);
    }
}
