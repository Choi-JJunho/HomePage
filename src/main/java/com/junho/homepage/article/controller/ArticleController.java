package com.junho.homepage.article.controller;

import com.junho.annotation.RoleUser;
import com.junho.config.security.CustomUserDetails;
import com.junho.config.support.error.ErrorCode;
import com.junho.config.support.exception.ApiException;
import com.junho.homepage.article.Article;
import com.junho.homepage.article.dto.ArticleRequest;
import com.junho.homepage.article.dto.ArticleResponse;
import com.junho.homepage.article.repository.ArticleRepository;
import com.junho.homepage.article.service.ArticleService;
import com.junho.homepage.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<ArticleResponse> post(@RequestBody ArticleRequest request) {

        Member currentMember = getCurrentMember();

        return new ResponseEntity<>(articleService.postArticle(request, currentMember), HttpStatus.OK);
    }

    @RoleUser
    @PutMapping("/{id}/update")
    public ResponseEntity<ArticleResponse> update(@PathVariable Long id, @RequestBody ArticleRequest request) {

        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.ARTICLE_NOT_EXIST));

        Member currentMember = getCurrentMember();

        return new ResponseEntity<>(articleService.updateArticle(article, request), HttpStatus.OK);
    }

    @RoleUser
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {

        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.ARTICLE_NOT_EXIST));

        Member currentMember = getCurrentMember();

        if (!Objects.equals(currentMember.getId(), article.getMember().getId())) {
            throw new ApiException(ErrorCode.AUTHORIZATION_INVALID);
        }

        return new ResponseEntity<>(articleService.deleteArticle(article, currentMember), HttpStatus.OK);
    }

    // TODO : Authentication 내부 인증정보 가져오는 로직 다른 도메인에서도 사용할 수 있도록 공통으로 분리
    private Member getCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new ApiException(ErrorCode.AUTHENTICATION_INVALID);
        }
        if (!(authentication.getPrincipal() instanceof CustomUserDetails)) {
            throw new ApiException(ErrorCode.AUTHENTICATION_INVALID);
        }

        return ((CustomUserDetails) authentication.getPrincipal()).getMember();
    }

}
