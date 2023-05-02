package com.hanghae.springlevelone.controller;

import com.hanghae.springlevelone.dto.CommentsRequestDto;
import com.hanghae.springlevelone.security.UserDetailsImpl;
import com.hanghae.springlevelone.service.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequiredArgsConstructor
@RequestMapping("/blog/comment")
public class CommentsController {
    private final CommentsService commentsService;

    @PostMapping("/{id}")
    public ResponseEntity<Object> createComment(@RequestBody CommentsRequestDto commentsRequestDto, @PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails)   {
        return commentsService.createComment(commentsRequestDto, id, userDetails.getUsername());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateComment(@RequestBody CommentsRequestDto commentsRequestDto, @PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails)   {
        return commentsService.updateComment(commentsRequestDto, id, userDetails.getUser());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentsService.deleteComment(id, userDetails.getUser());
    }
}
