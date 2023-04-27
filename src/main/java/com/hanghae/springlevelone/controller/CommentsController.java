package com.hanghae.springlevelone.controller;

import com.hanghae.springlevelone.dto.CommentsRequestDto;
import com.hanghae.springlevelone.dto.CommentsResponseDto;
import com.hanghae.springlevelone.service.CommentsService;
import lombok.RequiredArgsConstructor;
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
    public CommentsResponseDto createComment(@RequestBody CommentsRequestDto commentsRequestDto, @PathVariable Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return commentsService.createComment(commentsRequestDto, id, request, response);
    }

    @PutMapping("/{id}")
    public CommentsResponseDto updateComment(@RequestBody CommentsRequestDto commentsRequestDto, @PathVariable Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return commentsService.updateComment(commentsRequestDto, id, request, response);
    }

    @DeleteMapping("/{id}")
    public String deleteComment(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return commentsService.deleteComment(id, request, response);
    }
}
