package com.junho.config.security;

import com.junho.config.support.error.ErrorCode;
import com.junho.config.support.exception.AuthException;
import com.junho.homepage.member.Member;
import com.junho.homepage.member.repository.MemberRepository;
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
