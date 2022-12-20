package com.junho.homepage.article.repository;

import com.junho.homepage.article.Article;
import com.junho.homepage.article.QArticle;
import com.junho.homepage.article.dto.ArticleResponse;
import com.junho.homepage.article.dto.QArticleResponse;
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

import java.util.Objects;

@Repository
public class ArticleRepositoryExtensionImpl extends QuerydslRepositorySupport implements ArticleRepositoryExtension {

    private final JPAQueryFactory queryFactory;

    private final QArticle article = QArticle.article;

    public ArticleRepositoryExtensionImpl(JPAQueryFactory jpaQueryFactory) {
        super(Article.class);
        this.queryFactory = jpaQueryFactory;
    }

    @Override
    public Page<ArticleResponse> findAll(String keyword, Pageable pageable) {

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

        JPQLQuery<ArticleResponse> byPagination = Objects.requireNonNull(getQuerydsl())
                .applyPagination(pageable, query);

        return new PageImpl<>(byPagination.fetch(), pageable, pageable.getPageSize());
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
