package com.hanghae.springlevelone.entity;

import com.hanghae.springlevelone.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne()
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    List<Comment> commentsList = new ArrayList<>();

    public Post(PostRequestDto postRequestDto, User user) {
        this.title = postRequestDto.getTitle();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.post = postRequestDto.getPost();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void update(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.post = postRequestDto.getPost();
    }

    public void addComment(Comment comment) {
        this.commentsList.add(comment);
    }
}
