package com.example.onboardung.global.util;

import com.example.onboardung.domain.member.dto.MemberResponse;
import com.example.onboardung.domain.member.entity.Member;
import com.example.onboardung.domain.post.dto.PostResponse;
import com.example.onboardung.domain.post.entity.Post;

public class ConverterDto {

    public static MemberResponse convertMemberResponse(Member member) {
        return new MemberResponse(member.getEmail());
    }

    public static PostResponse convertPostResponse(Post post){
        return new PostResponse(post.getPostId(), post.getMember().getMemberName(),post.getContent());
    }
}
