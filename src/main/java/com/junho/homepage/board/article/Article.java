package com.junho.homepage.board.article;

import com.junho.homepage.board.article.dto.request.CreateArticle;
import com.junho.homepage.board.Board;
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

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Article extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private Integer hits;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    // TODO : 첨부파일 고려해보기

    public void update(CreateArticle request) {
        this.title = request.getTitle();
        this.description = request.getDescription();
    }

    public void increaseViewCount() {
        this.hits++;
    }
}
