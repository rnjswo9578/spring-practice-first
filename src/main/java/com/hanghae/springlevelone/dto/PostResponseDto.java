package com.hanghae.springlevelone.dto;

import com.hanghae.springlevelone.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
//    private Long id;
    private String title;
    private String username;
    private String post;
    private LocalDateTime createdAt;

    public PostResponseDto(Post post) {
//        this.id = post.getId();
        this.title = post.getTitle();
        this.username = post.getUsername();
        this.post = post.getPost();
        this.createdAt = post.getCreatedAt();
    }
}
