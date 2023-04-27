package com.hanghae.springlevelone.controller;

import com.hanghae.springlevelone.dto.PostRequestDto;
import com.hanghae.springlevelone.dto.PostResponseDto;
import com.hanghae.springlevelone.message.Message;
import com.hanghae.springlevelone.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blog")
public class PostController {
    private final PostService postService;


    @PostMapping("/posting")
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto, HttpServletRequest request) {
        return postService.createPost(postRequestDto, request);
    }
    @GetMapping("/list")
    public List<PostResponseDto> getPostList() {
        return postService.getPostList();
    }
    @GetMapping("/list/{id}")
    public PostResponseDto getPost(@PathVariable Long id){
        return postService.getPost(id);
    }
    @PutMapping("/{id}")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, HttpServletRequest request) {
        return postService.updatePost(id, postRequestDto, request);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Message> deletePost(@PathVariable Long id, HttpServletRequest request) {
        return postService.deletePost(id, request);
    }
}
