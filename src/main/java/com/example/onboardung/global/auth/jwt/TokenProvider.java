package com.example.onboardung.global.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
@Slf4j
public class TokenProvider {

    private final String secretKey;
    private final long tokenValidityInSeconds;

    public TokenProvider(
            @Value("${secret-key}") String secretKey,
            @Value("${token-validity-in-seconds}") long tokenValidityInSeconds
    ) {
        this.secretKey = secretKey;
        this.tokenValidityInSeconds = tokenValidityInSeconds;
    }

    /**
     * JWT 토큰생성 메소드
     * @param userSpecification 유저 ID:유저 PW
     * @return Jwts 반환
     */
    public String createToken(String userSpecification) {

        return Jwts.builder()
                // HS512 알고리즘을 사용하여 secretKey를 이용해 서명
                .signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName()))

                // JWT 토큰 제목
                .setSubject(userSpecification)

                // JWT 만료날짜
                .setExpiration(Date.from(Instant.now().plus(tokenValidityInSeconds, ChronoUnit.SECONDS)))

                // JWT 토큰 생성
                .compact();
    }
}
