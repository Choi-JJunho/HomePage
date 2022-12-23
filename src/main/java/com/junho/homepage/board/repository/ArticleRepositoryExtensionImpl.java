package com.junho.homepage.board.repository;

import com.junho.homepage.board.domain.Article;
import com.junho.homepage.board.domain.QArticle;
import com.junho.homepage.board.domain.QBoard;
import com.junho.homepage.board.dto.ArticleResponse;
import com.junho.homepage.board.dto.QArticleResponse;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
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

        JPAQuery<ArticleResponse> query = queryFactory
                .select(new QArticleResponse(
                        article.id,
                        article.creator.id,
                        article.modifier.id,
                        article.title,
                        article.description,
                        article.createDate,
                        article.updateDate
                ))
                .from(article)
                .where(
                        containTitle(keyword),
                        containDesc(keyword)
                );
        
        // TODO : Pagination 공통쿼리로 분리
        List<ArticleResponse> fetch = query.fetch();

        JPQLQuery<ArticleResponse> byPagination = Objects.requireNonNull(getQuerydsl())
                .applyPagination(pageable, query);

        return new PageImpl<>(byPagination.fetch(), pageable, fetch.size());
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
