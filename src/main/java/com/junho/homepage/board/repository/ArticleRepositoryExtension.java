package com.junho.homepage.board.repository;

import com.junho.homepage.board.dto.ArticleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleRepositoryExtension {
    Page<ArticleResponse> findAll(Long boardId, String keyword, Pageable pageable);
}
