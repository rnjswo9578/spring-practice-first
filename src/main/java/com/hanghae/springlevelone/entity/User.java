package com.hanghae.springlevelone.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserOrAdminEnum admin;

    @OneToMany(mappedBy = "user")
    List<Post> posts = new ArrayList<>();

    public User(String username, String password, UserOrAdminEnum userOrAdminEnum) {
        this.username = username;
        this.password = password;
        this.admin = userOrAdminEnum;
    }
}
