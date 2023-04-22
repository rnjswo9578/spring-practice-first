package com.hanghae.springlevelone.dto;

import com.hanghae.springlevelone.entity.Post;
import com.hanghae.springlevelone.entity.Timestamped;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private final Long id;
    private final String title;
    private final String username;
    private final String post;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.username = post.getUsername();
        this.post = post.getPost();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }
}
