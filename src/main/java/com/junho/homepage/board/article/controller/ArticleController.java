package com.junho.homepage.board.article.controller;

import com.junho.annotation.RoleUser;
import com.junho.homepage.board.article.dto.request.CreateArticle;
import com.junho.homepage.board.article.dto.response.ArticleResponse;
import com.junho.homepage.board.article.repository.ArticleRepository;
import com.junho.homepage.board.article.service.ArticleService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

@Api(tags = "게시물 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleRepository articleRepository;

    @GetMapping("/list")
    public ResponseEntity<Page<ArticleResponse>> list(String keyword, @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return new ResponseEntity<>(articleService.getArticles(keyword, pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> view(@PathVariable Long id) {

        return new ResponseEntity<>(articleService.getArticle(id), HttpStatus.OK);
    }

    @RoleUser
    @PostMapping("/post")
    public ResponseEntity<ArticleResponse> post(@RequestBody CreateArticle request) {

        return new ResponseEntity<>(articleService.postArticle(request), HttpStatus.OK);
    }

    @RoleUser
    @PutMapping("/{id}")
    public ResponseEntity<ArticleResponse> modify(@PathVariable Long id, @RequestBody CreateArticle request) {

        return new ResponseEntity<>(articleService.updateArticle(id, request), HttpStatus.OK);
    }

    @RoleUser
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {

        return new ResponseEntity<>(articleService.deleteArticle(id), HttpStatus.OK);
    }
}
