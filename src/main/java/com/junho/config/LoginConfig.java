package com.junho.config;

import com.junho.config.security.CustomUserDetails;
import com.junho.homepage.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class LoginConfig implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return Optional.empty();
        }
        if (!(authentication.getPrincipal() instanceof CustomUserDetails)) {
            return Optional.empty();
        }
        
        Member member = ((CustomUserDetails) authentication.getPrincipal()).getMember();
        return Optional.ofNullable(member.getId());
    }
}
