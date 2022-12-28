package com.junho.support;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Objects;

public abstract class CustomQueryDslSupport extends QuerydslRepositorySupport {

    public CustomQueryDslSupport(Class<?> domainClass) {
        super(domainClass);
    }

    public <C> BooleanExpression eq(SimpleExpression<C> columnExp, C right) {
        if (columnExp == null || right == null)
            return null;
        return columnExp.eq(right);
    }

    public <C> BooleanExpression in(SimpleExpression<C> columnExp, List<C> rights) {
        if (rights == null || rights.size() == 0)
            return null;
        return columnExp.in(rights);
    }

    public BooleanExpression find(String keyword, StringPath... stringPaths) {
        if (keyword == null)
            return null;

        BooleanExpression expression = null;
        for (StringPath path : stringPaths) {

            BooleanExpression exp = null;

            for (String word : keyword.split(" ")) {
                if (exp == null)
                    exp = path.containsIgnoreCase(word);
                else
                    exp = exp.and(path.containsIgnoreCase(word));
            }

            if (expression == null)
                expression = exp;
            else
                expression = expression.or(exp);
        }
        return expression;
    }

    public <T> Page<T> page(JPQLQuery<T> query, Pageable pageable) {

        JPQLQuery<T> pageableQuery = Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query);
        QueryResults<T> fetchResults = pageableQuery.fetchResults();
        return new PageImpl<>(fetchResults.getResults(), pageable, fetchResults.getTotal());
    }

    public <T, C> Page<T> page(JPQLQuery<T> query, JPQLQuery<C> count, Pageable pageable) {
        
        JPQLQuery<T> pageableQuery = Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query);
        return PageableExecutionUtils.getPage(pageableQuery.fetch(), pageable, count::fetchCount);
    }
}
