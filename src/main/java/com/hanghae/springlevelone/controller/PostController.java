package com.hanghae.springlevelone.controller;

import com.hanghae.springlevelone.dto.PostRequestDto;
import com.hanghae.springlevelone.dto.PostResponseDto;
import com.hanghae.springlevelone.service.PostService;
import lombok.RequiredArgsConstructor;
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


    @PostMapping("/posting")
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return postService.createPost(postRequestDto, request, response);
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
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return postService.updatePost(id, postRequestDto, request, response);
    }
    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return postService.deletePost(id, request, response);
    }
}
