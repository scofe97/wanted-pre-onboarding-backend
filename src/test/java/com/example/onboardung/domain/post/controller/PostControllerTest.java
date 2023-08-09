package com.example.onboardung.domain.post.controller;

import com.example.onboardung.domain.member.entity.Member;
import com.example.onboardung.domain.post.dto.PostRequest;
import com.example.onboardung.domain.post.dto.PostResponse;
import com.example.onboardung.domain.post.service.PostReadService;
import com.example.onboardung.domain.post.service.PostWriteService;
import com.example.onboardung.global.auth.jwt.TokenProvider;
import com.example.onboardung.global.util.AuthSteps;
import com.example.onboardung.global.util.PostSteps;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext ctx;

    @MockBean
    private TokenProvider tokenProvider;

    @MockBean
    private PostReadService postReadService;

    @MockBean
    private PostWriteService postWriteService;

    private Authentication auth;

    @BeforeEach
    void setUp() {
        auth = mock(Authentication.class);

        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @Test
    void 게시글작성_성공() throws Exception {
        PostRequest postRequest = PostSteps.getPostRequest();
        Member member = AuthSteps.getTrueMember();
        setAuthMember(member);

        when(postWriteService.addPost(eq(member), any(PostRequest.class)))
                .thenReturn("글이 생성되었습니다.");

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(postRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().string("글이 생성되었습니다."));

    }

    @Test
    void 게시물조회_성공() throws Exception {
        PostResponse postResponse = PostSteps.getPostResponse();

        when(postReadService.findPost(anyLong()))
                .thenReturn(postResponse);

        mockMvc.perform(get("/posts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void postList() {

    }

    @Test
    void 게시글수정_성공() throws Exception {
        PostRequest postModifyRequest = PostSteps.getPostModifyRequest();
        PostResponse postModifyResponse = PostSteps.getPostModifyResponse();
        Member member = AuthSteps.getTrueMember();
        setAuthMember(member);

        when(postWriteService.modifyPost(eq(member), anyLong(), any(PostRequest.class)))
                .thenReturn(postModifyResponse);

        mockMvc.perform(put("/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer tokenValue")
                        .content(new Gson().toJson(postModifyRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(new Gson().toJson(postModifyResponse)));
    }

    @Test
    void 게시글삭제_성공() throws Exception {
        Member member = AuthSteps.getTrueMember();
        setAuthMember(member);

        when(postWriteService.removePost(eq(member), anyLong()))
                .thenReturn("삭제되었습니다");

        mockMvc.perform(delete("/posts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("삭제되었습니다"));
    }

    private void setAuthMember(Member member) {
        when(auth.getPrincipal()).thenReturn(member);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}