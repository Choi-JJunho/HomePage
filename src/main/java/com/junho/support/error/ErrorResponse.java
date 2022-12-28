package com.junho.support.error;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class ErrorResponse {

    @ApiModelProperty(value = "오류코드")
    private final int code;

    @ApiModelProperty(value = "Http 상태코드")
    private final int httpStatus;

    @ApiModelProperty(value = "오류 메시지")
    private final String message;
}
