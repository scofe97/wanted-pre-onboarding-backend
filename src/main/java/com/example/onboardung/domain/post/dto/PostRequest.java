package com.example.onboardung.domain.post.dto;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record PostRequest(
        @NonNull
        String content
) {
}
