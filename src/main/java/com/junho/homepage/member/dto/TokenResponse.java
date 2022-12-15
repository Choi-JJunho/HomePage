package com.junho.homepage.member.dto;

import lombok.Getter;

@Getter
public class TokenResponse {
    private final String token;

    private TokenResponse(String token) {
        this.token = token;
    }

    public static TokenResponse from(String token) {
        return new TokenResponse(token);
    }
}
