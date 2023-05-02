package com.hanghae.springlevelone.controller;

import com.hanghae.springlevelone.dto.PostRequestDto;
import com.hanghae.springlevelone.dto.PostResponseDto;
import com.hanghae.springlevelone.security.UserDetailsImpl;
import com.hanghae.springlevelone.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blog")
public class PostController {
    private final PostService postService;


    @PostMapping("/posts")
    public ResponseEntity<Object> createPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.createPost(postRequestDto, userDetails.getUser());
    }
    @GetMapping("/post")
    public List<PostResponseDto> getPostList() {
        return postService.getPostList();
    }
    @GetMapping("/post/{id}")
    public PostResponseDto getPost(@PathVariable Long id){
        return postService.getPost(id);
    }
    @PutMapping("/post/{id}")
    public ResponseEntity<Object> updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.updatePost(id, postRequestDto, userDetails.getUser());
    }
    @DeleteMapping("/post/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.deletePost(id, userDetails.getUser());
    }
}
