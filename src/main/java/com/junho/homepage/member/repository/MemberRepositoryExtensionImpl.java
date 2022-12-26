package com.junho.homepage.member.repository;

import com.junho.homepage.member.domain.Member;
import com.junho.homepage.member.domain.QMember;
import com.junho.homepage.member.dto.response.MemberResponse;
import com.junho.homepage.member.mapper.MemberMapper;
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
public class MemberRepositoryExtensionImpl extends QuerydslRepositorySupport implements MemberRepositoryExtension {

    private final JPAQueryFactory queryFactory;

    private final QMember member = QMember.member;

    public MemberRepositoryExtensionImpl(JPAQueryFactory queryFactory) {
        super(Member.class);
        this.queryFactory = queryFactory;
    }

    public Page<MemberResponse> findAll(String keyword, Pageable pageable) {

        JPAQuery<Member> query = queryFactory.select(member)
                .from(member)
                .where(
                        containName(keyword)
                );

        // TODO : Pagination 공통쿼리로 분리
        List<Member> fetch = query.fetch();

        List<Member> byPagination = Objects.requireNonNull(getQuerydsl())
                .applyPagination(pageable, query).fetch();

        List<MemberResponse> response = byPagination.stream()
                .map(MemberMapper.INSTANCE::toMemberResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(response, pageable, fetch.size());
    }

    private BooleanExpression containName(String keyword) {
        if (StringUtils.isBlank(keyword)) {
            return null;
        }
        return member.name.containsIgnoreCase(keyword);
    }

}
