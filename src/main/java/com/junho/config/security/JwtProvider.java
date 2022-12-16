package com.junho.config.security;

import com.junho.config.security.token.AccessTokenRepository;
import com.junho.config.support.error.ErrorCode;
import com.junho.config.support.exception.TokenExpiredException;
import com.junho.homepage.member.Authority;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JpaUserDetailsService userDetailsService;
    private final AccessTokenRepository accessTokenRepository;

    // 만료시간 : 1Hour
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000L * 60 * 60;

    // Bearer 검증
    private static final String BEARER = "BEARER ";

    @Value("${jwt.secret.key}")
    private String salt;
    private Key secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(salt.getBytes(StandardCharsets.UTF_8));
    }

    public String createAccessToken(String account, List<Authority> roles) {
        Claims claims = Jwts.claims().setSubject(account);
        claims.put("roles", roles);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getAccount(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getAccount(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public boolean isValidToken(String token) {
        try {
            if (StringUtils.isBlank(token) || !token.toUpperCase().startsWith(BEARER)) {
                return false;
            }
            String refined = token.substring(BEARER.length());
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(refined);

            String account = claims.getBody().getSubject();
            boolean expired = claims.getBody().getExpiration().before(new Date());

            return !expired && isExpireByRedis(account);
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException(ErrorCode.TOKEN_EXPIRED);
        } catch (Exception ignored) {
            return false;
        }
    }

    private boolean isExpireByRedis(String account) {
        return accessTokenRepository.existsById(account);
    }
}
