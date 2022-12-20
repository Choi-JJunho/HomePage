package com.junho.homepage.article;

import com.junho.config.support.BaseEntity;
import com.junho.homepage.article.dto.ArticleRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "article")
public class Article extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "atcl_id")
    private Long id;

    @Column(name = "atcl_tt")
    private String title;

    @Column(name = "atcl_cn")
    private String description;

    @Column(name = "hits")
    private Integer hits;

    // TODO : 첨부파일 고려해보기

    public void update(ArticleRequest request) {
        this.title = request.getTitle();
        this.description = request.getDescription();
    }

    public void increaseViewCount() {
        this.hits++;
    }
}
