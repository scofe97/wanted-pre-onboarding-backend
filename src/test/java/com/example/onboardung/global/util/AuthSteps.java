package com.example.onboardung.global.util;

import com.example.onboardung.domain.member.entity.Member;
import com.example.onboardung.global.auth.dto.LoginRequest;
import com.example.onboardung.global.auth.dto.SignUpRequest;

public class AuthSteps {
    public static SignUpRequest getSignUpRequest() {
        return SignUpRequest.builder()
                .email("test@")
                .name("test")
                .password("password1234")
                .build();
    }

    public static LoginRequest getLoginRequest() {
        return LoginRequest.builder()
                .email("test@")
                .password("password1234")
                .build();
    }

    public static Member getTrueMember() {
        return Member.builder()
                .memberId(1L)
                .email("test@")
                .memberName("test")
                .password("encodedPassword")
                .build();
    }

    public static Member getFalseMember() {
        return Member.builder()
                .memberId(2L)
                .email("test2@")
                .memberName("test2")
                .password("encodedPassword")
                .build();
    }
}
