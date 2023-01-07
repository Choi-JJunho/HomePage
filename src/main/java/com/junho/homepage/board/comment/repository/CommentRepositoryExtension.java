package com.junho.homepage.board.comment.repository;

import com.junho.homepage.board.comment.dto.response.CommentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentRepositoryExtension {

    Page<CommentResponse> getComments(Long articleId, String keyword, Pageable pageable);
}
