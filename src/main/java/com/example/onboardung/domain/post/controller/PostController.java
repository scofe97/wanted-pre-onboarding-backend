package com.example.onboardung.domain.post.controller;


import com.example.onboardung.domain.member.entity.Member;
import com.example.onboardung.domain.post.dto.PostRequest;
import com.example.onboardung.domain.post.service.PostReadService;
import com.example.onboardung.domain.post.service.PostWriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostReadService postReadService;
    private final PostWriteService postWriteService;

    @PostMapping
    public ResponseEntity<?> postAdd(@AuthenticationPrincipal Member member, @RequestBody PostRequest postRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postWriteService.addPost(member, postRequest));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> postDetails(@PathVariable("postId") Long postId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(postReadService.findPost(postId));
    }

    @GetMapping
    public ResponseEntity<?> postList(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(postReadService.findPostList(pageable));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<?> postModify(@AuthenticationPrincipal Member member, @PathVariable("postId") Long postId, @RequestBody PostRequest postRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(postWriteService.modifyPost(member, postId, postRequest));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> postRemove(@AuthenticationPrincipal Member member, @PathVariable("postId") Long postId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(postWriteService.removePost(member, postId));
    }
}
