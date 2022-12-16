package com.junho.homepage.article.controller;

import com.junho.annotation.RoleUser;
import com.junho.config.security.CustomUserDetails;
import com.junho.config.support.error.ErrorCode;
import com.junho.config.support.exception.ApiException;
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
        
        // TODO : Authentication 내부 인증정보 가져오는 로직 공통으로 분리
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new ApiException(ErrorCode.AUTHENTICATION_INVALID);
        }
        if(!(authentication.getPrincipal() instanceof CustomUserDetails)) {
            throw new ApiException(ErrorCode.AUTHENTICATION_INVALID);
        }

        Member currentMember = ((CustomUserDetails) authentication.getPrincipal()).getMember();

        return new ResponseEntity<>(articleService.postArticle(request, currentMember), HttpStatus.OK);
    }

}
