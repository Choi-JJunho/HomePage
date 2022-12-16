package com.junho.homepage.article.service;

import com.junho.config.support.error.ErrorCode;
import com.junho.config.support.exception.ApiException;
import com.junho.homepage.article.Article;
import com.junho.homepage.article.dto.ArticleRequest;
import com.junho.homepage.article.dto.ArticleResponse;
import com.junho.homepage.article.mapper.ArticleMapper;
import com.junho.homepage.article.repository.ArticleRepository;
import com.junho.homepage.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleResponse getArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.ARTICLE_NOT_EXIST));
        return ArticleMapper.INSTANCE.toArticleResponse(article);
    }

    public ArticleResponse postArticle(ArticleRequest request, Member writer) {
        Article article = ArticleMapper.INSTANCE.toArticle(request, writer);
        articleRepository.save(article);
        return ArticleMapper.INSTANCE.toArticleResponse(article);
    }

    public ArticleResponse updateArticle(Article article, ArticleRequest request) {
        article.update(request);
        return ArticleMapper.INSTANCE.toArticleResponse(article);
    }

    public boolean deleteArticle(Article article, Member currentMember) {
        articleRepository.deleteById(article.getId());
        return true;
    }
}
