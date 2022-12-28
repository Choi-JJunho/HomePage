package com.junho.homepage.board.controller;

import com.junho.annotation.RoleAdmin;
import com.junho.homepage.board.Board;
import com.junho.homepage.board.article.dto.response.ArticleResponse;
import com.junho.homepage.board.article.service.ArticleService;
import com.junho.homepage.board.dto.request.CreateBoard;
import com.junho.homepage.board.dto.response.BoardResponse;
import com.junho.homepage.board.repository.BoardRepository;
import com.junho.homepage.board.service.BoardService;
import com.junho.support.error.ErrorCode;
import com.junho.support.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final BoardRepository boardRepository;
    private final ArticleService articleService;

    @GetMapping("/list")
    public ResponseEntity<List<BoardResponse>> list() {
        return new ResponseEntity<>(boardService.getBoards(), HttpStatus.OK);
    }

    @GetMapping("/{boardId}/list")
    public ResponseEntity<Page<ArticleResponse>> list(@PathVariable Long boardId, String keyword, @PageableDefault Pageable pageable) {
        return new ResponseEntity<>(articleService.getArticles(boardId, keyword, pageable), HttpStatus.OK);
    }

    @RoleAdmin
    @PostMapping("/post")
    public ResponseEntity<Boolean> post(@RequestBody CreateBoard request) {

        return new ResponseEntity<>(boardService.createBoard(request), HttpStatus.OK);
    }

    @RoleAdmin
    @PutMapping("/{id}")
    public ResponseEntity<BoardResponse> update(@PathVariable Long id, @RequestBody CreateBoard request) {

        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.BOARD_NOT_EXIST));

        return new ResponseEntity<>(boardService.updateBoard(board, request), HttpStatus.OK);
    }

    @RoleAdmin
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {

        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.ARTICLE_NOT_EXIST));

        return new ResponseEntity<>(boardService.deleteBoard(board), HttpStatus.OK);
    }
}
