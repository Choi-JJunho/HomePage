package com.junho.homepage.member.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenResponse {

    private final String accessToken;
    private String refreshToken;

    private TokenResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    private TokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public static TokenResponse from(String accessToken) {
        return new TokenResponse(accessToken);
    }

    public static TokenResponse of(String accessToken, String refreshToken) {
        return new TokenResponse(accessToken, refreshToken);
    }
}
