package com.hanghae.springlevelone.service;

import com.hanghae.springlevelone.dto.AuthRequestDto;
import com.hanghae.springlevelone.entity.User;
import com.hanghae.springlevelone.jwt.JwtUtil;
import com.hanghae.springlevelone.message.Message;
import com.hanghae.springlevelone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public ResponseEntity<Message> signup(AuthRequestDto authRequestDto) {
        String username = authRequestDto.getUsername();
        String password = authRequestDto.getPassword();

        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디 입니다.");
        }

        User user = new User(username, password);
        userRepository.save(user);

        Message message = new Message();
        message.setMsg("회원가입 성공");
        message.setStatusCode(HttpStatus.OK.value());
        return ResponseEntity.ok(message);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> login(AuthRequestDto authRequestDto, HttpServletResponse response) {
        String username = authRequestDto.getUsername();
        String password = authRequestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("아이디가 일치하지 않습니다.")
        );

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername()));

        Message message = new Message();
        message.setMsg("로그인 성공");
        message.setStatusCode(HttpStatus.OK.value());
        return ResponseEntity.ok(message);
    }
}