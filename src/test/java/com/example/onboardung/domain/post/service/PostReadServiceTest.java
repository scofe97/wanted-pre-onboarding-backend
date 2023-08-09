package com.example.onboardung.domain.post.service;

import com.example.onboardung.domain.member.entity.Member;
import com.example.onboardung.domain.post.dto.PostRequest;
import com.example.onboardung.domain.post.dto.PostResponse;
import com.example.onboardung.domain.post.entity.Post;
import com.example.onboardung.domain.post.repository.PostRepository;
import com.example.onboardung.global.auth.jwt.TokenProvider;
import com.example.onboardung.global.util.AuthSteps;
import com.example.onboardung.global.util.ConverterDto;
import com.example.onboardung.global.util.PostSteps;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostReadServiceTest {

    @InjectMocks
    private PostReadService postReadService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private TokenProvider tokenProvider;

    @Test
    void 게시글조회_성공() {
        PostRequest postRequest = PostSteps.getPostRequest();
        Member write_member = AuthSteps.getTrueMember();
        Post post = PostSteps.getPost(write_member, postRequest);

        when(postRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(post));

        assertEquals(ConverterDto.convertPostResponse(post), postReadService.findPost(1L));
        verify(postRepository, times(1)).findById(1L);
    }

    @Test
    void 게시글리스트조회_성공() {
        Pageable pageable = PageRequest.of(0, 10);

        PostRequest postRequest = PostSteps.getPostRequest();
        Member write_member = AuthSteps.getTrueMember();
        List<Post> mockPosts = IntStream.range(0, 10)
                .mapToObj(i -> PostSteps.getPost(write_member, postRequest))
                .collect(Collectors.toList());

        Page<Post> mockPostPage = new PageImpl<>(mockPosts, pageable, mockPosts.size());
        when(postRepository.findAll(pageable)).thenReturn(mockPostPage);


        List<PostResponse> postResponses = postReadService.findPostList(pageable);

        // Then
        assertNotNull(postResponses);
        assertEquals(10, postResponses.size());
        verify(postRepository, times(1)).findAll(pageable);
    }
}