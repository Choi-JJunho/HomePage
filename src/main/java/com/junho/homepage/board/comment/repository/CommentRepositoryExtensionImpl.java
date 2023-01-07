package com.junho.homepage.board.comment.repository;

import com.junho.homepage.board.comment.Comment;
import com.junho.homepage.board.comment.QComment;
import com.junho.homepage.board.comment.dto.response.CommentResponse;
import com.junho.support.CustomQueryDslSupport;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

public class CommentRepositoryExtensionImpl extends CustomQueryDslSupport implements CommentRepositoryExtension {

    private final JPAQueryFactory queryFactory;

    private final QComment comment = QComment.comment;

    public CommentRepositoryExtensionImpl(JPAQueryFactory queryFactory) {
        super(Comment.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<CommentResponse> getComments(Long articleId, String keyword, Pageable pageable) {

        JPAQuery<Comment> query = queryFactory
                .select(comment)
                .from(comment)
                .where(
                        comment.article.id.eq(articleId),
                        find(keyword, comment.description)
                );

        Page<Comment> page = page(query, pageable);

        List<CommentResponse> response = page.stream()
                .map(CommentMapper.INSTANCE::toCommentResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(response, pageable, page.getTotalElements());
    }
}
