package com.hanghae.springlevelone.service;

import com.hanghae.springlevelone.dto.LoginRequestDto;
import com.hanghae.springlevelone.dto.SignupRequestDto;
import com.hanghae.springlevelone.entity.User;
import com.hanghae.springlevelone.entity.UserOrAdminEnum;
import com.hanghae.springlevelone.jwt.JwtUtil;
import com.hanghae.springlevelone.message.Message;
import com.hanghae.springlevelone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public String signup(SignupRequestDto signupRequestDto, HttpServletResponse response) throws IOException {
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();

        if (userRepository.findByUsername(username).isPresent()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "중복된 아이디 입니다.");
            return null;
        }
        UserOrAdminEnum userOrAdmin = UserOrAdminEnum.USER;
        if (signupRequestDto.isAdmin()) {
            userOrAdmin = UserOrAdminEnum.ADMIN;
        }
        User user = new User(username, password, userOrAdmin);
        userRepository.save(user);

        return HttpStatus.OK.value()+" 회원가입 완료";
    }

    @Transactional(readOnly = true)
    public String login(LoginRequestDto loginRequestDto, HttpServletResponse response) throws IOException {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("아이디가 일치하지 않습니다.")
        );

        if (!user.getPassword().equals(password) || !user.getUsername().equals(username)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "회원 정보를 찾을 수 없습니다.");
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getAdmin()));

        return HttpStatus.OK.value()+ " 로그인 성공";
    }
}