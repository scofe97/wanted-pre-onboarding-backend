package com.example.onboardung.domain.post.service;

import com.example.onboardung.domain.post.dto.PostResponse;
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
public class PostReadService {

    private final PostRepository postRepository;


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
}
