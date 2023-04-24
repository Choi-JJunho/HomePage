package com.junho.config.security;

import com.junho.homepage.member.Authority;
import com.junho.support.error.ErrorCode;
import com.junho.support.exception.TokenExpiredException;
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
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JpaUserDetailsService userDetailsService;

    // 만료시간 : 1Hour
    private static final long ACCESS_TOKEN_EXPIRE_TIME = TimeUnit.HOURS.toMillis(1);

    // Bearer 검증
    private static final String BEARER_PREFIX = "BEARER ";

    @Value("${jwt.secret.key}")
    private String salt;
    private Key secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(salt.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(String account, List<Authority> roles) {
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
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey).build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        }
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public boolean isValidToken(String token) {
        try {
            if (StringUtils.isBlank(token) || !token.toUpperCase().startsWith(BEARER_PREFIX)) {
                return false;
            }
            String refined = token.substring(BEARER_PREFIX.length());
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(refined);
            boolean expired = claims.getBody().getExpiration().before(new Date());
            return !expired;
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException(ErrorCode.TOKEN_EXPIRED);
        } catch (Exception ignored) {
            return false;
        }
    }
}
