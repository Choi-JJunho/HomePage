package com.junho.homepage.board.article.service;

import com.junho.homepage.board.Board;
import com.junho.homepage.board.article.Article;
import com.junho.homepage.board.article.dto.request.CreateArticle;
import com.junho.homepage.board.article.dto.response.ArticleResponse;
import com.junho.homepage.board.article.repository.ArticleMapper;
import com.junho.homepage.board.article.repository.ArticleRepository;
import com.junho.homepage.board.repository.BoardRepository;
import com.junho.homepage.member.Member;
import com.junho.support.error.ErrorCode;
import com.junho.support.exception.ApiException;
import com.junho.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

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

    public ArticleResponse postArticle(CreateArticle request) {

        Board board = boardRepository.findById(request.getBoardId())
                .orElseThrow(() -> new ApiException(ErrorCode.BOARD_NOT_EXIST));

        Article article = Article.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .board(board)
                .build();

        articleRepository.save(article);
        return ArticleMapper.INSTANCE.toArticleResponse(article);
    }

    public ArticleResponse updateArticle(Long id, CreateArticle request) {

        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.ARTICLE_NOT_EXIST));

        article.update(request);
        return ArticleMapper.INSTANCE.toArticleResponse(article);
    }

    public boolean deleteArticle(Long id) {

        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.ARTICLE_NOT_EXIST));

        Member currentMember = AuthUtils.getCurrentMember();

        if (!Objects.equals(currentMember.getId(), article.getCreator().getId())) {
            throw new ApiException(ErrorCode.AUTHORIZATION_INVALID);
        }

        article.setEnabled(false);
        return true;
    }

    public Page<ArticleResponse> getArticles(Long boardId, String keyword, Pageable pageable) {
        return articleRepository.findAll(boardId, keyword, pageable);
    }

    public Page<ArticleResponse> getArticles(String keyword, Pageable pageable) {
        return articleRepository.findAll(keyword, pageable);
    }
}
