package com.hanghae.springlevelone.controller;

import com.hanghae.springlevelone.dto.AuthRequestDto;
import com.hanghae.springlevelone.message.Message;
import com.hanghae.springlevelone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Message> signup(@RequestBody @Valid AuthRequestDto authRequestDto) {
        return userService.signup(authRequestDto);
    }

    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<Message> login(@RequestBody AuthRequestDto authRequestDto, HttpServletResponse response) {
        return userService.login(authRequestDto, response);
    }
}
