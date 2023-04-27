package com.hanghae.springlevelone.dto;

import com.hanghae.springlevelone.entity.Comment;
import lombok.Getter;

@Getter
public class CommentsResponseDto {
    private final String comment;
    private final String username;

    public CommentsResponseDto(Comment comment) {
        this.comment = comment.getComment();
        this.username = comment.getUsername();
    }
}
