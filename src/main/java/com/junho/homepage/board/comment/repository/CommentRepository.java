package com.junho.homepage.board.comment.repository;

import com.junho.homepage.board.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<List<Comment>> findByArticle_Id(Long articleId);
}
