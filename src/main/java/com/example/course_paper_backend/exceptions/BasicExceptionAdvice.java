package com.example.course_paper_backend.exceptions;

import com.example.course_paper_backend.model.ResponseV1;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.text.ParseException;

@ControllerAdvice
public class BasicExceptionAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseV1> handleException(NotFoundException e) {
        ResponseV1 response = new ResponseV1().toBuilder()
                .errorCode(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<ResponseV1> handleException(JwtAuthenticationException e) {
        ResponseV1 response = new ResponseV1().toBuilder()
                .errorCode(HttpStatus.FORBIDDEN.value())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ResponseV1> handleException(ExpiredJwtException e) {
        ResponseV1 response = new ResponseV1().toBuilder()
                .errorCode(HttpStatus.FORBIDDEN.value())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseV1> handleException(IllegalArgumentException e) {
        ResponseV1 response = new ResponseV1().toBuilder()
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ResponseV1> handleException(JwtException e) {
        ResponseV1 response = new ResponseV1().toBuilder()
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ResponseV1> handleException(AlreadyExistsException e) {
        ResponseV1 response = new ResponseV1().toBuilder()
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidFieldsException.class)
    public ResponseEntity<ResponseV1> handleException(InvalidFieldsException e) {
        ResponseV1 response = new ResponseV1().toBuilder()
                .errorCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ParseException.class)
    public ResponseEntity<ResponseV1> handleException(ParseException e) {
        ResponseV1 response = new ResponseV1().toBuilder()
                .errorCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(JSONException.class)
    public ResponseEntity<ResponseV1> handleException(JSONException e) {
        ResponseV1 response = new ResponseV1().toBuilder()
                .errorCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseV1> handleException(Exception e) {
        ResponseV1 response = new ResponseV1().toBuilder()
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
