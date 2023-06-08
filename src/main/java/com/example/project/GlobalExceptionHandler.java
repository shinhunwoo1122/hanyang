package com.example.project;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.ReflectionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleQueryException(ReflectionException ex){
        log.error("오류 발생: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD_REQUEST");
    }
}
