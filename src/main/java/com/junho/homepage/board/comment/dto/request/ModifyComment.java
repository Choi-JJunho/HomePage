package com.junho.homepage.board.comment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModifyComment {
    private Long id;
    private String description;
    private Long articleId;
    private Long parentId;
}
