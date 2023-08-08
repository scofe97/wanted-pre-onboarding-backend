package com.example.onboardung.global.util;

import com.example.onboardung.domain.member.entity.Member;
import com.example.onboardung.global.auth.dto.LoginRequest;
import com.example.onboardung.global.auth.dto.SignUpRequest;

public class AuthSteps {
    public static SignUpRequest getSignUpRequest() {
        SignUpRequest request = new SignUpRequest("test@", "test", "password1234");
        return request;
    }

    public static LoginRequest getLoginRequest() {
        LoginRequest request = new LoginRequest("test@", "password1234");
        return request;
    }

    public static Member getMember() {
        Member member = new Member(1L, "test@", "test", "encodedPassword");
        return member;
    }
}
