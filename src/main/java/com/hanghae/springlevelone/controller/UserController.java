package com.hanghae.springlevelone.controller;

import com.hanghae.springlevelone.dto.LoginRequestDto;
import com.hanghae.springlevelone.dto.SignupRequestDto;
import com.hanghae.springlevelone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/blog/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        return userService.signup(signupRequestDto);
    }

    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }
}
