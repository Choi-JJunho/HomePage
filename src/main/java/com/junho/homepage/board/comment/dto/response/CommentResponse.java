package com.junho.homepage.board.comment.dto.response;

import com.junho.homepage.member.dto.response.MemberResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {

    @ApiModelProperty(notes = "고유 id", example = "1")
    private Long id;

    @ApiModelProperty(notes = "댓글 내용", example = "댓글입니다.")
    private String description;

    @ApiModelProperty(notes = "댓글 깊이\n0 : 최상위댓글\n1 ~ n : 하위댓글", example = "0")
    private Long depth;

    @ApiModelProperty(notes = "작성자")
    private MemberResponse creator;

    @ApiModelProperty(notes = "수정자")
    private MemberResponse modifier;

    // TODO : 대댓글 표현방식?
}
