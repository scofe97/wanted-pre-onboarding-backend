package com.example.onboardung.global.auth.controller;


import com.example.onboardung.domain.member.service.MemberService;
import com.example.onboardung.global.auth.dto.LoginRequest;
import com.example.onboardung.global.auth.dto.SignUpRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    @PostMapping("/signup")
    public ResponseEntity<?> authSignUp(@Valid @RequestBody SignUpRequest signUpRequest){
        return new ResponseEntity<>("회원가입 성공", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authLogin(@Valid @RequestBody LoginRequest loginRequest){
        return new ResponseEntity<>("로그인 성공", HttpStatus.OK);
    }

}
