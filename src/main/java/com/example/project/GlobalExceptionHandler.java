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

    @ExceptionHandler(ReflectionException.class)
    public ResponseEntity<String> handleQueryException(ReflectionException ex){
        log.info("DB INSERT 실패: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
    }
}
