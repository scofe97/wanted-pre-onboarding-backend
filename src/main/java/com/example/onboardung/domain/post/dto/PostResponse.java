package com.example.onboardung.domain.post.dto;

public record PostResponse(
        Long postId,
        String author,
        String content
) {
}
