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
@RedisHash(value = "accessToken", timeToLive = 60 * 60 * 60L) // Access Token TTL 1Hour
public class AccessToken {

    @Id
    String id;
    String token;

    public static AccessToken of(String account, String token) {
        return AccessToken.builder()
                .id(account)
                .token(token)
                .build();
    }

}
