package com.junho.config.filter;

import com.junho.config.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Jwt가 유효한 토큰인지 검증하는 Filter
 */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = jwtProvider.resolveToken((HttpServletRequest) servletRequest);

        // TODO : static한 path를 관리하는게 마음에 들지 않음... 더 나은방법 찾아보기
        String path = ((HttpServletRequest) servletRequest).getServletPath();
        if ("/refresh".equals(path) || "/signout".equals(path)) {
            setAuthByToken(token);
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (jwtProvider.isValidToken(token)) {
            setAuthByToken(token);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void setAuthByToken(String token) {
        String refined = token.split(" ")[1].trim();
        Authentication auth = jwtProvider.getAuthentication(refined);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
