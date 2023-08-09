package com.example.onboardung.global.util;

import com.example.onboardung.domain.member.entity.Member;
import com.example.onboardung.domain.post.dto.PostRequest;
import com.example.onboardung.domain.post.dto.PostResponse;
import com.example.onboardung.domain.post.entity.Post;

public class PostSteps {

    public static PostRequest getPostRequest() {
        return PostRequest.
                builder()
                .content("글 내용")
                .build();
    }

    public static PostRequest getPostModifyRequest() {
        return PostRequest
                .builder()
                .content("글 수정")
                .build();
    }

    public static PostResponse getPostResponse(){
        return PostResponse
                .builder()
                .postId(1L)
                .author("test")
                .content("글 내용")
                .build();
    }

    public static PostResponse getPostModifyResponse(){
        return PostResponse
                .builder()
                .postId(1L)
                .author("test")
                .content("글 수정")
                .build();
    }

    public static Post getPost(Member member, PostRequest postRequest){
        return Post.builder()
                .postId(1L)
                .member(member)
                .content(postRequest.content())
                .build();
    }
}
