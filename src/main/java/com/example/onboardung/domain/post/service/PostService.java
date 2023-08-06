package com.example.onboardung.domain.post.service;

import com.example.onboardung.domain.member.entity.Member;
import com.example.onboardung.domain.post.dto.PostRequest;
import com.example.onboardung.domain.post.dto.PostResponse;
import com.example.onboardung.domain.post.entity.Post;
import com.example.onboardung.domain.post.repository.PostRepository;
import com.example.onboardung.global.util.ConverterDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public String addPost(Member member, PostRequest postRequest) {
        postRepository.save(Post.builder()
                .member(member)
                .content(postRequest.content())
                .build());

        return "글이 생성되었습니다";
    }


    @Transactional(readOnly = true)
    public PostResponse findPost(Long postId) {
        return ConverterDto.convertPostResponse(postRepository.findById(postId).orElseThrow());
    }


    @Transactional(readOnly = true)
    public List<PostResponse> findPostList(Pageable pageable) {
        return postRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(ConverterDto::convertPostResponse)
                .collect(Collectors.toList());
    }


    @Transactional
    public PostResponse modifyPost(Member member, Long postId, PostRequest postRequest) {
        Post post = postRepository.findById(postId).orElseThrow();

        if(post.getMember().getMemberId().equals(member.getMemberId())){
            post.update(postRequest);
        }

        return ConverterDto.convertPostResponse(post);
    }


    @Transactional
    public String removePost(Member member, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();

        if(post.getMember().getMemberId().equals(member.getMemberId())){
            postRepository.deleteById(postId);
            return "삭제되었습니다";
        }

        return "게시물 작성자가 아닙니다.";
    }
}
