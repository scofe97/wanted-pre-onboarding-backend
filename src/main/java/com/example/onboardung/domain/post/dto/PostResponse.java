package com.example.onboardung.domain.post.dto;

import lombok.Builder;

@Builder
public record PostResponse(
        Long postId,
        String author,
        String content
) {
}
