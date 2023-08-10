package com.example.onboardung.global.exception;

import com.example.onboardung.domain.member.exception.MemberAlreadyExistsException;
import com.example.onboardung.domain.post.exception.NoPostWriterException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorMessage);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleSuchElementExceptions() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("해당 객체가 존재하지 않습니다.");
    }

    @ExceptionHandler(NoPostWriterException.class)
    public ResponseEntity<String> handleNoPostWriterExceptions(NoPostWriterException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ex.getMessage());
    }

    @ExceptionHandler(MemberAlreadyExistsException.class)
    public ResponseEntity<String> handleMemberAlreadyExistsExceptions(MemberAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
}
