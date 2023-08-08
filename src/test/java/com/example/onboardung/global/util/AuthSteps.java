package com.example.onboardung.global.util;

import com.example.onboardung.domain.member.entity.Member;
import com.example.onboardung.global.auth.dto.LoginRequest;
import com.example.onboardung.global.auth.dto.SignUpRequest;

public class AuthSteps {
    public static SignUpRequest getSignUpRequest() {
        return new SignUpRequest("test@", "test", "password1234");
    }

    public static LoginRequest getLoginRequest() {
        return new LoginRequest("test@", "password1234");
    }

    public static Member getMember() {
        return new Member(1L, "test@", "test", "encodedPassword");
    }
}
