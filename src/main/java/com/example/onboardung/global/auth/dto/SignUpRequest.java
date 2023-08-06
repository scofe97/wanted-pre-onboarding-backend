package com.example.onboardung.global.auth.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignUpRequest(
        @Pattern(regexp = ".*@.*", message = "@를 넣어서 이메일을 만들어주세요")
        String email,

        @NotEmpty(message = "이름은 필수값입니다.")
        String name,

        @Size(min=8, message="비밀번호는 최소 8자 이상입니다.")
        String password
) {
}
