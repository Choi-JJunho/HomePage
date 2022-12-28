package com.junho.homepage.board.comment;

import com.junho.homepage.board.article.Article;
import com.junho.homepage.board.comment.dto.request.ModifyComment;
import com.junho.support.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;

    @OneToOne
    private Comment parent;

    private Integer depth;

    public void update(ModifyComment request) {
        this.description = request.getDescription();
    }
}
