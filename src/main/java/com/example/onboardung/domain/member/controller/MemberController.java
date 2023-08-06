package com.example.onboardung.domain.member.controller;


import com.example.onboardung.domain.member.entity.Member;
import com.example.onboardung.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> memberDetails(@AuthenticationPrincipal User user, @PathVariable("userId") Long userId){
        log.info("User = {}", user);
        return new ResponseEntity<>(memberService.findMember(userId), HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<?> authLogin(@AuthenticationPrincipal Member member) {
        log.info("member = {}", member);
        return new ResponseEntity<>("정상호출", HttpStatus.OK);
    }

}
