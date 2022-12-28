package com.junho.config;

import com.junho.config.security.CustomUserDetails;
import com.junho.homepage.member.Member;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @CreatedBy 어노테이션 적용을 위한 설정
 */
@Component
public class SignInConfig implements AuditorAware<Member> {

    @Override
    public Optional<Member> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return Optional.empty();
        }
        if (!(authentication.getPrincipal() instanceof CustomUserDetails)) {
            return Optional.empty();
        }

        Member member = ((CustomUserDetails) authentication.getPrincipal()).getMember();
        return Optional.ofNullable(member);
    }
}
