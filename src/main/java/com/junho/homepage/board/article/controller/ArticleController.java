package com.junho.homepage.board.article.controller;

import com.junho.annotation.RoleUser;
import com.junho.homepage.board.article.Article;
import com.junho.homepage.board.article.dto.request.CreateArticle;
import com.junho.homepage.board.article.dto.response.ArticleResponse;
import com.junho.homepage.board.article.repository.ArticleRepository;
import com.junho.homepage.board.article.service.ArticleService;
import com.junho.homepage.member.Member;
import com.junho.support.error.ErrorCode;
import com.junho.support.exception.ApiException;
import com.junho.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
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

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleRepository articleRepository;

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

        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.ARTICLE_NOT_EXIST));

        return new ResponseEntity<>(articleService.updateArticle(article, request), HttpStatus.OK);
    }

    @RoleUser
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {

        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.ARTICLE_NOT_EXIST));

        Member currentMember = AuthUtils.getCurrentMember();

        if (!Objects.equals(currentMember.getId(), article.getCreator().getId())) {
            throw new ApiException(ErrorCode.AUTHORIZATION_INVALID);
        }

        return new ResponseEntity<>(articleService.deleteArticle(article), HttpStatus.OK);
    }
}
