package com.hanghae.springlevelone.entity;

import com.hanghae.springlevelone.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Post{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String username;
    private String password;
    private String post;
    @CreationTimestamp
    private LocalDateTime createdAt = LocalDateTime.now();

    public Post(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.username = postRequestDto.getUsername();
        this.password = postRequestDto.getPassword();
        this.post = postRequestDto.getPost();
    }

    public void update(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.username = postRequestDto.getUsername();
        this.post = postRequestDto.getPost();
    }
}
