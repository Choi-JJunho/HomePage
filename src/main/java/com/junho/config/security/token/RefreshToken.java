package com.junho.config.security.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "refreshToken", timeToLive = 60 * 60 * 60 * 24 * 7) // Refresh Token TTL 7Days
public class RefreshToken {

    @Id
    String id;
    String token;

    public static RefreshToken of(String account, String token) {
        return RefreshToken.builder()
                .id(account)
                .token(token)
                .build();
    }

}
