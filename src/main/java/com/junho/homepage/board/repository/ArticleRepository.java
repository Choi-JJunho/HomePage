package com.junho.homepage.board.repository;

import com.junho.homepage.board.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleRepositoryExtension {
}
