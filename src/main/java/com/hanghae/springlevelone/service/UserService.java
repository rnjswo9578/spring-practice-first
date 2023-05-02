package com.hanghae.springlevelone.service;

import com.hanghae.springlevelone.dto.LoginRequestDto;
import com.hanghae.springlevelone.dto.SignupRequestDto;
import com.hanghae.springlevelone.entity.User;
import com.hanghae.springlevelone.entity.UserOrAdminEnum;
import com.hanghae.springlevelone.jwt.JwtUtil;
import com.hanghae.springlevelone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
    public ResponseEntity<Object> signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());

        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            return ResponseEntity.badRequest().body("중복된 아이디 입니다.");
        }

        UserOrAdminEnum userOrAdmin = UserOrAdminEnum.USER;
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            userOrAdmin = UserOrAdminEnum.ADMIN;
        }

        User user = new User(username, password, userOrAdmin);
        userRepository.save(user);

        return ResponseEntity.ok().body("회원가입 성공");
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("아이디 정보를 찾을 수 없습니다.")
        );

        if(!passwordEncoder.matches(password, user.getPassword())){
            return ResponseEntity.badRequest().body("비밀번호를 확인해주세요.");
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getAdmin()));

        return ResponseEntity.ok().body("로그인 성공");
    }
}