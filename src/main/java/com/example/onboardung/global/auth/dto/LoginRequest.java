package com.example.onboardung.global.auth.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record LoginRequest(
        @Pattern(regexp = ".*@.*", message = "이메일은 @가 포함되어야 합니다.")
        String email,

        @Size(min=8, message="비밀번호는 최소 8자리 이상이어야 합니다.")
        String password
) {
}
