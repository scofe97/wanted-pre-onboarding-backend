package com.example.onboardung.global.auth.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignUpRequest(
        @Pattern(regexp = ".*@.*", message = "Email should contain @")
        String email,

        @Size(min=8, message="Password should be at least 8 characters")
        String password
) {
}
