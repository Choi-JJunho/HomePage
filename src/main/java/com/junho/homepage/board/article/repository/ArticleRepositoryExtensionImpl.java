package com.junho.homepage.board.article.repository;

import com.junho.homepage.board.article.Article;
import com.junho.homepage.board.article.QArticle;
import com.junho.homepage.board.article.dto.response.ArticleResponse;
import com.junho.support.CustomQueryDslSupport;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ArticleRepositoryExtensionImpl extends CustomQueryDslSupport implements ArticleRepositoryExtension {

    private final JPAQueryFactory queryFactory;

    private final QArticle article = QArticle.article;

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
                        find(keyword, article.title, article.description)
                );

        Page<Article> page = page(query, pageable);

        // TODO : QueryProjection vs 가져와서 변환?
        List<ArticleResponse> response = page.stream()
                .map(ArticleMapper.INSTANCE::toArticleResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(response, pageable, page.getTotalElements());
    }
}
