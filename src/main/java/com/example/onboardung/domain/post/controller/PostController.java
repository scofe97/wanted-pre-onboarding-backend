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
        return new ResponseEntity<>(postWriteService.addPost(member, postRequest), HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> postDetails(@PathVariable("postId") Long postId) {
        return new ResponseEntity<>(postReadService.findPost(postId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> postList(Pageable pageable) {
        return new ResponseEntity<>(postReadService.findPostList(pageable), HttpStatus.OK);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<?> postModify(@AuthenticationPrincipal Member member, @PathVariable("postId") Long postId, @RequestBody PostRequest postRequest) {
        return new ResponseEntity<>(postWriteService.modifyPost(member, postId, postRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> postRemove(@AuthenticationPrincipal Member member, @PathVariable("postId") Long postId) {
        return new ResponseEntity<>(postWriteService.removePost(member, postId), HttpStatus.OK);
    }
}
