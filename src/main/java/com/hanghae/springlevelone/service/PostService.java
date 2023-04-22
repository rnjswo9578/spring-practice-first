package com.hanghae.springlevelone.service;

import com.hanghae.springlevelone.dto.PostRequestDto;
import com.hanghae.springlevelone.dto.PostResponseDto;
import com.hanghae.springlevelone.entity.Post;
import com.hanghae.springlevelone.entity.User;
import com.hanghae.springlevelone.jwt.JwtUtil;
import com.hanghae.springlevelone.repository.PostRepository;
import com.hanghae.springlevelone.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("존재하지 않는 아이디 입니다.")
            );

            Post post = postRepository.saveAndFlush(new Post(postRequestDto, user.getUsername(), user.getPassword()));
            return new PostResponseDto(post);
        } else {
            return null;
        }
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getPostList() {
        return postRepository.findAllByOrderByCreatedAtDesc().stream().map(PostResponseDto::new).collect(Collectors.toList());
    }
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 게시글이 존재하지 않습니다.")
        );
        return new PostResponseDto(post);
    }

    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto, String password){
        Post post = checkPost(id);
        checkPassword(post, password);
        post.update(postRequestDto);
        return new PostResponseDto(post);
    }
    public String deletePost(Long id, String password) {
        Post post = checkPost(id);
        checkPassword(post, password);
        postRepository.delete(post);
        return "게시글을 삭제했습니다.";
    }

    public Post checkPost(Long id){
        return postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 게시글이 존재하지 않습니다.")
        );
    }
    public void checkPassword(Post post, String password){
        if (!post.getPassword().equals(password))
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
    }
}
