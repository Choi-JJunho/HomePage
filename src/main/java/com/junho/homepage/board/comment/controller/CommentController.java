package com.junho.homepage.board.comment.controller;

import com.junho.annotation.RoleUser;
import com.junho.homepage.board.comment.Comment;
import com.junho.homepage.board.comment.dto.request.CreateComment;
import com.junho.homepage.board.comment.dto.response.CommentResponse;
import com.junho.homepage.board.comment.repository.CommentRepository;
import com.junho.homepage.board.comment.service.CommentService;
import com.junho.support.error.ErrorCode;
import com.junho.support.exception.ApiException;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "댓글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    private final CommentRepository commentRepository;

    @RoleUser
    @PostMapping("/post")
    public ResponseEntity<Boolean> post(CreateComment request) {
        return new ResponseEntity<>(commentService.postComment(request), HttpStatus.OK);
    }

    @RoleUser
    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> modify(@PathVariable Long id, CreateComment request) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.COMMENT_NOT_EXIST));

        return new ResponseEntity<>(commentService.modifyComment(comment, request), HttpStatus.OK);
    }

    @RoleUser
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.COMMENT_NOT_EXIST));

        return new ResponseEntity<>(commentService.deleteComment(comment), HttpStatus.OK);
    }

}
