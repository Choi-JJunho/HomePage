package com.junho.config.security;

import com.junho.homepage.member.domain.Member;
import com.junho.homepage.member.repository.MemberRepository;
import com.junho.support.error.ErrorCode;
import com.junho.support.exception.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByAccount(username)
                .orElseThrow(() -> new AuthException(ErrorCode.USER_NOT_FOUND));

        return new CustomUserDetails(member);
    }
}
