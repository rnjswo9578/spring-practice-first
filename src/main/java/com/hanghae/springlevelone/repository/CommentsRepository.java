package com.hanghae.springlevelone.repository;

import com.hanghae.springlevelone.entity.Comment;
import com.hanghae.springlevelone.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostIdOrderByCreatedAtDesc(Long id);
}
