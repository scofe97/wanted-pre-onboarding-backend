package com.example.onboardung.global.auth.service;

import com.example.onboardung.domain.member.entity.Member;
import com.example.onboardung.domain.member.repository.MemberRepository;
import com.example.onboardung.global.auth.dto.LoginRequest;
import com.example.onboardung.global.auth.dto.SignUpRequest;
import com.example.onboardung.global.auth.jwt.TokenProvider;
import com.example.onboardung.global.util.AuthSteps;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private TokenProvider tokenProvider;

    @Mock
    private PasswordEncoder passwordEncoder;


    @Test
    void 존재하는_이메일_회원가입() {
        // given
        SignUpRequest request = AuthSteps.getSignUpRequest();


        // when
        when(memberRepository.findByEmail(request.email())).thenReturn(Optional.of(new Member()));
        String result = authService.signUp(request);

        // then
        assertEquals("이미 존재하는 이메일입니다", result);
    }

    @Test
    void 새로운_이메일_회원가입() {
        // given
        SignUpRequest request = AuthSteps.getSignUpRequest();

        // when
        when(memberRepository.findByEmail(request.email())).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password1234")).thenReturn("encodedPassword");

        String result = authService.signUp(request);

        assertEquals("회원가입 성공", result);
    }

    @Test
    void 로그인_실패_잘못된입력() {
        LoginRequest request = AuthSteps.getLoginRequest();

        when(memberRepository.findByEmail(request.email())).thenReturn(Optional.of(new Member()));
        when(passwordEncoder.matches("notPassword1234", "password1234")).thenReturn(false);

        assertThrows(Exception.class, () -> authService.login(request));
    }

    @Test
    void 로그인_성공() {
        LoginRequest request = AuthSteps.getLoginRequest();
        Member member = AuthSteps.getTrueMember();

        // 중복 이메일이 없다
        when(memberRepository.findByEmail(request.email())).thenReturn(Optional.of(member));
        // 암호화한 비밀번호가 일치한다
        when(passwordEncoder.matches(request.password(), member.getPassword())).thenReturn(true);
        // 올바른 토큰을 반환했다.
        when(tokenProvider.createToken(member)).thenReturn("validToken");

        String result = authService.login(request);

        assertEquals("validToken", result);
    }
}