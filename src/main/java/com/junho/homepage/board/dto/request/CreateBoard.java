package com.junho.homepage.board.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBoard {

    @NotNull
    @ApiModelProperty(notes = "게시판 제목", example = "자유게시판", required = true)
    private String title;

    @ApiModelProperty(notes = "게시판 설명", example = "자유롭게 글을 올리는 게시판입니다.")
    private String description;
}
