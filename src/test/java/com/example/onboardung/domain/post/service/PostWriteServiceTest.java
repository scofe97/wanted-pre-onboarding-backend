package com.example.onboardung.domain.post.service;

import com.example.onboardung.domain.member.entity.Member;
import com.example.onboardung.domain.post.dto.PostRequest;
import com.example.onboardung.domain.post.dto.PostResponse;
import com.example.onboardung.domain.post.entity.Post;
import com.example.onboardung.domain.post.exception.NoPostWriterException;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostWriteServiceTest {

    @InjectMocks
    private PostWriteService postWriteService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private TokenProvider tokenProvider;

    @Test
    void 게시글추가() {
        PostRequest postRequest = PostSteps.getPostRequest();
        Member member = AuthSteps.getTrueMember();
        Post post = PostSteps.getPost(member, postRequest);

        when(postRepository.save(any(Post.class))).thenReturn(post);
        PostResponse postResponse = postWriteService.addPost(member, postRequest);
        assertEquals(ConverterDto.convertPostResponse(post), postResponse);
    }

    @Test
    void 게시글수정_실패() {
        PostRequest postModifyRequest = PostSteps.getPostModifyRequest();
        Member write_member = AuthSteps.getTrueMember();
        Member login_member = AuthSteps.getFalseMember();
        Post post = PostSteps.getPost(write_member, postModifyRequest);

        when(postRepository.findById(anyLong())).thenReturn(Optional.ofNullable(post));
        assertThrows(NoPostWriterException.class, () -> postWriteService.modifyPost(login_member, 1L, postModifyRequest));
    }

    @Test
    void 게시글수정_성공() {
        PostRequest postModifyRequest = PostSteps.getPostModifyRequest();
        Member write_member = AuthSteps.getTrueMember();
        Member login_member = AuthSteps.getTrueMember();
        Post post = PostSteps.getPost(write_member, postModifyRequest);

        when(postRepository.findById(anyLong())).thenReturn(Optional.ofNullable(post));
        assertEquals(ConverterDto.convertPostResponse(post), postWriteService.modifyPost(login_member, 1L, postModifyRequest));
    }

    @Test
    void 게시글삭제_실패() {
        PostRequest postRequest = PostSteps.getPostRequest();
        Member write_member = AuthSteps.getTrueMember();
        Member login_member = AuthSteps.getFalseMember();
        Post post = PostSteps.getPost(write_member, postRequest);

        when(postRepository.findById(anyLong())).thenReturn(Optional.ofNullable(post));
        assertThrows(NoPostWriterException.class, () -> postWriteService.modifyPost(login_member, 1L, postRequest));
    }

    @Test
    void 게시글삭제_성공() {
        PostRequest postRequest = PostSteps.getPostRequest();
        Member write_member = AuthSteps.getTrueMember();
        Member login_member = AuthSteps.getTrueMember();
        Post post = PostSteps.getPost(write_member, postRequest);

        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
        postWriteService.removePost(login_member, post.getPostId());

        verify(postRepository, times(1)).deleteById(post.getPostId());
    }
}