package com.example.onboardung.global.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Primary
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = parseBearerToken(request);
        User user = parseUserSpecification(token);

        // Security 인증정보 설정
        // AbstractAuthenticationToken => 사용자의 정보를 보유한다.
        AbstractAuthenticationToken authenticated = UsernamePasswordAuthenticationToken.authenticated(user, token, user.getAuthorities());

        // 요청에 대한 정보를 담는다.
        authenticated.setDetails(new WebAuthenticationDetails(request));

        // SecurityContextHolder => 인증정보를 담는다.
        SecurityContextHolder.getContext().setAuthentication(authenticated);

        filterChain.doFilter(request, response);

    }

    /**
     * Header Authorization 값을 추출한다 (Bearer ~~)
     * @param request
     * @return
     */
    private String parseBearerToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .map(token -> token.substring(7))
                .orElse(null);
    }

    /**
     * 토큰값을 토대로 토큰에 담긴 회원ID, 회원PW를 바탕으로 시큐리티에서 사용할 Security 생성
     * @param token
     * @return
     */
    private User parseUserSpecification(String token) {
        String[] split = Optional.ofNullable(token)
                .filter(subject -> subject.length() >= 10)
                .map(tokenProvider::validateTokenAndGetSubject)
                .orElse("anonymous:anonymous")
                .split(":");

        return new User(split[0], split[1], List.of(new SimpleGrantedAuthority("USER")));
    }
}
