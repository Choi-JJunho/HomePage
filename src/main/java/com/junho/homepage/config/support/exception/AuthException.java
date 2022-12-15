package com.junho.homepage.config.support.exception;

import com.junho.homepage.config.support.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthException extends RuntimeException {
    private final ErrorCode errorCode;
}