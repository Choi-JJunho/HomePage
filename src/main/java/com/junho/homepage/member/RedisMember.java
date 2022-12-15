package com.junho.homepage.member;

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
@RedisHash("member")
public class RedisMember {

    @Id
    String id;
    String token;

    public static RedisMember of(String account, String token) {
        return RedisMember.builder()
                .id(account)
                .token(token)
                .build();
    }

}
