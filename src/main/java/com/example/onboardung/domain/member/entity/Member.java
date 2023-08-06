package com.example.onboardung.domain.member.entity;

import com.example.onboardung.global.auth.dto.SignUpRequest;
import io.jsonwebtoken.Claims;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Pattern(regexp=".*@.*", message="이메일은 @이 포함되어야 한다.")
    private String email;

    @NonNull
    @Size(min=8, message="비밀번호는 최소 8글자이다.")
    private String password;

    public static Member from(SignUpRequest request, PasswordEncoder encoder) {	// 파라미터에 PasswordEncoder 추가
        return Member.builder()
                .email(request.email())
                .password(encoder.encode(request.password()))
                .build();
    }

    public static Member from(Claims claims) {
        return Member.builder()
                .id(Long.valueOf(claims.get("id").toString()))
                .password("")
                .email(claims.get("email").toString())
                .build();
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
