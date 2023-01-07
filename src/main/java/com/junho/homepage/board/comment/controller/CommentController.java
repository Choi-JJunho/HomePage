package com.junho.homepage.board.comment.controller;

import com.junho.annotation.RoleUser;
import com.junho.homepage.board.comment.dto.request.CreateComment;
import com.junho.homepage.board.comment.dto.response.CommentResponse;
import com.junho.homepage.board.comment.repository.CommentRepository;
import com.junho.homepage.board.comment.service.CommentService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "댓글 API")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentRepository commentRepository;

    @GetMapping("/comment/{articleId}/list")
    public ResponseEntity<Page<CommentResponse>> list(@PathVariable Long articleId, String keyword, Pageable pageable) {

        return new ResponseEntity<>(commentService.getComments(articleId, keyword, pageable), HttpStatus.OK);
    }

    @RoleUser
    @PostMapping("/comment/post")
    public ResponseEntity<Boolean> post(CreateComment request) {

        return new ResponseEntity<>(commentService.postComment(request), HttpStatus.OK);
    }

    @RoleUser
    @PutMapping("/comment/{id}")
    public ResponseEntity<CommentResponse> modify(@PathVariable Long id, CreateComment request) {

        return new ResponseEntity<>(commentService.modifyComment(id, request), HttpStatus.OK);
    }

    @RoleUser
    @DeleteMapping("/comment/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {

        return new ResponseEntity<>(commentService.deleteComment(id), HttpStatus.OK);
    }

}
