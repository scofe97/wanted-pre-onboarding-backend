package com.example.onboardung.domain.post.service;

import com.example.onboardung.domain.member.entity.Member;
import com.example.onboardung.domain.post.dto.PostRequest;
import com.example.onboardung.domain.post.dto.PostResponse;
import com.example.onboardung.domain.post.entity.Post;
import com.example.onboardung.domain.post.exception.NoPostWriterException;
import com.example.onboardung.domain.post.repository.PostRepository;
import com.example.onboardung.global.util.ConverterDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostWriteService {

    private final PostRepository postRepository;

    @Transactional
    public String addPost(Member member, PostRequest postRequest) {
        postRepository.save(Post.builder()
                .member(member)
                .content(postRequest.content())
                .build());

        return "글이 생성되었습니다.";
    }


    @Transactional
    public PostResponse modifyPost(Member member, Long postId, PostRequest postRequest) {
        Post post = postRepository.findById(postId).orElseThrow();
        validatePostAuthor(post, member);
        post.update(postRequest);
        return ConverterDto.convertPostResponse(post);
    }


    @Transactional
    public String removePost(Member member, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        validatePostAuthor(post, member);
        postRepository.deleteById(postId);
        return "삭제되었습니다.";
    }

    private void validatePostAuthor(Post post, Member member) {
        if (!post.getMember().getMemberId().equals(member.getMemberId())) {
            throw new NoPostWriterException("게시물 작성자가 아닙니다.");
        }
    }
}
