package com.example.onboardung.domain.post.controller;


import com.example.onboardung.domain.member.entity.Member;
import com.example.onboardung.domain.post.dto.PostRequest;
import com.example.onboardung.domain.post.service.PostService;
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

    private final PostService postService;

    @PostMapping
    public ResponseEntity<?> postAdd(@AuthenticationPrincipal Member member, @RequestBody PostRequest postRequest
    ) {
        return new ResponseEntity<>(postService.addPost(member, postRequest), HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> postDetails(@PathVariable("postId") Long postId) {
        return new ResponseEntity<>(postService.findPost(postId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> postList(Pageable pageable) {
        return new ResponseEntity<>(postService.findPostList(pageable), HttpStatus.OK);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<?> postModify(@AuthenticationPrincipal Member member, @PathVariable("postId") Long postId, @RequestBody PostRequest postRequest) {
        return new ResponseEntity<>(postService.modifyPost(member, postId, postRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> postRemove(@AuthenticationPrincipal Member member, @PathVariable("postId") Long postId) {
        return new ResponseEntity<>(postService.removePost(member, postId), HttpStatus.OK);
    }
}
