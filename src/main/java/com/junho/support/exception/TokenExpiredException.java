package com.junho.support.exception;

import com.junho.support.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenExpiredException extends RuntimeException {
    private final ErrorCode errorCode;
}
