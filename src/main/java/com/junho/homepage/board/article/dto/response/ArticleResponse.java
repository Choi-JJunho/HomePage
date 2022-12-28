package com.junho.homepage.board.article.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Builder
public class ArticleResponse {

    @ApiModelProperty(notes = "고유 id", example = "10")
    private Long id;

    @ApiModelProperty(notes = "작성자 id", example = "10")
    private Long creatorId;

    @ApiModelProperty(notes = "작성자 id", example = "10")
    private Long modifierId;

    @ApiModelProperty(notes = "게시물 제목", example = "최준호의 게시글입니다.")
    private String title;

    @ApiModelProperty(notes = "게시물 내용", example = "게시글의 내용입니다.")
    private String description;

    @ApiModelProperty(notes = "생성일자", example = "2020.12.15 12:11")
    private LocalDateTime createDate;

    @ApiModelProperty(notes = "수정일자", example = "2020.12.15 12:11")
    private LocalDateTime updateDate;

    // QueryDsl에서 결과를 조회할 때 DTO로 변환하기위해 작성
    // TODO : 더 나은방식은 없나..?
    @QueryProjection
    @Builder
    public ArticleResponse(Long id, Long creatorId, Long modifierId, String title, String description, LocalDateTime createDate, LocalDateTime updateDate) {
        this.id = id;
        this.creatorId = creatorId;
        this.modifierId = modifierId;
        this.title = title;
        this.description = description;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}
