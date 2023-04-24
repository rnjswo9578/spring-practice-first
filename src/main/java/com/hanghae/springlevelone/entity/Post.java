package com.hanghae.springlevelone.entity;

import com.hanghae.springlevelone.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Post extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Post(PostRequestDto postRequestDto, User user) {
        this.title = postRequestDto.getTitle();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.post = postRequestDto.getPost();
        this.user = user;
    }

    public void update(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.post = postRequestDto.getPost();
    }
}
