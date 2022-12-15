package com.junho.homepage.member.service;

import com.junho.homepage.config.security.JwtProvider;
import com.junho.homepage.config.support.error.ErrorCode;
import com.junho.homepage.config.support.exception.ApiException;
import com.junho.homepage.member.Authority;
import com.junho.homepage.member.Member;
import com.junho.homepage.member.RedisMember;
import com.junho.homepage.member.dto.MemberRequest;
import com.junho.homepage.member.dto.MemberResponse;
import com.junho.homepage.member.dto.TokenResponse;
import com.junho.homepage.member.mapper.MemberMapper;
import com.junho.homepage.member.repository.MemberRepository;
import com.junho.homepage.member.repository.RedisMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private static final String tokenWhiteList = "tokenWhiteList";
    private final MemberRepository memberRepository;
    private final RedisMemberRepository redisMemberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public TokenResponse login(MemberRequest request) {
        // 아이디 검증
        Member member = memberRepository.findByAccount(request.getAccount())
                .orElseThrow(() -> new ApiException(ErrorCode.INVALID_LOGIN_INFO));

        // 비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new ApiException(ErrorCode.INVALID_LOGIN_INFO);
        }

        String token = jwtProvider.createToken(member.getAccount(), member.getRoles());
        RedisMember save = redisMemberRepository.save(RedisMember.of(member.getAccount(), token));
        return TokenResponse.from(token);

    }

    public boolean register(MemberRequest request) {
        try {
            Member member = MemberMapper.INSTANCE.toMemberEntity(request);
            // TODO : 권한 구분해서 부여할 수 있도록 고려하기
            member.setRoles(Collections.singletonList(Authority.builder().name("ROLE_MANAGER").build()));

            memberRepository.save(member);
        } catch (Exception e) {
            throw new ApiException(ErrorCode.ALREADY_EXIST_ACCOUNT);
        }
        return true;
    }

    public boolean logout(String account) {
        redisMemberRepository.deleteById(account);
        return true;
    }

    public MemberResponse getMember(String account) {
        Member member = memberRepository.findByAccount(account)
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

        return MemberMapper.INSTANCE.toMemberResponse(member);
    }
}
