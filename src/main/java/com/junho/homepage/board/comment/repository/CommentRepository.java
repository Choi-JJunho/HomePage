package com.junho.homepage.board.comment.repository;

import com.junho.homepage.board.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryExtension {

}
