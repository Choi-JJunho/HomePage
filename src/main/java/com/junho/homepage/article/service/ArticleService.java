package com.junho.homepage.article.service;

import com.junho.config.support.error.ErrorCode;
import com.junho.config.support.exception.ApiException;
import com.junho.homepage.article.Article;
import com.junho.homepage.article.dto.ArticleRequest;
import com.junho.homepage.article.dto.ArticleResponse;
import com.junho.homepage.article.mapper.ArticleMapper;
import com.junho.homepage.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleResponse getArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.ARTICLE_NOT_EXIST));
        return ArticleMapper.INSTANCE.toArticleResponse(article);
    }

    public ArticleResponse postArticle(ArticleRequest request) {
        Article article = ArticleMapper.INSTANCE.toArticle(request);
        articleRepository.save(article);
        return ArticleMapper.INSTANCE.toArticleResponse(article);
    }
}
