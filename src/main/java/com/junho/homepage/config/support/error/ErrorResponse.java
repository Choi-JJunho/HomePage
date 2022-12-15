package com.junho.homepage.config.support.error;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class ErrorResponse {

    private final int code;
    private final int httpStatus;
    private final String message;

}
