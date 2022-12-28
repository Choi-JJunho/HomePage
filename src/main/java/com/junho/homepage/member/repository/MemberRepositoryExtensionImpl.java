package com.junho.homepage.member.repository;

import com.junho.homepage.member.Member;
import com.junho.homepage.member.QMember;
import com.junho.homepage.member.dto.response.MemberResponse;
import com.junho.support.CustomQueryDslSupport;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MemberRepositoryExtensionImpl extends CustomQueryDslSupport implements MemberRepositoryExtension {

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

        Page<Member> page = page(query, pageable);

        List<MemberResponse> response = page.stream()
                .map(MemberMapper.INSTANCE::toMemberResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(response, pageable, page.getTotalElements());
    }

    private BooleanExpression containName(String keyword) {
        if (StringUtils.isBlank(keyword)) {
            return null;
        }
        return member.name.containsIgnoreCase(keyword);
    }

}
