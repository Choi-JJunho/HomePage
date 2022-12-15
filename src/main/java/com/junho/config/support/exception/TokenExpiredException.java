package com.junho.config.support.exception;

import com.junho.config.support.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenExpiredException extends RuntimeException {
    private final ErrorCode errorCode;
}
