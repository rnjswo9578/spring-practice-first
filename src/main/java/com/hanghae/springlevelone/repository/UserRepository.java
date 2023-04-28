package com.hanghae.springlevelone.repository;

import com.hanghae.springlevelone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
