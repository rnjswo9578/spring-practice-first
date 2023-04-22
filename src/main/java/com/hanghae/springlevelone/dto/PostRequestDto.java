package com.hanghae.springlevelone.dto;

import lombok.Getter;

@Getter
public class PostRequestDto {
    private Long id;
    private String title;
    private String username;
    private String password;
    private String post;
}
