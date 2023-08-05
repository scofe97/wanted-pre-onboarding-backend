package com.example.onboardung.global.auth.service;

import com.example.onboardung.domain.member.entity.Member;
import com.example.onboardung.domain.member.repository.MemberRepository;
import com.example.onboardung.global.auth.dto.SignUpRequest;
import com.example.onboardung.global.auth.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SignService {

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    public void register(SignUpRequest request) {
        memberRepository.save(Member.from(request, passwordEncoder));
    }

    public void SignIn(SignUpRequest request) {
        memberRepository.findByM
    }
}
