package com.example.onboardung.global.auth.service;

import com.example.onboardung.domain.member.entity.Member;
import com.example.onboardung.domain.member.repository.MemberRepository;
import com.example.onboardung.global.auth.dto.LoginRequest;
import com.example.onboardung.global.auth.dto.SignUpRequest;
import com.example.onboardung.global.auth.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String signUp(SignUpRequest request) {
        if(memberRepository.findByEmail(request.email()).isPresent()){
            return "이미 존재하는 이메일입니다";
        }

        memberRepository.save(Member.from(request, passwordEncoder));
        return "회원가입 성공";
    }

    @Transactional(readOnly = true)
    public String login(LoginRequest request) {
        Member loginMember = memberRepository.findByEmail(request.email())
                .filter(it -> passwordEncoder.matches(request.password(), it.getPassword()))
                .orElseThrow();

        return tokenProvider.createToken(loginMember);
    }
}
