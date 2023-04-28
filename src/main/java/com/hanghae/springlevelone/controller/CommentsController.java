package com.hanghae.springlevelone.controller;

import com.hanghae.springlevelone.dto.CommentsRequestDto;
import com.hanghae.springlevelone.dto.CommentsResponseDto;
import com.hanghae.springlevelone.service.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blog/comment")
public class CommentsController {
    private final CommentsService commentsService;

    @PostMapping("/{id}")
    public ResponseEntity<Object> createComment(@RequestBody CommentsRequestDto commentsRequestDto, @PathVariable Long id, HttpServletRequest request)   {
        return commentsService.createComment(commentsRequestDto, id, request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateComment(@RequestBody CommentsRequestDto commentsRequestDto, @PathVariable Long id, HttpServletRequest request)   {
        return commentsService.updateComment(commentsRequestDto, id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteComment(@PathVariable Long id, HttpServletRequest request) {
        return commentsService.deleteComment(id, request);
    }
}
