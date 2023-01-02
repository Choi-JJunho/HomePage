package com.junho.homepage.board.article.repository;

import com.junho.homepage.board.article.dto.response.ArticleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleRepositoryExtension {
    Page<ArticleResponse> findAll(Long boardId, String keyword, Pageable pageable);

    Page<ArticleResponse> findAll(String keyword, Pageable pageable);
}
