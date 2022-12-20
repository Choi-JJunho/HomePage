package com.junho.homepage.article.repository;

import com.junho.homepage.article.dto.ArticleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleRepositoryExtension {
    Page<ArticleResponse> findAll(String keyword, Pageable pageable);
}
