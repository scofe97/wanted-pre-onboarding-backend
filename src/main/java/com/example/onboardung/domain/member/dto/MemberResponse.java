package com.example.onboardung.domain.member.dto;

import lombok.Builder;

@Builder
public record MemberResponse(
        String email
) {
}
