package com.example.onboardung.global.auth.controller;


import com.example.onboardung.global.auth.dto.LoginRequest;
import com.example.onboardung.global.auth.dto.SignUpRequest;
import com.example.onboardung.global.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> authSignUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        String message = authService.signUp(signUpRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(message);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authLogin(@Valid @RequestBody LoginRequest loginRequest) {
        String token = authService.login(loginRequest);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(httpHeaders)
                .body("로그인 성공");
    }
}
