package com.example.onboardung.global.util;

import com.example.onboardung.domain.member.dto.MemberResponse;
import com.example.onboardung.domain.member.entity.Member;
import com.example.onboardung.domain.post.dto.PostResponse;
import com.example.onboardung.domain.post.entity.Post;

public class ConverterDto {

    public static MemberResponse convertMemberResponse(Member member) {
        return MemberResponse.builder()
                .email(member.getEmail())
                .build();
    }

    public static PostResponse convertPostResponse(Post post){
        return PostResponse.builder()
                .postId(post.getPostId())
                .author(post.getMember().getMemberName())
                .content(post.getContent())
                .build();
    }
}
