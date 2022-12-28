package com.junho.homepage.board.comment.controller;

import com.junho.homepage.board.comment.dto.request.CreateComment;
import com.junho.homepage.board.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment/post")
    public ResponseEntity<Boolean> postComment(CreateComment request) {
        return new ResponseEntity<>(commentService.postComment(request), HttpStatus.OK);
    }

    @PutMapping("/comment")
    public ResponseEntity<Boolean> modifyComment(CreateComment request) {
        return new ResponseEntity<>(commentService.postComment(request), HttpStatus.OK);
    }


}
