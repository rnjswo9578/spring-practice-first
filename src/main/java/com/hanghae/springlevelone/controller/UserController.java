package com.hanghae.springlevelone.controller;

import com.hanghae.springlevelone.dto.LoginRequestDto;
import com.hanghae.springlevelone.dto.SignupRequestDto;
import com.hanghae.springlevelone.message.Message;
import com.hanghae.springlevelone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public String signup(@RequestBody @Valid SignupRequestDto signupRequestDto, HttpServletResponse response) throws IOException {
        return userService.signup(signupRequestDto, response);
    }

    @ResponseBody
    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) throws IOException {
        return userService.login(loginRequestDto, response);
    }
}
