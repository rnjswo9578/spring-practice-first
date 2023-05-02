package com.hanghae.springlevelone.service;

import com.hanghae.springlevelone.dto.PostRequestDto;
import com.hanghae.springlevelone.dto.PostResponseDto;
import com.hanghae.springlevelone.entity.Post;
import com.hanghae.springlevelone.entity.User;
import com.hanghae.springlevelone.entity.UserOrAdminEnum;
import com.hanghae.springlevelone.jwt.JwtUtil;
import com.hanghae.springlevelone.repository.PostRepository;
import com.hanghae.springlevelone.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<Object> createPost(PostRequestDto postRequestDto, User user) {
//        Claims claims = getClaimsFromToken(request);
//        if (claims == null) {
//            return ResponseEntity.badRequest().body("토큰이 유효하지 않습니다.");
//        }

        Post post = postRepository.saveAndFlush(new Post(postRequestDto));
        post.setUser(user);

        return ResponseEntity.ok().body(new PostResponseDto(post));
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
    public ResponseEntity<Object> updatePost(Long id, PostRequestDto postRequestDto, User user) {
//        Claims claims = getClaimsFromToken(request);
//        if (claims == null) {
//            return ResponseEntity.badRequest().body("토큰이 유효하지 않습니다.");
//        }
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("post 가 없습니다!")
        );

        UserOrAdminEnum userOrAdminEnum = user.getAdmin();

        if (!post.getUser().getUsername().equals(user.getUsername())) {
            if (userOrAdminEnum != UserOrAdminEnum.ADMIN) {
                return ResponseEntity.badRequest().body("수정 / 삭제 권한이 없습니다.");
            }
        }

        post.update(postRequestDto);
        return ResponseEntity.ok().body(new PostResponseDto(post));
    }

    public ResponseEntity<Object> deletePost(Long id, User user) {

        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("post 가 없습니다!")
        );

        UserOrAdminEnum userOrAdminEnum = user.getAdmin();

        if (!post.getUser().getUsername().equals(user.getUsername())) {
            if (userOrAdminEnum != UserOrAdminEnum.ADMIN) {
                return ResponseEntity.badRequest().body("수정 / 삭제 권한이 없습니다.");
            }
        }

        postRepository.delete(post);
        return ResponseEntity.ok().body("게시글이 삭제됐습니다.");
    }

    //method 분리-------------------------------------------------------------------------------------
    //post_id로 게시글 조회.
    public Post checkPost(Long id){
        return postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 게시글이 존재하지 않습니다.")
        );
    }

    //토큰 유효성 검사 후 claims 반환
    private Claims getClaimsFromToken(HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        if (token == null || !jwtUtil.validateToken(token)) {
            return null;
        }
        return jwtUtil.getInfoFromToken(token);
    }

    //사용자 유효성 검사
//    public boolean userCheck(Post post, Claims claims) {
//        if (!post.getUser().getUsername().equals(claims.getSubject())) {
//            if (claims.get("auth").equals("ADMIN")) {
//                return true;
//            } else {
//                return false;
//            }
//        }
//        return true;
//    }
}
