package com.hanghae.springlevelone.service;

import com.hanghae.springlevelone.dto.PostRequestDto;
import com.hanghae.springlevelone.dto.PostResponseDto;
import com.hanghae.springlevelone.entity.Post;
import com.hanghae.springlevelone.entity.User;
import com.hanghae.springlevelone.jwt.JwtUtil;
import com.hanghae.springlevelone.message.Message;
import com.hanghae.springlevelone.repository.PostRepository;
import com.hanghae.springlevelone.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

            Post post = postRepository.saveAndFlush(new Post(postRequestDto, user));

            return new PostResponseDto(post);
        } else {
            throw new NullPointerException("토큰이 존재하지 않습니다.");
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

    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            Post post = checkPost(id);
            if (post.getUsername().equals(claims.getSubject())) {
                post.update(postRequestDto);
            } else {
                throw new IllegalArgumentException("작성자가 아니면 수정할 수 없습니다.");
            }

            return new PostResponseDto(post);

        } else {
            throw new NullPointerException("토큰이 존재하지 않습니다.");
        }
    }

    public ResponseEntity<Message> deletePost(Long id, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            Post post = checkPost(id);
            if (post.getUsername().equals(claims.getSubject())) {
                postRepository.delete(post);
            } else {
                throw new IllegalArgumentException("작성자가 아니면 삭제할 수 없습니다.");
            }

            Message message = new Message();
            message.setMsg("게시글이 삭제됐습니다.");
            message.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok(message);
        } else {
            throw new NullPointerException("토큰이 존재하지 않습니다.");
        }
    }

    public Post checkPost(Long id){
        return postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 게시글이 존재하지 않습니다.")
        );
    }
}
