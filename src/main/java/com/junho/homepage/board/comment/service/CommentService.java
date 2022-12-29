package com.junho.homepage.board.comment.service;

import com.junho.homepage.board.article.repository.ArticleRepository;
import com.junho.homepage.board.comment.Comment;
import com.junho.homepage.board.comment.dto.request.CreateComment;
import com.junho.homepage.board.comment.dto.response.CommentResponse;
import com.junho.homepage.board.comment.repository.CommentMapper;
import com.junho.homepage.board.comment.repository.CommentRepository;
import com.junho.support.error.ErrorCode;
import com.junho.support.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    public List<CommentResponse> getCommentsByArticleId(Long articleId) {
        
        List<Comment> comments = commentRepository.findByArticle_Id(articleId)
                .orElseThrow(() -> new ApiException(ErrorCode.BOARD_NOT_EXIST));

        return comments.stream()
                .map(CommentMapper.INSTANCE::toCommentResponse)
                .collect(Collectors.toList());
    }

    public boolean postComment(CreateComment request) {

        if (request.getParentId() == null) {
            Comment.builder()
                    .description(request.getDescription())
                    .article(articleRepository.findById(request.getArticleId())
                            .orElseThrow(() -> new ApiException(ErrorCode.ARTICLE_NOT_EXIST)))
                    .depth(0)
                    .build();
        } else {
            Comment parent = commentRepository.findById(request.getParentId())
                    .orElseThrow(() -> new ApiException(ErrorCode.COMMENT_NOT_EXIST));

            Comment.builder()
                    .description(request.getDescription())
                    .article(articleRepository.findById(request.getArticleId())
                            .orElseThrow(() -> new ApiException(ErrorCode.ARTICLE_NOT_EXIST)))
                    .parent(parent)
                    .depth(parent.getDepth() + 1)
                    .build();
        }

        return true;
    }

    public CommentResponse modifyComment(Long id, CreateComment request) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.COMMENT_NOT_EXIST));

        comment.update(request);
        return CommentMapper.INSTANCE.toCommentResponse(comment);
    }

    public boolean deleteComment(Long id) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.COMMENT_NOT_EXIST));

        comment.setEnabled(false);
        return true;
    }
}
