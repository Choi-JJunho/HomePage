package com.junho.homepage.article.controller;

import com.junho.annotation.RoleUser;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> view(@PathVariable Long id) {
        return new ResponseEntity<>(articleService.getArticle(id), HttpStatus.OK);
    }

    @RoleUser
    @PostMapping("/post")
    public ResponseEntity<ArticleResponse> post(@RequestBody ArticleRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        Object principal = authentication.getPrincipal();
        System.out.println(principal);
        String name = authentication.getName();
        return new ResponseEntity<>(articleService.postArticle(request, (Member) principal), HttpStatus.OK);
    }

}
