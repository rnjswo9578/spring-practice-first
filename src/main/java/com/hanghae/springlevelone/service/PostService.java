package com.hanghae.springlevelone.service;

import com.hanghae.springlevelone.dto.PostRequestDto;
import com.hanghae.springlevelone.dto.PostResponseDto;
import com.hanghae.springlevelone.entity.Post;
import com.hanghae.springlevelone.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public String createPost(PostRequestDto postRequestDto) {
        Post post = new Post(postRequestDto);
        postRepository.save(post);
        return "게시글이 등록됐습니다.";
    }
    public List<PostResponseDto> getPostList() {
        return postRepository.findAllByOrderByCreatedAtDesc().stream().map(PostResponseDto::new).collect(Collectors.toList());
    }
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 게시글이 존재하지 않습니다.")
        );
        return new PostResponseDto(post);
    }
    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto, String password){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 게시글이 존재하지 않습니다.")
        );
        if (!post.getPassword().equals(password))
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        post.update(postRequestDto);
        return new PostResponseDto(post);
    }
    public String deletePost(Long id, String password) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 게시글이 존재하지 않습니다.")
        );
        if (!post.getPassword().equals(password))
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        postRepository.delete(post);
        return "게시글을 삭제했습니다.";
    }
}
