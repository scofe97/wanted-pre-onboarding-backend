package com.example.onboardung.domain.post.exception;

public class NoPostWriterException extends RuntimeException {

    public NoPostWriterException() {}

    public NoPostWriterException(String message) {
        super(message);
    }
}
