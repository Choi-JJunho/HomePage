package com.junho.homepage.article;

import com.junho.config.support.BaseEntity;
import com.junho.homepage.article.dto.ArticleRequest;
import com.junho.homepage.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "atcl")
public class Article extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "atcl_id")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mbr_id")
    private Member member;

    @Column(name = "atcl_tt")
    private String title;

    @Column(name = "atcl_cn")
    private String description;

    public void update(ArticleRequest request) {
        this.title = request.getTitle();
        this.description = request.getDescription();
    }
}
