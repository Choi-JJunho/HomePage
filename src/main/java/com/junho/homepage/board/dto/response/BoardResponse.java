package com.junho.homepage.board.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponse {

    @ApiModelProperty(notes = "고유 id", example = "10")
    private Long id;

    @ApiModelProperty(notes = "게시판 제목", example = "최준호의 게시글입니다.")
    private String title;

    @ApiModelProperty(notes = "게시판 내용", example = "게시글의 내용입니다.")
    private String description;

    @ApiModelProperty(notes = "작성자 id", example = "10")
    private Long creatorId;

    @ApiModelProperty(notes = "작성자 id", example = "10")
    private Long modifierId;

    @ApiModelProperty(notes = "생성일자", example = "2020.12.15 12:11")
    private LocalDateTime createDate;

    @ApiModelProperty(notes = "수정일자", example = "2020.12.15 12:11")
    private LocalDateTime updateDate;
}
