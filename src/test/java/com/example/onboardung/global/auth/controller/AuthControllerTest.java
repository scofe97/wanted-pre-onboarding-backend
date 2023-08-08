package com.example.onboardung.global.auth.controller;

import com.example.onboardung.global.auth.dto.LoginRequest;
import com.example.onboardung.global.auth.dto.SignUpRequest;
import com.example.onboardung.global.auth.jwt.TokenProvider;
import com.example.onboardung.global.auth.service.AuthService;
import com.example.onboardung.global.util.AuthSteps;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext ctx;

    @MockBean
    private TokenProvider tokenProvider;

    @MockBean
    private AuthService authService;


    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @Test
    void 회원가입_성공() throws Exception {
        SignUpRequest signUpRequest = AuthSteps.getSignUpRequest();
        when(authService.signUp(signUpRequest)).thenReturn("회원가입 성공");

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(signUpRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().string("회원가입 성공"));
    }

    @Test
    void 로그인_성공() throws Exception {
        LoginRequest loginRequest = AuthSteps.getLoginRequest();
        when(authService.login(loginRequest)).thenReturn("validToken");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(header().string("Authorization", "Bearer validToken"))
                .andExpect(content().string("로그인 성공"));
    }
}