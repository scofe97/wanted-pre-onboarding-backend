package com.example.onboardung.domain.member.exception;

public class MemberAlreadyExistsException extends RuntimeException {

    public MemberAlreadyExistsException() {}

    public MemberAlreadyExistsException(String message) {
        super(message);
    }
}
