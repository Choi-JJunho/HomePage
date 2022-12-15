package com.junho.homepage.config.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.junho.homepage.config.support.error.ErrorCode;
import com.junho.homepage.config.support.error.ErrorResponse;
import com.junho.homepage.config.support.exception.AuthException;
import com.junho.homepage.config.support.exception.TokenExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter 내부에서 Exception 체크를 위한 Filter
 */
@Slf4j
@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (TokenExpiredException e) {
            setErrorMessage(response, e.getErrorCode());
        } catch (AuthException e) {
            setErrorMessage(response, e.getErrorCode());
        } catch (AccessDeniedException e) {
            setErrorMessage(response, ErrorCode.AUTHORIZATION_INVALID);
        } catch (RuntimeException e) {
            setErrorMessage(response, ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private void setErrorMessage(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(errorCode.getHttpStatus().value());
        response.getWriter().write(makeErrorMessage(errorCode));
    }

    private String makeErrorMessage(ErrorCode errorCode) throws JsonProcessingException {
        return objectMapper.writeValueAsString(
                ErrorResponse.builder()
                        .httpStatus(errorCode.getHttpStatus().value())
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }
}