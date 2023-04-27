package com.hanghae.springlevelone.entity;

import com.hanghae.springlevelone.dto.CommentsRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private String username;

    @ManyToOne
    private Post post;

    public Comment(CommentsRequestDto commentsRequestDto, String subject) {
        this.comment = commentsRequestDto.getComment();
        this.username = subject;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void update(CommentsRequestDto commentsRequestDto){
        this.comment = commentsRequestDto.getComment();
    }
}
