package com.junho.homepage.article.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleRequest {

    @ApiModelProperty(notes = "고유 id", example = "10")
    private String id;

    @ApiModelProperty(notes = "작성자 id", example = "10")
    private String memberId;

    @ApiModelProperty(notes = "게시물 제목", example = "최준호의 게시글입니다.")
    private String title;

    @ApiModelProperty(notes = "게시물 내용", example = "게시글의 내용입니다.")
    private String description;
}
