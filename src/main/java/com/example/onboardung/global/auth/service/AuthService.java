package com.example.onboardung.global.auth.service;

import com.example.onboardung.domain.member.entity.Member;
import com.example.onboardung.domain.member.repository.MemberRepository;
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
    public void signUp(SignUpRequest request) {
        memberRepository.save(Member.from(request, passwordEncoder));
    }

    @Transactional(readOnly = true)
    public void login(SignUpRequest request) {
        Member loginMember = memberRepository.findByEmail(request.email())
                .filter(it -> passwordEncoder.matches(request.password(), it.getPassword()))
                .orElseThrow();

        String token = tokenProvider
                .createToken(String.format("%s:%s", loginMember.getId(), loginMember.getPassword()));
    }
}
