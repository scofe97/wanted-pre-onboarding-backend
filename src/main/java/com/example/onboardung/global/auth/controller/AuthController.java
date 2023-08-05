package com.example.onboardung.global.auth.controller;


import com.example.onboardung.domain.member.service.MemberService;
import com.example.onboardung.global.auth.dto.LoginRequest;
import com.example.onboardung.global.auth.dto.SignUpRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    @GetMapping("/signup")
    public ResponseEntity<?> authSignUp(@Valid @RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok("회원가입 성공");
    }

    @GetMapping("/login")
    public ResponseEntity<?> authLogin(@Valid @RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok("로그인 성공");
    }

}
