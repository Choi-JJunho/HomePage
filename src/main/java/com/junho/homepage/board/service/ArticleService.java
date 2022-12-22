package com.junho.homepage.board.service;

import com.junho.homepage.board.domain.Article;
import com.junho.homepage.board.dto.ArticleRequest;
import com.junho.homepage.board.dto.ArticleResponse;
import com.junho.homepage.board.mapper.ArticleMapper;
import com.junho.homepage.board.repository.ArticleRepository;
import com.junho.homepage.board.repository.BoardRepository;
import com.junho.homepage.member.domain.Member;
import com.junho.support.error.ErrorCode;
import com.junho.support.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final BoardRepository boardRepository;

    public ArticleResponse getArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.ARTICLE_NOT_EXIST));
        article.increaseViewCount();
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

    public Page<ArticleResponse> getArticles(Long boardId, String keyword, Pageable pageable) {
        return articleRepository.findAll(boardId, keyword, pageable);
    }
}
