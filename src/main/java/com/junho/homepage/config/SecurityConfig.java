package com.junho.homepage.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.junho.homepage.config.filter.ExceptionHandlerFilter;
import com.junho.homepage.config.filter.JwtAuthenticationFilter;
import com.junho.homepage.config.security.JwtProvider;
import com.junho.homepage.config.support.error.ErrorCode;
import com.junho.homepage.config.support.error.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper = new ObjectMapper();

    String[] swaggerPaths = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            /* swagger v3 */
            "/v3/api-docs/**",
            "/swagger-ui/**"};

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .cors(c -> {
                            CorsConfigurationSource source = request -> {
                                // Cors 허용 패턴
                                CorsConfiguration config = new CorsConfiguration();
                                config.setAllowedOrigins(
                                        // List.of ("koreatech.in", "jjbaksa.com");
                                        List.of("*")
                                );
                                config.setAllowedMethods(
                                        // List.of("GET", "POST", "PUT", "DELETE")
                                        List.of("*")
                                );
                                return config;
                            };
                            c.configurationSource(source);
                        }
                )
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // TODO : 권한에 따른 접근 제한범위 설정
                .antMatchers("/", "/signup", "/signin").permitAll()
                .antMatchers(swaggerPaths).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new ExceptionHandlerFilter(), JwtAuthenticationFilter.class)

                .exceptionHandling()
                .accessDeniedHandler(
                        (request, response, accessDeniedException) -> {
                            ErrorCode errorCode = ErrorCode.AUTHORIZATION_INVALID;
                            setErrorMessage(response, errorCode);
                        })
                .authenticationEntryPoint(
                        (request, response, authException) -> {
                            ErrorCode errorCode = ErrorCode.AUTHENTICATION_INVALID;
                            setErrorMessage(response, errorCode);
                        });

        return http.build();
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

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        //큰 권한 순서로 ' > ' 를 사용하여 입력해준다. 띄어쓰기도 중요하다.
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_MANAGER > ROLE_USER");
        return roleHierarchy;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
