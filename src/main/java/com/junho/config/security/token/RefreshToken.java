package com.junho.config.security.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "refreshToken")
public class RefreshToken {

    @Id
    private String id;
    private String token;

    @TimeToLive(unit = TimeUnit.DAYS)
    private Integer expire_time;

    public static RefreshToken of(String account, String token) {
        return RefreshToken.builder()
                .id(account)
                .token(token)
                .expire_time(3) // TTL 3 Days
                .build();
    }
}
