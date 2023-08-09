package com.example.onboardung.global.auth.jwt;

import com.example.onboardung.domain.member.entity.Member;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class TokenProvider {

    private final String secretKey;
    private final long tokenValidityInSeconds;

    public TokenProvider(
            @Value("${jwt.secret-key}") String secretKey,
            @Value(value = "${jwt.token-validity-in-seconds}") long tokenValidityInSeconds
    ) {
        this.secretKey = secretKey;
        this.tokenValidityInSeconds = tokenValidityInSeconds;
    }

    /**
     * JWT 토큰생성
     *
     * @param member 유저 Entity
     * @return Jwts 반환
     */
    public String createToken(Member member) {

        return Jwts.builder()
                .setHeader(createHeader())

                // JWT 제목
                .setSubject("JWT 인증")
                // JWT 페이로드
                .setClaims(createClaims(member))
                // JWT 만료날짜
                .setExpiration(Date.from(Instant.now().plus(tokenValidityInSeconds, ChronoUnit.SECONDS)))

                // HS512 알고리즘을 사용하여 secretKey를 이용해 서명
                .signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName()))

                // JWT 토큰 생성
                .compact();
    }

    public boolean validToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey.getBytes())
                    .build()
                    .parseClaimsJws(token);
            return true;

        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            // 유효하지 않은 구성의 JWT 토큰
            log.info("잘못된 JW 서명이다.");
        } catch (ExpiredJwtException e) {
            // 만료된 JWT 토큰
            log.info("만료된 JWT 토큰이다.");
        } catch (UnsupportedJwtException e) {
            // 지원되지 않은 형식이거나 구성의 JWT
            log.info("지원되지 않는 JWT 토큰이다.");
        } catch (IllegalArgumentException e) {
            // 잘못된 JWT
            log.info("JWT 토큰이 잘못되었다.");
        }

        return false;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();

        // Authentication(인증) 구현체, (사용자 -, 비밀번호, 권한)
        return new UsernamePasswordAuthenticationToken(Member.from(claims), token, List.of(new SimpleGrantedAuthority("USER")));
    }

    private Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256"); // 해시 256 암호화
        return header;
    }

    //  payload
    private Map<String, Object> createClaims(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", member.getMemberId());
        claims.put("email", member.getEmail());
        claims.put("name", member.getMemberName());
        return claims;
    }

    public String createTestToken() {
        Member testMember = Member.builder()
                .memberId(1L)
                .email("test@")
                .memberName("test")
                .build();

        return createToken(testMember);
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
