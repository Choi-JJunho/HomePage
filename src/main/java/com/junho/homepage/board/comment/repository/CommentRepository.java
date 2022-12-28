package com.junho.homepage.board.comment.repository;

import com.junho.homepage.board.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // TODO: 계층구조 생각해서 List<Comment> 부분 다시 고려하기
    //  + 쿼리 최적화
    Optional<List<Comment>> findByArticle_Id(Long articleId);
}
