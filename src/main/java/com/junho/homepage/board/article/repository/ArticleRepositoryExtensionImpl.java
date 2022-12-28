package com.junho.homepage.board.article.repository;

import com.junho.homepage.board.QBoard;
import com.junho.homepage.board.article.Article;
import com.junho.homepage.board.article.QArticle;
import com.junho.homepage.board.article.dto.response.ArticleResponse;
import com.junho.homepage.board.article.mapper.ArticleMapper;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class ArticleRepositoryExtensionImpl extends QuerydslRepositorySupport implements ArticleRepositoryExtension {

    private final JPAQueryFactory queryFactory;

    private final QArticle article = QArticle.article;
    private final QBoard board = QBoard.board;

    public ArticleRepositoryExtensionImpl(JPAQueryFactory jpaQueryFactory) {
        super(Article.class);
        this.queryFactory = jpaQueryFactory;
    }

    @Override
    public Page<ArticleResponse> findAll(Long boardId, String keyword, Pageable pageable) {

        JPAQuery<Article> query = queryFactory
                .select(article)
                .from(article)
                .where(
                        article.board.id.eq(boardId),
                        containTitle(keyword),
                        containDesc(keyword)
                );

        // TODO : Pagination 공통쿼리로 분리
        List<Article> fetch = query.fetch();

        List<Article> byPagination = Objects.requireNonNull(getQuerydsl())
                .applyPagination(pageable, query).fetch();

        List<ArticleResponse> response = byPagination.stream()
                .map(ArticleMapper.INSTANCE::toArticleResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(response, pageable, fetch.size());
    }

    private BooleanExpression containTitle(String keyword) {
        if (StringUtils.isBlank(keyword)) {
            return null;
        }
        return article.title.containsIgnoreCase(keyword);
    }

    private BooleanExpression containDesc(String keyword) {
        if (StringUtils.isBlank(keyword)) {
            return null;
        }
        return article.title.containsIgnoreCase(keyword);
    }
}
